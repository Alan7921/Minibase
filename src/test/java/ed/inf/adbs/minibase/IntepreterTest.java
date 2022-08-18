package ed.inf.adbs.minibase;

import org.junit.Test;

import java.util.Arrays;

public class IntepreterTest {
    @Test
    public void generalTest(){
        Catalog catalog = Catalog.getInstance();
        catalog.init("data\\evaluation\\db","data\\evaluation\\input\\query1.txt","data\\evaluation\\output\\query1.txt");
        Intepreter it = new Intepreter();
        it.dump();
    }

    @Test
    public void test2(){
        Catalog catalog = Catalog.getInstance();
        catalog.init("data\\evaluation\\db","data\\evaluation\\input\\query1.txt","data\\evaluation\\output\\query1.csv");
        //Query query = QueryParser.parse("Q(x, SUM(y)) :- R(x, y, z), S(x, w, t)");
        Intepreter it = new Intepreter();
        it.dump();
    }



    @Test
    public void testUtils(){
        String[] strs = {"x","y","z"};
        System.out.println(Utils.join(Arrays.asList(strs), ","));
    }
}
