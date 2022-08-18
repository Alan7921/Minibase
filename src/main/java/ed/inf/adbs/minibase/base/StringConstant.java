package ed.inf.adbs.minibase.base;
/**
 * This class abstract the concept of String Constant.
 * It encapsulates a string typed value.
 * For convenience, its toString, hashcode and equals method are
 * overridden. Besides,it also provides some getters.
 */
public class StringConstant extends Constant {
    private String value;

    public StringConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "'" + value + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringConstant)) return false;

        StringConstant that = (StringConstant) o;

        return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
    }

    @Override
    public int hashCode() {
        return getValue() != null ? getValue().hashCode() : 0;
    }
}