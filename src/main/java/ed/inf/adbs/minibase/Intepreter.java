package ed.inf.adbs.minibase;

import ed.inf.adbs.minibase.base.Query;
import ed.inf.adbs.minibase.base.operator.Operator;
import ed.inf.adbs.minibase.parser.QueryParser;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * This class takes the responsibility of interpreting query from
 * the given filepath and put it into the query plan generator.
 */
public class Intepreter {
    Generator generator;
    Catalog catalog = Catalog.getInstance();

    public Intepreter(){
        Query query = null;
        try {
            query = QueryParser.parse(Paths.get(catalog.getInputFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        generator = new Generator(query);
    }

    public void dump(){
        Operator root = generator.generateQueryPlan();
        root.dump();
    }
}
