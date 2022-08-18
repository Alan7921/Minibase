package ed.inf.adbs.minibase;

import ed.inf.adbs.minibase.base.Atom;
import ed.inf.adbs.minibase.base.ComparisonAtom;
import ed.inf.adbs.minibase.base.Query;
import ed.inf.adbs.minibase.base.RelationalAtom;
import ed.inf.adbs.minibase.base.operator.ScanOperator;
import ed.inf.adbs.minibase.base.operator.SelectOperator;
import ed.inf.adbs.minibase.parser.QueryParser;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SelectOperatorTest {

    @Test
    public void testEvaluateIntegerCondition() {

        Catalog catalog = Catalog.getInstance();
        catalog.init("data\\evaluation\\db","data\\evaluation\\input\\query1.txt","data\\evaluation\\output\\query1.txt");
        //Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), 3 = 5");
        //Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), 3 != 5");
        //Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), 3 < 7");
        //Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), 3 > 7");
        //Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), 3 >= 7");
        Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), 3 <= 7");
        RelationalAtom head = query.getHead();
        List<Atom> body = query.getBody();
        RelationalAtom ra = (RelationalAtom) body.get(0);

        ArrayList<ComparisonAtom> conditions = new ArrayList<>();
        for (Atom atom : body) {
            if (atom instanceof ComparisonAtom) {
                conditions.add((ComparisonAtom) atom);
            }
        }
        ScanOperator scanOperator = new ScanOperator(ra);
        SelectOperator selectOperator = new SelectOperator(scanOperator, conditions, ra);
        selectOperator.dump();
    }

        @Test
        public void testEvaluateIntegerCondition2() {

            Catalog catalog = Catalog.getInstance();
            catalog.init("data\\evaluation\\db","data\\evaluation\\input\\query1.txt","data\\evaluation\\output\\query1.txt");
            Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), x = 5");
            //Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), x != 5");
            //Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), x < 7");
            //Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), x > 7");
            //Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), x >= 7");
            //Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), x <= 7");
            RelationalAtom head = query.getHead();
            List<Atom> body = query.getBody();
            RelationalAtom ra = (RelationalAtom) body.get(0);

            ArrayList<ComparisonAtom> conditions = new ArrayList<>();
            for (Atom atom : body) {
                if (atom instanceof ComparisonAtom) {
                    conditions.add((ComparisonAtom) atom);
                }
            }
            ScanOperator scanOperator = new ScanOperator(ra);
            SelectOperator selectOperator = new SelectOperator(scanOperator, conditions, ra);
            selectOperator.dump();
        }


            @Test
            public void testEvaluateStringCondition1() {

                Catalog catalog = Catalog.getInstance();
                catalog.init("data\\evaluation\\db","data\\evaluation\\input\\query1.txt","data\\evaluation\\output\\query1.txt");
                Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), 'adbs' = 'adbs'");
                //Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), 'adbs' != 'adbs'");
                //Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), 'adbs' < 'adbs'");
                //Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), 'adbs' > 'adbs'");
                //Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), 'adbs' >= 'adbs'");
                //Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), 'adbs' <= 'adbs'");
                RelationalAtom head = query.getHead();
                List<Atom> body = query.getBody();
                RelationalAtom ra = (RelationalAtom) body.get(0);

                ArrayList<ComparisonAtom> conditions = new ArrayList<>();
                for (Atom atom : body) {
                    if (atom instanceof ComparisonAtom) {
                        conditions.add((ComparisonAtom) atom);
                    }
                }
                ScanOperator scanOperator = new ScanOperator(ra);
                SelectOperator selectOperator = new SelectOperator(scanOperator, conditions, ra);
                selectOperator.dump();
            }

        @Test
        public void testEvaluateStringCondition2() {

            Catalog catalog = Catalog.getInstance();
            catalog.init("data\\evaluation\\db","data\\evaluation\\input\\query1.txt","data\\evaluation\\output\\query1.txt");
            //Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), z = 'adbs'");
            //Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), z != 'adbs'");
            //Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), z < 'adbs'");
            //Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), z > 'adbs'");
            //Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), z >= 'adbs'");
            Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), z <= 'adbs'");
            RelationalAtom head = query.getHead();
            List<Atom> body = query.getBody();
            RelationalAtom ra = (RelationalAtom) body.get(0);

            ArrayList<ComparisonAtom> conditions = new ArrayList<>();
            for (Atom atom : body) {
                if (atom instanceof ComparisonAtom) {
                    conditions.add((ComparisonAtom) atom);
                }
            }
            ScanOperator scanOperator = new ScanOperator(ra);
            SelectOperator selectOperator = new SelectOperator(scanOperator, conditions, ra);
            selectOperator.dump();
        }

        @Test
        public void selectOperatorTest(){
            Catalog catalog = Catalog.getInstance();
            catalog.init("data\\evaluation\\db","data\\evaluation\\input\\query1.txt","data\\evaluation\\output\\query1.txt");
            //Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), z = 'adbs'");
            Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), z != 'adbs'");
            //Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), z < 'adbs'");
            //Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), z > 'adbs'");
            //Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), z >= 'adbs'");
            //Query query = QueryParser.parse("Q(x,y,z) :- R(x, y, z), z <= 'adbs'");
            RelationalAtom head = query.getHead();
            List<Atom> body = query.getBody();
            RelationalAtom ra = (RelationalAtom) body.get(0);

            ArrayList<ComparisonAtom> conditions = new ArrayList<>();
            for (Atom atom : body) {
                if (atom instanceof ComparisonAtom) {
                    conditions.add((ComparisonAtom) atom);
                }
            }
            ScanOperator scanOperator = new ScanOperator(ra);
            SelectOperator selectOperator = new SelectOperator(scanOperator, conditions, ra);
            selectOperator.dump();
        }

    }

