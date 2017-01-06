package stakashka.ssm.core.data;

public class ConstraintColumn {

    private String constraintName;
    private String tableName;
    private String columnName;
    private Integer position;

    public ConstraintColumn(String constraintName, String tableName, String columnName, Integer position) {
        this.constraintName = constraintName;
        this.tableName = tableName;
        this.columnName = columnName;
        this.position = position;
    }

    public String getConstraintName() {
        return constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "ConstraintColumn{" +
                "constraintName='" + constraintName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", columnName='" + columnName + '\'' +
                ", position=" + position +
                '}';
    }
}
