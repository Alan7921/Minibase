package ed.inf.adbs.minibase;

import org.junit.Test;

/**
 * Unit test for Minibase.
 */

public class MinibaseTest {

    /**
     * Rigorous Test :-)
     */

    @Test
    public void evaluateCQtest1(){
        String dbDir = "data\\evaluation\\db";
        String inputFile = "data\\evaluation\\input\\query1.txt";
        String outputFile = "data\\evaluation\\output\\query1.csv";
        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
    }
//    @Test
//    public void evaluateCQtest2(){
//        String dbDir = "data\\evaluation\\db";
//        String inputFile = "data\\evaluation\\input\\query2.txt";
//        String outputFile = "data\\evaluation\\output\\query2.csv";
//        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
//    }
//    @Test
//    public void evaluateCQtest3(){
//        String dbDir = "data\\evaluation\\db";
//        String inputFile = "data\\evaluation\\input\\query3.txt";
//        String outputFile = "data\\evaluation\\output\\query3.csv";
//        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
//    }
//    @Test
//    public void evaluateCQtest4(){
//        String dbDir = "data\\evaluation\\db";
//        String inputFile = "data\\evaluation\\input\\query4.txt";
//        String outputFile = "data\\evaluation\\output\\query4.csv";
//        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
//    }
//    @Test
//    public void evaluateCQtest5(){
//        String dbDir = "data\\evaluation\\db";
//        String inputFile = "data\\evaluation\\input\\query5.txt";
//        String outputFile = "data\\evaluation\\output\\query5.csv";
//        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
//    }
//    @Test
//    public void evaluateCQtest6(){
//        String dbDir = "data\\evaluation\\db";
//        String inputFile = "data\\evaluation\\input\\query6.txt";
//        String outputFile = "data\\evaluation\\output\\query6.csv";
//        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
//    }
//    @Test
//    public void evaluateCQtest7(){
//        String dbDir = "data\\evaluation\\db";
//        String inputFile = "data\\evaluation\\input\\query7.txt";
//        String outputFile = "data\\evaluation\\output\\query7.csv";
//        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
//    }
//    @Test
//    public void evaluateCQtest8(){
//        String dbDir = "data\\evaluation\\db";
//        String inputFile = "data\\evaluation\\input\\query8.txt";
//        String outputFile = "data\\evaluation\\output\\query8.csv";
//        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
//    }
//    @Test
//    public void evaluateCQtest9(){
//        String dbDir = "data\\evaluation\\db";
//        String inputFile = "data\\evaluation\\input\\query9.txt";
//        String outputFile = "data\\evaluation\\output\\query9.csv";
//        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
//    }

//    @Test
//    public void evaluateCQOnDB2test1(){
//        String dbDir = "data\\evaluation\\db2";
//        String inputFile = "data\\evaluation\\input2\\query1.txt";
//        String outputFile = "data\\evaluation\\output2\\query1.csv";
//        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
//    }
//
//    @Test
//    public void evaluateCQOnDB2test2(){
//        String dbDir = "data\\evaluation\\db2";
//        String inputFile = "data\\evaluation\\input2\\query2.txt";
//        String outputFile = "data\\evaluation\\output2\\query2.csv";
//        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
//    }
//    @Test
//    public void evaluateCQOnDB2test3(){
//        String dbDir = "data\\evaluation\\db2";
//        String inputFile = "data\\evaluation\\input2\\query3.txt";
//        String outputFile = "data\\evaluation\\output2\\query3.csv";
//        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
//    }
//    @Test
//    public void evaluateCQOnDB2test4(){
//        String dbDir = "data\\evaluation\\db2";
//        String inputFile = "data\\evaluation\\input2\\query4.txt";
//        String outputFile = "data\\evaluation\\output2\\query4.csv";
//        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
//    }
//    @Test
//    public void evaluateCQOnDB2test5(){
//        String dbDir = "data\\evaluation\\db2";
//        String inputFile = "data\\evaluation\\input2\\query5.txt";
//        String outputFile = "data\\evaluation\\output2\\query5.csv";
//        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
//    }
//    @Test
//    public void evaluateCQOnDB2test6(){
//        String dbDir = "data\\evaluation\\db2";
//        String inputFile = "data\\evaluation\\input2\\query6.txt";
//        String outputFile = "data\\evaluation\\output2\\query6.csv";
//        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
//    }
//    @Test
//    public void evaluateCQOnDB2test7(){
//        String dbDir = "data\\evaluation\\db2";
//        String inputFile = "data\\evaluation\\input2\\query7.txt";
//        String outputFile = "data\\evaluation\\output2\\query7.csv";
//        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
//    }
//    @Test
//    public void evaluateCQOnDB2test8(){
//        String dbDir = "data\\evaluation\\db2";
//        String inputFile = "data\\evaluation\\input2\\query8.txt";
//        String outputFile = "data\\evaluation\\output2\\query8.csv";
//        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
//    }
//    @Test
//    public void evaluateCQOnDB2test9(){
//        String dbDir = "data\\evaluation\\db2";
//        String inputFile = "data\\evaluation\\input2\\query9.txt";
//        String outputFile = "data\\evaluation\\output2\\query9.csv";
//        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
//    }
//    @Test
//    public void evaluateCQOnDB2test10(){
//        String dbDir = "data\\evaluation\\db2";
//        String inputFile = "data\\evaluation\\input2\\query10.txt";
//        String outputFile = "data\\evaluation\\output2\\query10.csv";
//        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
//    }
//    @Test
//    public void evaluateCQOnDB2test11(){
//        String dbDir = "data\\evaluation\\db2";
//        String inputFile = "data\\evaluation\\input2\\query11.txt";
//        String outputFile = "data\\evaluation\\output2\\query11.csv";
//        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
//    }

        @Test
    public void evaluateCQOnDB3test01(){
        String dbDir = "data\\evaluation\\db3";
        String inputFile = "data\\evaluation\\input3\\query01.txt";
        String outputFile = "data\\evaluation\\output3\\query01.csv";
        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
    }

    @Test
    public void evaluateCQOnDB3test02(){
        String dbDir = "data\\evaluation\\db3";
        String inputFile = "data\\evaluation\\input3\\query02.txt";
        String outputFile = "data\\evaluation\\output3\\query02.csv";
        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
    }

    @Test
    public void evaluateCQOnDB3test03(){
        String dbDir = "data\\evaluation\\db3";
        String inputFile = "data\\evaluation\\input3\\query03.txt";
        String outputFile = "data\\evaluation\\output3\\query03.csv";
        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
    }

    @Test
    public void evaluateCQOnDB3test04(){
        String dbDir = "data\\evaluation\\db3";
        String inputFile = "data\\evaluation\\input3\\query04.txt";
        String outputFile = "data\\evaluation\\output3\\query04.csv";
        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
    }

    @Test
    public void evaluateCQOnDB3test05(){
        String dbDir = "data\\evaluation\\db3";
        String inputFile = "data\\evaluation\\input3\\query05.txt";
        String outputFile = "data\\evaluation\\output3\\query05.csv";
        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
    }

    @Test
    public void evaluateCQOnDB3test06(){
        String dbDir = "data\\evaluation\\db3";
        String inputFile = "data\\evaluation\\input3\\query06.txt";
        String outputFile = "data\\evaluation\\output3\\query06.csv";
        Minibase.evaluateCQ(dbDir,inputFile,outputFile);
    }
}

