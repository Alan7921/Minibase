package ed.inf.adbs.minibase;

import ed.inf.adbs.minibase.base.Query;
import ed.inf.adbs.minibase.base.RelationalAtom;
import ed.inf.adbs.minibase.base.Tuple;
import ed.inf.adbs.minibase.base.operator.ScanOperator;
import ed.inf.adbs.minibase.parser.QueryParser;
import org.junit.Test;

public class ScanOperatorTest {
    @Test
    public void testScanOperatorDump(){
        Catalog catalog = Catalog.getInstance();
        catalog.init("data\\evaluation\\db","data\\evaluation\\input\\query1.txt","data\\evaluation\\output\\query1.txt");
        Query query = QueryParser.parse("Q(x,y,z) :- T(x, y, z)");
        RelationalAtom ra= (RelationalAtom) query.getBody().get(0);
        ScanOperator scanOperator = new ScanOperator(ra);
        scanOperator.dump();
    }

    @Test
    public void testScanOperatorGetNextTuple(){
        Catalog catalog = Catalog.getInstance();
        catalog.init("data\\evaluation\\db","data\\evaluation\\input\\query1.txt","data\\evaluation\\output\\query1.txt");
        Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z)");
        RelationalAtom ra= (RelationalAtom) query.getBody().get(0);
        ScanOperator scanOperator = new ScanOperator(ra);

        Tuple t = null;
        while((t =scanOperator.getNextTuple())!=null){
            System.out.println(t);
        }
    }

    @Test
    public void testScanOperatorReset(){
        Catalog catalog = Catalog.getInstance();
        catalog.init("data\\evaluation\\db","data\\evaluation\\input\\query1.txt","data\\evaluation\\output\\query1.txt");
        Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z)");
        RelationalAtom ra= (RelationalAtom) query.getBody().get(0);
        ScanOperator scanOperator = new ScanOperator(ra);

        Tuple t = null;
        while((t =scanOperator.getNextTuple())!=null){
            System.out.println(t);
        }
        System.out.println("***********************");
        scanOperator.reset();
        while((t =scanOperator.getNextTuple())!=null){
            System.out.println(t);
        }
    }

    @Test
    public void testScanOperatorReset2(){
        Catalog catalog = Catalog.getInstance();
        catalog.init("data\\evaluation\\db","data\\evaluation\\input\\query1.txt","data\\evaluation\\output\\query1.txt");
        Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z)");
        RelationalAtom ra= (RelationalAtom) query.getBody().get(0);
        ScanOperator scanOperator = new ScanOperator(ra);

        Tuple t = null;
        int count= 0;
        while((t =scanOperator.getNextTuple())!=null){
            System.out.println(t);
            count++;
            if(count == 2){
                System.out.println("Break here!");
                break;
            }
        }

        scanOperator.reset();
        while((t =scanOperator.getNextTuple())!=null){
            System.out.println(t);
        }
    }
}
