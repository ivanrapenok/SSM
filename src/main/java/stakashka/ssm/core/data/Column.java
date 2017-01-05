package stakashka.ssm.core.data;

public class Column {

    private String columnName;
    private String tableName;
    private DataTypes.NormalizedTypes columnType;
    private Integer length;
    private Integer precision;
    private Integer scale;
    private boolean nullable;

    public Column(String columnName, String tableName, DataTypes.NormalizedTypes columnType,
                  Integer length, Integer precision, Integer scale, boolean nullable) {
        this.columnName = columnName;
        this.tableName = tableName;
        this.columnType = columnType;
        this.length = length;
        this.precision = precision;
        this.scale = scale;
        this.nullable = nullable;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public DataTypes.NormalizedTypes getColumnType() {
        return columnType;
    }

    public void setColumnType(DataTypes.NormalizedTypes columnType) {
        this.columnType = columnType;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getPrecision() {
        return precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    @Override
    public String toString() {
        return "Column{" +
                "columnName='" + columnName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", columnType=" + columnType +
                ", length=" + length +
                ", precision=" + precision +
                ", scale=" + scale +
                ", nullable=" + nullable +
                '}';
    }
}
