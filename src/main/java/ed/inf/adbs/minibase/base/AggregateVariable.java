package ed.inf.adbs.minibase.base;

/**
 * This class abstract the concept of aggregation variables i.e.,
 * the variable that is aggregated in the head of a query.
 * It contains its name,and the type of this aggregation operation.
 * For convenience, its toString, hashcode and equals method are
 * overridden. Besides,it also provides some getters.
 */
public class AggregateVariable extends Term{
    private String name;
    private String type;

    public AggregateVariable(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AggregateVariable)) return false;

        AggregateVariable that = (AggregateVariable) o;

        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        return getType() != null ? getType().equals(that.getType()) : that.getType() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        return result;
    }
}
