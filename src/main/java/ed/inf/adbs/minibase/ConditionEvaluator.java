package ed.inf.adbs.minibase;

import ed.inf.adbs.minibase.base.*;
import ed.inf.adbs.minibase.base.operator.ComparisonOperator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is a util class that is used to evaluate conditions on tuple.
 */
public  class ConditionEvaluator{

    /**
     * Evaluate whether a tuple pass given lists of conditions.
     * @param conditions a list of comparision atoms
     * @param t tuple to evaluate.
     * @return true, if the tuple pass this conditions, else return false.
     */
    public static boolean evaluate(List<ComparisonAtom> conditions, Tuple t){
        boolean pass = true;
        for (ComparisonAtom ca:conditions) {
            if(!evalSingleCond(ca,t)){
                pass = false;
            }
        }
        return pass;
    }

    /**
     * Evaluate whether a tuple pass given comparison atom, a decomposition of evaluate method.
     * @param ca a list of comparison atoms
     * @param t tuple to evaluate.
     * @return true, if the tuple pass this condition, else return false.
     */
    public static boolean evalSingleCond(ComparisonAtom ca, Tuple t){
        // first step, analysing the comparison Atom
        Term term1 = ca.getTerm1();
        Term term2 = ca.getTerm2();
        ComparisonOperator op = ca.getOp();

        // build the mapping
        HashMap<Term,Object> mapping = new HashMap<>();
        List<Term> terms = t.getSchema().getAttributesNames();
        ArrayList<Object> attributes = t.getAttributes();
        for(int i=0; i<terms.size();i++){
            mapping.put(terms.get(i),attributes.get(i));
        }

        // Replacing terms in ComparisonAtom with tuple's attribute
        Term mappedTerm1 = null;
        if(!(term1 instanceof Constant)){
            Object obj1 = mapping.get(term1);
            if(obj1 instanceof String){
                String str = (String)obj1;
                str = str.replace("'","");
                mappedTerm1 = new StringConstant(str);
            }else{
                mappedTerm1 = new IntegerConstant((Integer) obj1);
            }
        }

        Term mappedTerm2 = null;
        if(!(term2 instanceof Constant)){
            Object obj2 = mapping.get(term2);
            if(obj2 instanceof String){
                String str = (String)obj2;
                str = str.replace("'","");
                mappedTerm2 = new StringConstant((String) obj2);
            }else{
                mappedTerm2 = new IntegerConstant((Integer) obj2);
            }
        }
        Term termToCompare1 = mappedTerm1!=null ? mappedTerm1 : term1;
        Term termToCompare2 = mappedTerm2!=null ? mappedTerm2 : term2;

        return compareTermsWithOp(termToCompare1,termToCompare2,op);
    }

    /**
     * Evaluate whether the comparison between two terms on the given comparison operator.
     * @param term1 First term to check.
     * @param term2 Second term to check.
     * @param op Given comparison operator
     * @return true, if this comparison is passed, else return false.
     */
    public static boolean compareTermsWithOp(Term term1, Term term2, ComparisonOperator op){
        // if the two terms provided are not the same types directly return false
        if(!term1.getClass().equals(term2.getClass())){
            return false;
        }
        // else cast them to their type and start the comparison
        if(term1 instanceof IntegerConstant){
            int value1 = ((IntegerConstant) term1).getValue();
            int value2 = ((IntegerConstant) term2).getValue();
            switch (op){
                case EQ:
                    return value1 == value2;
                case NEQ:
                    return value1 != value2;
                case GT:
                    return value1 > value2;
                case GEQ:
                    return value1 >= value2;
                case LT:
                    return value1 < value2;
                case LEQ:
                    return value1 <= value2;
            }
        }else{
            String s1 = ((StringConstant)term1).getValue();
            String s2 = ((StringConstant)term2).getValue();
            s1 = s1.replace("'", "");
            s2 = s2.replace("'", "");
            switch (op)
            {
                case EQ:
                    return s1.equals(s2);
                case NEQ:
                    return !s1.equals(s2);
                case GT:
                    return s1.compareTo(s2) > 0;
                case GEQ:
                    return s1.compareTo(s2) >= 0;
                case LT:
                    return s1.compareTo(s2) < 0;
                case LEQ:
                    return s1.compareTo(s2) <= 0;
            }
        }
        return false;
    }
}