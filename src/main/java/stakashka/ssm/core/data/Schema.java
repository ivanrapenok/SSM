package stakashka.ssm.core.data;

import java.util.ArrayList;
import java.util.List;

public class Schema {

    private List<Table> tablesList;
    private List<Column> columnsList;
    private List<Constraint> constraintsList;
    private List<ConstraintColumn> constraintColumnsList;


    public Schema() {
        tablesList = new ArrayList<>();
        columnsList = new ArrayList<>();
        constraintsList = new ArrayList<>();
        constraintColumnsList = new ArrayList<>();
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

    public List<Constraint> getConstraintsList() {
        return constraintsList;
    }

    public void setConstraintsList(List<Constraint> constraintsList) {
        this.constraintsList = constraintsList;
    }

    public List<ConstraintColumn> getConstraintColumnsList() {
        return constraintColumnsList;
    }

    public void setConstraintColumnsList(List<ConstraintColumn> constraintColumnsList) {
        this.constraintColumnsList = constraintColumnsList;
    }

    @Override
    public String toString() {
        return "Schema{" +
                "tablesList=" + tablesList +
                ", columnsList=" + columnsList +
                ", constraintsList=" + constraintsList +
                ", constraintColumnsList=" + constraintColumnsList +
                '}';
    }
}
