package ed.inf.adbs.minibase.base;

/**
 * This class abstract the concept of Variable and is the subclass of Term.
 * For convenience, its toString, hashcode and equals method are
 * overridden. Besides,it also provides some getters.
 */
public class Variable extends Term {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Variable)) return false;

        Variable variable = (Variable) o;

        return getName().equals(variable.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
