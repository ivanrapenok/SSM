package stakashka.ssm.core.data;

public class Constraint {

    public enum ConstraintType {
        C,
        O,
        P,
        U,
        V,
        R;
        // foreign keys must be created last
        public static int compare(ConstraintType ct1, ConstraintType ct2) {
            if (ct1 == ct2 || (ct1 != R && ct2 != R)) {
                return ct1.name().compareTo(ct2.name());
            } else if (ct1 == R) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    private String constraintName;
    private boolean generatedName;
    private ConstraintType constraintType;
    private String tableName;
    private String searchCondition;
    private String refConstraintName;

    public Constraint(String constraintName, boolean generatedName, ConstraintType constraintType,
                      String tableName, String searchCondition, String refConstraintName) {
        this.constraintName = constraintName;
        this.generatedName = generatedName;
        this.constraintType = constraintType;
        this.tableName = tableName;
        this.searchCondition = searchCondition;
        this.refConstraintName = refConstraintName;
    }

    public String getConstraintName() {
        return constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    public boolean isGeneratedName() {
        return generatedName;
    }

    public void setGeneratedName(boolean generatedName) {
        this.generatedName = generatedName;
    }

    public ConstraintType getConstraintType() {
        return constraintType;
    }

    public void setConstraintType(ConstraintType constraintType) {
        this.constraintType = constraintType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(String searchCondition) {
        this.searchCondition = searchCondition;
    }

    public String getRefConstraintName() {
        return refConstraintName;
    }

    public void setRefConstraintName(String refConstraintName) {
        this.refConstraintName = refConstraintName;
    }

    @Override
    public String toString() {
        return "Constraint{" +
                "constraintName='" + constraintName + '\'' +
                ", generatedName=" + generatedName +
                ", constraintType=" + constraintType +
                ", tableName='" + tableName + '\'' +
                ", searchCondition='" + searchCondition + '\'' +
                ", refConstraintName='" + refConstraintName + '\'' +
                '}';
    }
}
