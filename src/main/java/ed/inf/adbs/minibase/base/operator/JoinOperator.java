package ed.inf.adbs.minibase.base.operator;

import ed.inf.adbs.minibase.Catalog;
import ed.inf.adbs.minibase.ConditionEvaluator;
import ed.inf.adbs.minibase.Utils;
import ed.inf.adbs.minibase.base.ComparisonAtom;
import ed.inf.adbs.minibase.base.Tuple;

import java.util.List;

/**
 * This class abstracts the concept of join operator.
 * It has two child operator the left one and the right one.
 * The implemented algorithm here is the simple nested loop join.
 */

public class JoinOperator extends Operator {
    private Operator left_child;
    private Operator right_child;
    private List<ComparisonAtom> conds;
    private Tuple left_tuple = null;
    private Tuple right_tuple = null;
    private Catalog catalog = Catalog.getInstance();

    public JoinOperator(Operator left_child, Operator right_child, List<ComparisonAtom> predicates){
        this.left_child = left_child;
        this.right_child = right_child;
        this.conds = predicates;
        this.left_tuple = left_child.getNextTuple();
        this.right_tuple = right_child.getNextTuple();
    }

    /**
     * This method is used to get next tuple.
     * It follows the simple nested loop join algorithm.
     * The left child is in outer and the right child is in inner.
     * Each time this method is called, it would grasp tuples from both the left and the right operator.
     * If there are no join conditions, it would directly return the merged tuple. Otherwise, it
     * would call ConditionEvaluator's evaluate method to check whether the merged tuple satisfy it.
     * Whenever the inner loop arrive its end, reset it and advance the left operator.
     * When the left operator touch its end, return null.
     * @see ed.inf.adbs.minibase.ConditionEvaluator#evaluate(List, Tuple)
     */
    @Override
    public Tuple getNextTuple() {
        Tuple merged_tuple;

        while (left_tuple != null) {
            merged_tuple = new Tuple(left_tuple, right_tuple);

            // update right
            if ((right_tuple = right_child.getNextTuple()) == null) {
                right_child.reset();
                right_tuple = right_child.getNextTuple();
                left_tuple = left_child.getNextTuple();
            }

            if ((conds != null) && (conds.size() != 0)) {
                if (ConditionEvaluator.evaluate(conds, merged_tuple)) {
                    return merged_tuple;
                }
            } else {
                return merged_tuple;

            }
        }
        return null;
    }

    /**
     * This method is used reset this operator,
     * especially, used to reset its left and right child operator.
     */
    @Override
    public void reset() {
        left_child.reset();
        right_child.reset();
    }

    /**
     * This method is used to dump the tuples merged and satisfy the join conditions,
     * calling the writeFile method in Utils to write these tuples back to output file path.
     * @see ed.inf.adbs.minibase.Utils#writeFile(Tuple, String)
     */
    @Override
    public void dump() {
        Tuple merged_tuple = null;
        while (left_tuple != null) {
            merged_tuple = new Tuple(left_tuple, right_tuple);

            // update right
            if ((right_tuple = right_child.getNextTuple()) == null) {
                right_child.reset();
                right_tuple = right_child.getNextTuple();
                left_tuple = left_child.getNextTuple();
            }
            if ((conds != null) && (conds.size() != 0)) {
                if (ConditionEvaluator.evaluate(conds, merged_tuple)) {
                    Utils.writeFile(merged_tuple,catalog.getOutputFile());
                }
            } else {
                Utils.writeFile(merged_tuple,catalog.getOutputFile());
            }
        }
    }

}
