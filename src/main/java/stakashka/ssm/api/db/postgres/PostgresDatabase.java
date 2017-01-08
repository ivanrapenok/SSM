package stakashka.ssm.api.db.postgres;

import stakashka.ssm.api.db.AbstractDatabase;
import stakashka.ssm.api.db.TypesTemplates;
import stakashka.ssm.core.data.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PostgresDatabase extends AbstractDatabase {

    private enum ColumnTypes implements TypesTemplates {
        CHARACTER,
        NUMERIC;

        @Override
        public String getTypeTemplate(String[] args) {
            switch (this) {
                case CHARACTER:
                    return String.format("CHARACTER(%s)", args[0]);
                case NUMERIC:
                    return String.format("NUMERIC(%s,%s)", args[1], args[2]);
                default:
                    return this.name();
            }
        }
    }

    public PostgresDatabase(String schemaTo) throws Exception {
        super(schemaTo);
    }

    public PostgresDatabase(String url, String user, String password, String schemaFrom) throws SQLException {
        super(url, user, password, schemaFrom);
    }

        @Override
        protected void initTypesMapping() {
            typesMapping = new HashMap<TypesTemplates, DataTypes.NormalizedTypes>() {{
                put(ColumnTypes.NUMERIC, DataTypes.NormalizedTypes.NUMBER);
                put(ColumnTypes.CHARACTER, DataTypes.NormalizedTypes.VARCHAR);
            }};
        }

    @Override
    protected void initDriver() {

    }

    @Override
    protected List<Table> getTablesList() throws SQLException {
        return null;
    }

    @Override
    protected List<Column> getColumnsList() throws SQLException {
        return null;
    }

    @Override
    protected List<Constraint> getConstraintsList() throws SQLException {
        return null;
    }

    @Override
    protected List<ConstraintColumn> getConstraintColumnsList() throws SQLException {
        return null;
    }

    @Override
    protected String createTableDDL(String tableName, List<Column> columnsList) throws Exception {
        String res = "";
        res += "CREATE TABLE " + ((schemaTo == null) ? "" : schemaTo + ".") + tableName + " (";
        for (Column column : columnsList) {
            String type = getSpecificType(column.getColumnType())
                    .getTypeTemplate(new String[]{column.getLength().toString(), column.getPrecision().toString(), column.getScale().toString()});
            res += "\n    " + column.getColumnName() + " " + type + ((column.isNullable()) ? "" : " not null") + ",";
        }
        res = res.substring(0, res.length() - 1);
        res += "\n);\n";
        return res;
    }

    @Override
    protected String createConstraintDDL(Constraint constraint, List<ConstraintColumn> columns,
                                         Constraint refConstraint, List<ConstraintColumn> refColumns) {
        String res = "ALTER TABLE " + ((schemaTo == null) ? "" : schemaTo + ".") + constraint.getTableName() + " ADD"
                + (constraint.isGeneratedName() ? "" : " CONSTRAINT " + constraint.getConstraintName()) + " ";
        switch (constraint.getConstraintType()) {
            case C:
                if (!constraint.getSearchCondition().contains("IS NOT NULL")) {
                    res += "CHECK (" + constraint.getSearchCondition() + ");\n";
                } else {
                    return "";
                }
                break;
            case O:
                break;
            case P:
                res += "PRIMARY KEY (";
                columns = columns.stream().sorted((c1, c2) -> Integer.compare(c1.getPosition(), c2.getPosition()))
                        .collect(Collectors.toList());
                for (ConstraintColumn column: columns) {
                    res += column.getColumnName() + ", ";
                }
                res = res.substring(0, res.length() - 2) + ");\n";
                break;
            case U:
                res += "UNIQUE (";
                columns = columns.stream().sorted((c1, c2) -> Integer.compare(c1.getPosition(), c2.getPosition()))
                        .collect(Collectors.toList());
                for (ConstraintColumn column: columns) {
                    res += column.getColumnName() + ", ";
                }
                res = res.substring(0, res.length() - 2) + ");\n";
            case V:
                break;
            case R:
                res += "FOREIGN KEY (" + columns.get(0).getColumnName() + ") REFERENCES "
                        + ((schemaTo == null) ? "" : schemaTo + ".") + refConstraint.getTableName()
                        + " (" + refColumns.get(0).getColumnName() + ");\n";
                break;
        }
        return res;
    }
}
