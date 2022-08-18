package ed.inf.adbs.minibase.base.operator;

import ed.inf.adbs.minibase.Catalog;
import ed.inf.adbs.minibase.ConditionEvaluator;
import ed.inf.adbs.minibase.Utils;
import ed.inf.adbs.minibase.base.ComparisonAtom;
import ed.inf.adbs.minibase.base.RelationalAtom;
import ed.inf.adbs.minibase.base.Tuple;

import java.util.List;

/**
 * This class takes the responsibility of select tuples with given conditions.
 */
public class SelectOperator extends Operator {
    private List<ComparisonAtom> conditions;
    private Operator child;
    private Catalog catalog = Catalog.getInstance();

    public SelectOperator(Operator child, List<ComparisonAtom> conditions, RelationalAtom relationalAtom){
        this.child = child;
        this.conditions = conditions;
    }

    public Operator getChild() {
        return child;
    }

    /**
     * This method is used to get next tuple from child operator,
     * if child operator still has tuple, then get it and
     * throw it to the evaluate method of ConditionEvaluator.
     * If tuple passes these conditions then return it, else
     * grasp next tuple from child, until no tuple left.
     * @see ed.inf.adbs.minibase.ConditionEvaluator#evaluate(List, Tuple)
     */
    @Override
    public Tuple getNextTuple() {
        Tuple t = null;
        while((t = child.getNextTuple())!=null){
            //check whether tuple t satisfy the condition
            if(ConditionEvaluator.evaluate(conditions,t)){
                return t;
            }
        }
        return null;
    }

    /**
     * This method is used reset this operator,
     * especially, used to reset its child operator.
     */
    @Override
    public void reset() {
        child.reset();
    }

    /**
     * This method is used to dump the tuples that pass the conditions,
     * calling the writeFile method in Utils to write
     * these tuples back to output file path.
     * @see ed.inf.adbs.minibase.Utils#writeFile(Tuple, String)
     */
    @Override
    public void dump() {
        Tuple t = null;
        while((t = child.getNextTuple())!=null){
            //check whether tuple t satisfy the condition
            if(ConditionEvaluator.evaluate(conditions,t)){
                Utils.writeFile(t, catalog.getOutputFile());
            }
        }
    }
}
