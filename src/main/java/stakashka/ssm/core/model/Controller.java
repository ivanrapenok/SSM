package stakashka.ssm.core.model;

import stakashka.ssm.api.db.AbstractDatabase;
import stakashka.ssm.api.db.oracle.OracleDatabase;
import stakashka.ssm.api.db.postgres.PostgresDatabase;
import stakashka.ssm.core.data.Schema;

import java.io.FileWriter;
import java.io.IOException;

public class Controller {

    public enum Databases {
        Oracle,
        Postgres
    }

    private String urlFrom;
    private String dbUser;
    private String dbPassword;
    private Databases dbFrom;
    private Databases dbTo;
    private String schemaFrom;
    private String schemaTo;

    public Controller(String urlFrom, String dbUser, String dbPassword, String schemaFrom,
                      Databases dbFrom, String schemaTo, Databases dbTo) {
        this.urlFrom = urlFrom;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.dbFrom = dbFrom;
        this.dbTo = dbTo;
        this.schemaFrom = schemaFrom;
        this.schemaTo = schemaTo;
    }

    public void process() {
        try {
            AbstractDatabase databaseFrom = null;
            AbstractDatabase databaseTo = null;
            Schema schema = null;
            switch (dbFrom) {
                case Oracle:
                    databaseFrom = new OracleDatabase(urlFrom, dbUser, dbPassword);
                    break;
            }
            if (databaseFrom != null) {
                schema = databaseFrom.getSchema();
                System.out.println(schema.getTablesList());
                System.out.println(schema.getColumnsList());
            }

            switch (dbTo) {
                case Postgres:
                    databaseTo = new PostgresDatabase(schemaTo);
                    break;
            }
            if (databaseTo != null && schema != null) {
                writeToFile(databaseTo.crateDDL(schema));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(String text) {
        try(FileWriter writer = new FileWriter("createbase.sql", false)) {
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initDrivers() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
