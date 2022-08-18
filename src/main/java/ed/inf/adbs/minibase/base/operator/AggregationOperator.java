package ed.inf.adbs.minibase.base.operator;

import ed.inf.adbs.minibase.*;
import ed.inf.adbs.minibase.base.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * This class abstracts the concept of aggregation operators and should be extended for each specific
 * aggregation operator. It defines the fields all the aggregation operators need and provide their
 * getters and setters.
 */
public class AggregationOperator extends Operator {
    private Catalog catalog = Catalog.getInstance();
    private Operator child;
    private AggregateVariable aggVariable;
    private List<Term> groupVariables;
    private LinkedHashMap<Tuple, AggregationStat> stats = new LinkedHashMap<>();

    public AggregationOperator(Operator child, AggregateVariable aggVariable, List<Term> groupVariables) {
        this.child = child;
        this.aggVariable = aggVariable;
        this.groupVariables = groupVariables;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public Operator getChild() {
        return child;
    }

    public void setChild(Operator child) {
        this.child = child;
    }

    public AggregateVariable getAggVariable() {
        return aggVariable;
    }

    public void setAggVariable(AggregateVariable aggVariable) {
        this.aggVariable = aggVariable;
    }

    public List<Term> getGroupVariables() {
        return groupVariables;
    }

    public void setGroupVariables(List<Term> groupVariables) {
        this.groupVariables = groupVariables;
    }

    public LinkedHashMap<Tuple, AggregationStat> getStats() {
        return stats;
    }

    public void setStats(LinkedHashMap<Tuple, AggregationStat> stats) {
        this.stats = stats;
    }

    @Override
    public Tuple getNextTuple() {
        return null;
    }

    @Override
    public void reset() {

    }

    @Override
    public void dump() {

    }

    /**
     * This method is used to extract a new tuple with group-by variables from the given tuple.
     * @param t the given tuple to be extracted.
     * @return a new tuple that only contains the group-by variables.
     */
    public Tuple getTupleWithGroupVaribales(Tuple t) {

        // get the schema and attributes of tuple
        Schema pre_schema = t.getSchema();
        List<Term> prev_names = pre_schema.getAttributesNames();
        List<String> pre_Types = pre_schema.getAttributesTypes();
        ArrayList<Object> prev_attributes = t.getAttributes();

        ArrayList<Object> new_attributes = new ArrayList<>();
        ArrayList<String> new_Types = new ArrayList<>();

        for (Term var:groupVariables) {
            new_attributes.add(prev_attributes.get(prev_names.indexOf(var)));
            new_Types.add(pre_Types.get(prev_names.indexOf(var)));
        }

        return new Tuple(new Schema(groupVariables,new_Types),new_attributes);
    }


}
