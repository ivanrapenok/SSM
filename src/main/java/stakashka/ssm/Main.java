package stakashka.ssm;

import stakashka.ssm.core.model.Controller;
import stakashka.ssm.core.model.Controller.Databases;

public class Main {

    public static void main(String[] args) {
        String urlFrom = "jdbc:oracle:thin:@localhost:1521:orcl";
        String user = "ewa";
        String password = "ewapas";
        String schemaFrom = "ewa";
        Databases databaseFrom = Databases.Oracle;
        String schemaTo = "ewa";
        Databases databaseTo = Databases.Postgres;

        Controller controller = new Controller(urlFrom, user, password, schemaFrom, databaseFrom, schemaTo, databaseTo);
        controller.process();
    }
}
