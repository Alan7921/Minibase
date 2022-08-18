package ed.inf.adbs.minibase;

import java.io.File;

/**
 *
 * Catalog of Minibase, records the database directory, the input file path
 * and the output file path, also provides some getters, designed following
 * the singleton pattern.
 */
public class Catalog {
    private static Catalog instance = null;
    private String dbDir;
    private String inputFile;
    private String outputFile;

    private Catalog(){

    }

    public static Catalog getInstance(){
        if (instance == null){
            instance = new Catalog();
        }
        return instance;
    }

    public void init(String dbDir, String inputFile, String outputFile){
        this.dbDir = dbDir;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public String getInputFile() {
        return inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public String getRelationFilePath(String filename){
        if(instance == null){
            throw new RuntimeException("Catlog is not initialized!");
        }
        return instance.dbDir + File.separator+"files"+File.separator+filename+".csv";
    }

    public String getSchema(){
        if(instance == null){
            throw new RuntimeException("Catlog is not initialized!");
        }
        String schemaPath = instance.dbDir+ File.separator +"schema.txt";
        return schemaPath;
    }

}
