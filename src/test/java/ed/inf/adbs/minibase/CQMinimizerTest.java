package ed.inf.adbs.minibase;

import org.junit.Test;

public class CQMinimizerTest {
    @Test
    public void test(){
        CQMinimizer.minimizeCQ("data/minimization/input/query1.txt", "data/minimization/output/query1.txt");
    }
}
