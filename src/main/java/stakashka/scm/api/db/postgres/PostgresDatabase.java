package stakashka.scm.api.db.postgres;

import stakashka.scm.api.db.AbstractDatabase;
import stakashka.scm.api.db.TypesTemplates;
import stakashka.scm.core.data.Column;
import stakashka.scm.core.data.DataTypes;
import stakashka.scm.core.data.Table;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

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
    protected List<Table> getTablesList() throws SQLException {
        return null;
    }

    @Override
    protected List<Column> getColumnsList() throws SQLException {
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
}
