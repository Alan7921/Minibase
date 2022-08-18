package ed.inf.adbs.minibase;

import ed.inf.adbs.minibase.base.Tuple;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * This is a util class that provides two methods, the first one is
 * used to join elements in collection to string with given delimiter.
 * The second method is used to write tuples back to file.
 */
public class Utils {

    public static String join(Collection<?> c, String delimiter) {
        return c.stream()
                .map(x -> x.toString())
                .collect(Collectors.joining(delimiter));
    }

    /**
     * A method that is used to write tuple back to file.
     * @param tuple A tuple that need to be written back.
     * @param outputFile A String typed object representing the path of outputfile
     */
    public static void writeFile(Tuple tuple, String outputFile){
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(outputFile,true);
            bw= new BufferedWriter(fw);
            String str = Utils.join(tuple.getAttributes(),", ");
            bw.write(str);
            bw.newLine();
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
