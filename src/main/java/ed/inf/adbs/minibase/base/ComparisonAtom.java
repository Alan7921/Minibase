package ed.inf.adbs.minibase.base;

import ed.inf.adbs.minibase.base.operator.ComparisonOperator;

import java.util.ArrayList;
import java.util.List;

/**
 * This class abstract the concept of comparison atom.
 * It contains its terms, and a comparison operator.
 * For convenience, its toString, hashcode and equals method are
 * overridden. Besides,it also provides some getter and setter.
 */
public class ComparisonAtom extends Atom {

    private Term term1;

    private Term term2;

    private ComparisonOperator op;

    public ComparisonAtom(Term term1, Term term2, ComparisonOperator op) {
        this.term1 = term1;
        this.term2 = term2;
        this.op = op;
    }

    public Term getTerm1() {
        return term1;
    }

    public Term getTerm2() {
        return term2;
    }

    public ComparisonOperator getOp() {
        return op;
    }

    /**
     * A method used to determine whether this comparison atom is contained
     * in a list of terms.
     * @param terms A list of terms that used to judge containment.
     * @return true if contained, false if not.
     */
    public boolean containedIn(List<Term> terms){
        // if both the terms in this comparison atom are constant
        // just return true
        if(term1 instanceof Constant && term2 instanceof  Constant){
            return true;
        }
        // else find all the variables and check whether it's a subset
        // of the given list of terms
        List<Variable> variablesInCA = new ArrayList<>();
        if(term1 instanceof  Variable){
            variablesInCA.add((Variable)term1);
        }
        if(term2 instanceof  Variable){
            variablesInCA.add((Variable)term2);
        }
        return  terms.containsAll(variablesInCA);
    }

    @Override
    public String toString() {
        return term1 + " " + op + " " + term2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComparisonAtom)) return false;

        ComparisonAtom that = (ComparisonAtom) o;

        if (getTerm1() != null ? !getTerm1().equals(that.getTerm1()) : that.getTerm1() != null) return false;
        if (getTerm2() != null ? !getTerm2().equals(that.getTerm2()) : that.getTerm2() != null) return false;
        return getOp() == that.getOp();
    }

    @Override
    public int hashCode() {
        int result = getTerm1() != null ? getTerm1().hashCode() : 0;
        result = 31 * result + (getTerm2() != null ? getTerm2().hashCode() : 0);
        result = 31 * result + (getOp() != null ? getOp().hashCode() : 0);
        return result;
    }
}
