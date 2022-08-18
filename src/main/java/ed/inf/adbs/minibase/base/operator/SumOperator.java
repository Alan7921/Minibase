package ed.inf.adbs.minibase.base.operator;

import ed.inf.adbs.minibase.Utils;
import ed.inf.adbs.minibase.base.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class abstracts the sum aggregation operator. It would calculate the sum value of aggregated variable,
 * for each distinct group-by variables group.
 */
public class SumOperator extends AggregationOperator {
    private int singleSum = 0;

    public SumOperator(Operator child, AggregateVariable aggVariable, List<Term> groupVariables) {
        super(child, aggVariable, groupVariables);
    }

    @Override
    public Tuple getNextTuple() {
        return null;
    }

    @Override
    public void reset() {

    }

    /**
     * This method is used to dump the groups of tuple and their sum value.
     */
    @Override
    public void dump(){
        Tuple tuple = null;

        // if no group-by variables are provided just return a single avg value
        if(this.getGroupVariables().size() == 0){
            int sum = 0;
            while((tuple = this.getChild().getNextTuple()) != null){
                sum += (Integer) tuple.getAttrWithTerm(new Variable(this.getAggVariable().getName()));
            }
            singleSum = sum;
        }else{
            LinkedHashMap<Tuple, AggregationStat> stats= getStats();

            while((tuple = this.getChild().getNextTuple()) != null){
                //
                Tuple tupleForGroup = getTupleWithGroupVaribales(tuple);
                int aggregatedAttr = (Integer) tuple.getAttrWithTerm(new Variable(this.getAggVariable().getName()));

                if (stats.containsKey(tupleForGroup)) {
                    SumSat stat = (SumSat) stats.get(tupleForGroup);
                    stat.setSum(stat.getSum()+aggregatedAttr);
                } else {
                    stats.put(tupleForGroup,new SumSat(aggregatedAttr));
                }
            }
        }
        writeFileback();
    }

    /**
     * This method is used to write tuples and their average values back to output file.
     */
    private void writeFileback(){
        // if only single value need to be written
        if(this.getGroupVariables().size() == 0){
            FileWriter fw = null;
            BufferedWriter bw = null;
            try {
                fw = new FileWriter(this.getCatalog().getOutputFile(),true);
                bw= new BufferedWriter(fw);
                bw.write(Integer.toString(singleSum));
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(bw != null){
                    try {
                        bw.close();
                    } catch (IOException e) {
                        System.err.println("Exception occurred during writing query back to file");
                        e.printStackTrace();
                    }
                }
            }
        }else{
            FileWriter fw = null;
            BufferedWriter bw = null;
            try {
                fw = new FileWriter(this.getCatalog().getOutputFile(),true);
                bw= new BufferedWriter(fw);

                Iterator<Map.Entry<Tuple,AggregationStat>> entries = this.getStats().entrySet().iterator();
                while (entries.hasNext()){
                    Map.Entry<Tuple, AggregationStat> entry = entries.next();
                    Tuple t = entry.getKey();
                    String str = Utils.join(t.getAttributes(),", ");
                    bw.write(str +", "+ ((SumSat)(this.getStats().get(t))).getSum());
                    if(entries.hasNext()){
                        bw.newLine();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(bw != null){
                    try {
                        bw.close();
                    } catch (IOException e) {
                        System.err.println("Exception occurred during writing query back to file");
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
