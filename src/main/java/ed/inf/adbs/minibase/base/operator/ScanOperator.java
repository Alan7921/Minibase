package ed.inf.adbs.minibase.base.operator;

import ed.inf.adbs.minibase.Catalog;
import ed.inf.adbs.minibase.base.Schema;
import ed.inf.adbs.minibase.base.Tuple;
import ed.inf.adbs.minibase.Utils;
import ed.inf.adbs.minibase.base.RelationalAtom;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class takes the responsibility of scanning records and create tuples from relation files.
 */
public class ScanOperator extends Operator {
    private RelationalAtom relationalAtom;
    private Catalog catalog = Catalog.getInstance();
    private Scanner scanner= null;
    private Schema schema;

    public ScanOperator(RelationalAtom ra){
        this.relationalAtom = ra;
        init();
    }

    /**
     * Initialization method, need to scan the schema to find the types of tuples to be created.
     */
    private void init(){
        ArrayList<String> attributesType = new ArrayList<>();
        // Full scan the schema to find given relation and get its attributes' type
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(Catalog.getInstance().getSchema()));
            String line = br.readLine();
            while (line != null) {
                line.trim();
                // get the name of relation in this line
                String relationName = line.substring(0,1);
                if(relationName.equals(relationalAtom.getName())){
                    String [] terms = line.split("\\s+");
                    for(int i=1;i<terms.length;i++){
                        attributesType.add(terms[i]);
                    }
                    break;
                }
                line = br.readLine();
            }
            this.schema = new Schema(relationalAtom.getTerms(),attributesType);
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // initialize the scanner
        String filePath = catalog.getRelationFilePath(relationalAtom.getName());
        try {
            scanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to get next tuple from scanner.
     * If scanner shows that the file still has a next line,
     * load it and new a tuple and return it.
     */
    @Override
    public Tuple getNextTuple() {
        Tuple t = null;
        if(scanner.hasNextLine()){
            t = new Tuple(schema,scanner.nextLine());
        }else{
            scanner.close();
        }
        return t;
    }

    /**
     * This method is used reset this operator,
     * especially, used to reset the scanner here.
     */
    @Override
    public void reset() {
        // recreate the scanner to reset it to the first line
        String filePath = catalog.getRelationFilePath(relationalAtom.getName());
        try {
            scanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used dump the tuple created,
     * calling the writeFile method in Utils to write
     * the tuple back to output file path.
     * @see ed.inf.adbs.minibase.Utils#writeFile(Tuple, String)
     */
    @Override
    public void dump() {
        Tuple t = null;
        while(scanner.hasNextLine()){
            t = new Tuple(schema,scanner.nextLine());
            Utils.writeFile(t, catalog.getOutputFile());
        }
        scanner.close();
    }

}
