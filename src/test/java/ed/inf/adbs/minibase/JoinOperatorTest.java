package ed.inf.adbs.minibase;

import ed.inf.adbs.minibase.base.*;
import ed.inf.adbs.minibase.base.operator.ComparisonOperator;
import ed.inf.adbs.minibase.base.operator.JoinOperator;
import ed.inf.adbs.minibase.base.operator.ScanOperator;
import ed.inf.adbs.minibase.parser.QueryParser;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JoinOperatorTest {

    @Test
    public void test(){
        Catalog catalog = Catalog.getInstance();
        catalog.init("data\\evaluation\\db","data\\evaluation\\input\\query1.txt","data\\evaluation\\output\\query1.txt");
        Query query = QueryParser.parse("Q(x, y, z, u, w, t) :- R(x, y, z), S(u, w, t)");
        RelationalAtom ra1= (RelationalAtom) query.getBody().get(0);
        RelationalAtom ra2= (RelationalAtom) query.getBody().get(1);
        ScanOperator scanOperator1 = new ScanOperator(ra1);
        ScanOperator scanOperator2 = new ScanOperator(ra2);

        ComparisonAtom ca = new ComparisonAtom(new Variable("x"),new Variable("u"), ComparisonOperator.EQ);
        List<ComparisonAtom> conds = new ArrayList<>();
        conds.add(ca);
        JoinOperator joinOp1 = new JoinOperator(scanOperator1,scanOperator2,conds);
        joinOp1.dump();
    }

    @Test
    public void testGNT(){
        Catalog catalog = Catalog.getInstance();
        catalog.init("data\\evaluation\\db","data\\evaluation\\input\\query1.txt","data\\evaluation\\output\\query1.txt");
        Query query = QueryParser.parse("Q(x, y, z, u, w, t) :- R(x, y, z), S(u, w, t)");
        RelationalAtom ra1= (RelationalAtom) query.getBody().get(0);
        RelationalAtom ra2= (RelationalAtom) query.getBody().get(1);
        ScanOperator scanOperator1 = new ScanOperator(ra1);
        ScanOperator scanOperator2 = new ScanOperator(ra2);

        ComparisonAtom ca = new ComparisonAtom(new Variable("x"),new Variable("u"), ComparisonOperator.EQ);
        List<ComparisonAtom> conds = new ArrayList<>();
        conds.add(ca);
        JoinOperator joinOp1 = new JoinOperator(scanOperator1,scanOperator2,conds);
//        System.out.println(joinOp1.getNextTuple());
//        System.out.println(joinOp1.getNextTuple());
//        System.out.println(joinOp1.getNextTuple());
//        System.out.println(joinOp1.getNextTuple());
//        System.out.println(joinOp1.getNextTuple());
//        System.out.println(joinOp1.getNextTuple());
        joinOp1.dump();
    }

}
