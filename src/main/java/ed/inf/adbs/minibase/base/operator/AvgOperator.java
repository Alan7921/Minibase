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
 * This class abstracts the average aggregation operator. It would calculate the average value of aggregated variable,
 * for each distinct group-by variables group.
 */
public class AvgOperator extends AggregationOperator {
    private LinkedHashMap<Tuple,Integer> avgs = null;
    private int singleAvgValue = 0;

    public AvgOperator(Operator child, AggregateVariable aggVariable, List<Term> groupVariables) {
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
     * This method is used to dump the groups of tuple and their average value.
     */
    public void dump(){
        Tuple tuple = null;
        // if no group-by variables are provided just return a single avg value
        if(this.getGroupVariables().size() == 0){
            int count = 0,sum = 0;
            while((tuple = this.getChild().getNextTuple()) != null){
                sum += (Integer) tuple.getAttrWithTerm(new Variable(this.getAggVariable().getName()));
                count ++;
            }
            singleAvgValue = (int) Math.round((double) sum/count);;
        }else{
            LinkedHashMap<Tuple, AggregationStat> stats= getStats();

            while((tuple = this.getChild().getNextTuple()) != null){
                //
                Tuple tupleForGroup = getTupleWithGroupVaribales(tuple);
                int aggregatedAttr = (Integer) tuple.getAttrWithTerm(new Variable(this.getAggVariable().getName()));

                if (stats.containsKey(tupleForGroup)) {
                    AvgStat stat = (AvgStat) stats.get(tupleForGroup);
                    stat.setCount(stat.getCount()+1);
                    stat.setSum(stat.getSum()+aggregatedAttr);
                } else {
                    stats.put(tupleForGroup,new AvgStat(aggregatedAttr,1));
                }
            }
            avgs = new LinkedHashMap<>();
            // full scan the stats get average values for each group of tuple
            for (Tuple t :stats.keySet()) {
                int sum = ((AvgStat)(stats.get(t))).getSum();
                int count = ((AvgStat)(stats.get(t))).getCount();
                int avgNum = (int) Math.round(1.0*sum/count);
                avgs.put(t,new Integer(avgNum));
            }
        }
        writeBack();
    }

    /**
     * This method is used to write tuples and their average values back to output file.
     */
    private void writeBack(){
        // if only single value need to be written
        if(avgs == null){
            FileWriter fw = null;
            BufferedWriter bw = null;
            try {
                fw = new FileWriter(this.getCatalog().getOutputFile(),true);
                bw= new BufferedWriter(fw);
                bw.write(Integer.toString(singleAvgValue));
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

                Iterator<Map.Entry<Tuple,Integer>> entries = avgs.entrySet().iterator();
                while (entries.hasNext()){
                    Map.Entry<Tuple, Integer> entry = entries.next();
                    Tuple t = entry.getKey();
                    String str = Utils.join(t.getAttributes(),", ");
                    bw.write(str +", "+ avgs.get(t));
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
