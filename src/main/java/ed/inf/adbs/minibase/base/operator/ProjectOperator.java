package ed.inf.adbs.minibase.base.operator;

import ed.inf.adbs.minibase.Catalog;
import ed.inf.adbs.minibase.base.Schema;
import ed.inf.adbs.minibase.base.Tuple;
import ed.inf.adbs.minibase.Utils;
import ed.inf.adbs.minibase.base.Term;

import java.util.ArrayList;
import java.util.List;

/**
 * This class takes the responsibility of abstracting projection operation.
 * Since the set semantics are applied here, it maintains a list that
 * records the tuple that has been returned.
 */
public class ProjectOperator extends Operator {
    private Operator child;
    private List<Tuple> buffer = new ArrayList<>();
    private List<Term> projectionVariables;
    private Catalog catalog = Catalog.getInstance();

    public ProjectOperator(List<Term> projVariables, Operator child){
        this.projectionVariables = projVariables;
        this.child = child;
    }

    /**
     * This method is used to get next tuple from its child operator,
     * if child operator still has tuple, then get it and conduct
     * the projection on it. Check if this tuple has been returned
     * already, if not add it to buffer and return it, else, grasp next
     * tuple from child, till it returns null.
     */
    @Override
    public Tuple getNextTuple() {
        Tuple t = null;
        while((t = child.getNextTuple())!=null){
            // project on it
            Tuple projected_tuple = project(t);
            if(!buffer.contains(projected_tuple)){
                buffer.add(projected_tuple);
                return projected_tuple;
            }
        }
        return null;
    }

    /**
     * This method is used to get the projected tuple.
     * @param t the tuple provided to project on.
     * @return the tuple after projection
     */
    private Tuple project(Tuple t) {
        // get the schema and attributes of the provided tuple
        Schema pre_schema = t.getSchema();
        List<Term> prev_names = pre_schema.getAttributesNames();
        List<String> pre_Types = pre_schema.getAttributesTypes();
        ArrayList<Object> prev_attributes = t.getAttributes();

        // create list for remained attributes and their types
        ArrayList<Object> new_attributes = new ArrayList<>();
        ArrayList<String> new_Types = new ArrayList<>();

        // only remain those free variables in head
        for (Term var:projectionVariables) {
            new_attributes.add(prev_attributes.get(prev_names.indexOf(var)));
            new_Types.add(pre_Types.get(prev_names.indexOf(var)));
        }
        return new Tuple(new Schema(projectionVariables,new_Types),new_attributes);
    }

    /**
     * This method is used reset this operator,
     * especially, used to reset its child operator
     * and its buffer here.
     */
    @Override
    public void reset() {
        buffer = new ArrayList<>();
        child.reset();
    }

    /**
     * This method is used dump the projected tuples,
     * calling the writeFile method in Utils to write
     * these tuples back to output file path.
     * @see ed.inf.adbs.minibase.Utils#writeFile(Tuple, String)
     */
    @Override
    public void dump() {
        Tuple t = null;
        while((t = child.getNextTuple())!=null){
            // project on it
            Tuple projected_tuple = project(t);
            if(!buffer.contains(projected_tuple)){
                buffer.add(projected_tuple);
                Utils.writeFile(projected_tuple,catalog.getOutputFile());
            }
        }
    }

    public Operator getChild() {
        return child;
    }
}
