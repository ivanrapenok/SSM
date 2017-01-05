package stakashka.scm.core.data;

import java.util.ArrayList;
import java.util.List;

public class Schema {

    private List<Table> tablesList;
    private List<Column> columnsList;

    public Schema() {
        tablesList = new ArrayList<Table>();
        columnsList = new ArrayList<Column>();
    }

    public List<Table> getTablesList() {
        return tablesList;
    }

    public void setTablesList(List<Table> tablesList) {
        this.tablesList = tablesList;
    }

    public List<Column> getColumnsList() {
        return columnsList;
    }

    public void setColumnsList(List<Column> columnsList) {
        this.columnsList = columnsList;
    }

    @Override
    public String toString() {
        return "Schema{" +
                "tablesList=" + tablesList +
                ", columnsList=" + columnsList +
                '}';
    }
}
