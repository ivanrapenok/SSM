package stakashka.scm.api.db.oracle;

import stakashka.scm.api.db.AbstractDatabase;
import stakashka.scm.api.db.TypesTemplates;
import stakashka.scm.core.data.Column;
import stakashka.scm.core.data.DataTypes;
import stakashka.scm.core.data.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OracleDatabase extends AbstractDatabase {

    private enum ColumnTypes implements TypesTemplates {
        VARCHAR2,
        NUMBER;

        @Override
        public String getTypeTemplate(String[] args) {
            return null;
        }
    }

    public OracleDatabase(String schemaTo) throws Exception {
        super(schemaTo);
    }

    public OracleDatabase(String url, String user, String password) throws SQLException {
        super(url, user, password, null);
        schemaFrom = connection.getMetaData().getUserName();
    }

    @Override
    protected void initTypesMapping() {
        typesMapping = new HashMap<TypesTemplates, DataTypes.NormalizedTypes>() {{
            put(ColumnTypes.NUMBER, DataTypes.NormalizedTypes.NUMBER);
            put(ColumnTypes.VARCHAR2, DataTypes.NormalizedTypes.VARCHAR);
        }};
    }

    @Override
    public List<Table> getTablesList() throws SQLException {
        List<Table> tablesList = new ArrayList<Table>();
        statement = connection.createStatement();
        resultSet = statement.executeQuery("select TABLE_NAME from SYS.ALL_TABLES where OWNER = '" + schemaFrom + "'");
        while (resultSet.next()) {
            tablesList.add(new Table(resultSet.getString(1)));
        }
        return tablesList;
    }

    @Override
    protected List<Column> getColumnsList() throws SQLException {
        List<Column> columnsList = new ArrayList<>();
        statement = connection.createStatement();
        resultSet = statement.executeQuery(
                "select COLUMN_NAME, TABLE_NAME, DATA_TYPE, DATA_LENGTH, DATA_PRECISION, DATA_SCALE, NULLABLE " +
                        "from SYS.ALL_TAB_COLS where OWNER = '" + schemaFrom + "' order by COLUMN_ID"
        );
        while (resultSet.next()) {
            columnsList.add(new Column(
                    resultSet.getString(1), resultSet.getString(2),
                    getNormalizedType(ColumnTypes.valueOf(resultSet.getString(3))),
                    resultSet.getInt(4), resultSet.getInt(5), resultSet.getInt(6),
                    resultSet.getString(7).equals("Y")
            ));
        }
        return columnsList;
    }

    @Override
    protected String createTableDDL(String tableName, List<Column> columnsList) {
        return null;
    }

    public static void main(String[] args) throws Exception {
        Connection connection;
        Class.forName("oracle.jdbc.driver.OracleDriver");
        connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "ewa", "ewapas");
        System.out.println(connection.getMetaData().getDatabaseProductName());
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select TABLE_NAME from SYS.ALL_TABLES where OWNER = 'EWA'");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
        connection.close();
    }


}
