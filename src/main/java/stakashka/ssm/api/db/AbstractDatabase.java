package stakashka.ssm.api.db;

import stakashka.ssm.core.data.*;
import stakashka.ssm.core.data.Constraint.ConstraintType;
import stakashka.ssm.core.data.DataTypes.NormalizedTypes;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

abstract public class AbstractDatabase {

    protected String schemaFrom;
    protected String schemaTo;
    protected Connection connection = null;
    protected Statement statement = null;
    protected ResultSet resultSet = null;
    // TODO : unique values and think about it more
    protected Map<TypesTemplates, NormalizedTypes> typesMapping = null;
    protected Map<NormalizedTypes, TypesTemplates> inverseTypesMapping = null;

    public AbstractDatabase(String schemaTo) throws Exception {
        this.schemaTo = schemaTo;
        initTypesMapping();
        initInverseTypesMapping();
    }

    public AbstractDatabase(String url, String user, String password, String schemaFrom) throws SQLException {
        this.schemaFrom = schemaFrom;
        initTypesMapping();
        try {
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) connection.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            throw e;
        }
    }

    protected abstract void initTypesMapping();

    protected void initInverseTypesMapping() throws Exception {
        inverseTypesMapping = new HashMap<>();
        for (TypesTemplates key : typesMapping.keySet()) {
            inverseTypesMapping.put(typesMapping.get(key), key);
        }
        if (inverseTypesMapping.size() != typesMapping.size()) {
            throw new Exception();
        }
    }

    public Schema getSchema() throws SQLException {
        try {
            Schema schema = new Schema();
            schema.setTablesList(getTablesList());
            schema.setColumnsList(getColumnsList());
            schema.setConstraintsList(getConstraintsList());
            schema.setConstraintColumnsList(getConstraintColumnsList());
            return schema;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (statement != null) statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (resultSet != null) resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String crateDDL(Schema schema) throws Exception {
        String resultDDL = "";

        Map<String, List<Column>> groupByTables = schema.getColumnsList()
                .stream().collect(Collectors.groupingBy(Column::getTableName));
        for (String table : groupByTables.keySet()) {
            resultDDL += createTableDDL(table, groupByTables.get(table));
        }

        // foreign key must be created last
        List<Constraint> sortedConstraintList = schema.getConstraintsList().stream()
                .sorted((c1, c2) -> ConstraintType.compare(c1.getConstraintType(), c2.getConstraintType()))
                .collect(Collectors.toList());
        Map<String, List<ConstraintColumn>> columnsMap = schema.getConstraintColumnsList()
                .stream().collect(Collectors.groupingBy(ConstraintColumn::getConstraintName));
        for (Constraint constraint : sortedConstraintList) {
            Constraint refConstraint = null;
            List<ConstraintColumn> refCol = null;
            if (constraint.getRefConstraintName() != null) {
                refConstraint = schema.getConstraintsList().stream()
                        .filter(con -> con.getConstraintName().equals(constraint.getRefConstraintName()))
                        .findFirst().get();
                refCol = columnsMap.get(refConstraint.getConstraintName());
            }
            resultDDL += createConstraintDDL(constraint, columnsMap.get(constraint.getConstraintName()), refConstraint, refCol);
        }

        System.out.println(resultDDL);
        return resultDDL;
    }

    protected abstract List<Table> getTablesList() throws SQLException;

    protected abstract List<Column> getColumnsList() throws SQLException;

    protected abstract List<Constraint> getConstraintsList() throws SQLException;

    protected abstract List<ConstraintColumn> getConstraintColumnsList() throws SQLException;

    protected NormalizedTypes getNormalizedType(TypesTemplates type) {
        NormalizedTypes res = typesMapping.get(type);
        return (res == null) ? NormalizedTypes.VARCHAR : res;
    }

    protected TypesTemplates getSpecificType(NormalizedTypes type) throws Exception {
        TypesTemplates res = inverseTypesMapping.get(type);
        if (res == null) {
            throw new Exception();
        }
        return res;
    }

    protected abstract String createTableDDL(String tableName, List<Column> columnsList) throws Exception;

    protected abstract String createConstraintDDL(Constraint constraint, List<ConstraintColumn> columns,
                                                  Constraint refConstraint, List<ConstraintColumn> refColumns);
}
