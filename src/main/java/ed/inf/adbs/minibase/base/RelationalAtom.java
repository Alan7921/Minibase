package ed.inf.adbs.minibase.base;

import ed.inf.adbs.minibase.Utils;

import java.util.List;

/**
 * This class abstract the concept of relational atom.
 * It contains its name, a list of terms and a aggregate variable.
 * For convenience, its toString, hashcode and equals method are
 * overridden. Besides,it also provides some getter and setter.
 */

public class RelationalAtom extends Atom {
    private String name;
    private List<Term> terms;
    private AggregateVariable aggregateVariable = null;

    public RelationalAtom(String name, List<Term> terms) {
        this.name = name;
        this.terms = terms;
    }

    public AggregateVariable getAggregateVariable() {
        return aggregateVariable;
    }

    public void setAggregateVariable(AggregateVariable aggregateVariable) {
        this.aggregateVariable = aggregateVariable;
    }

    public String getName() {
        return name;
    }

    public List<Term> getTerms() {
        return terms;
    }

    @Override
    public String toString() {
        return name + "(" + Utils.join(terms, ", ") + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RelationalAtom)) return false;

        RelationalAtom that = (RelationalAtom) o;

        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        return getTerms() != null ? getTerms().equals(that.getTerms()) : that.getTerms() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getTerms() != null ? getTerms().hashCode() : 0);
        return result;
    }
}
