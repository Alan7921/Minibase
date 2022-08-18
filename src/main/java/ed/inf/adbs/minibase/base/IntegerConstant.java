package ed.inf.adbs.minibase.base;
/**
 * This class abstract the concept of Integer Constant.
 * For convenience, its toString, hashcode and equals method are
 * overridden. Besides,it also provides some getters.
 */
public class IntegerConstant extends Constant {
    private Integer value;

    public IntegerConstant(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntegerConstant)) return false;

        IntegerConstant that = (IntegerConstant) o;

        return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
    }

    @Override
    public int hashCode() {
        return getValue() != null ? getValue().hashCode() : 0;
    }
}
