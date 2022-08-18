package ed.inf.adbs.minibase.base;

import java.util.ArrayList;
import java.util.List;

/**
 * This class encapsulate the data of tuple.
 * It has two fields, a list of object which represents the record
 * and a Schema typed schema.
 * For convenience, its toString, hashcode and equals method are
 * overridden. Besides,it also provides some getters and setters.
 */
public class Tuple {
    private ArrayList<Object> attributes = new ArrayList<>();
    private Schema schema;

    public Tuple(Schema schema, String contents) {
        this.schema = schema;
        contents.trim();
        String[] data = contents.split(", ");
        List<String> attributesTypes = schema.getAttributesTypes();
        for(int i = 0; i < attributesTypes.size(); i++){
            if(attributesTypes.get(i).equals("int")){
                attributes.add(Integer.parseInt(data[i]));
            }else{
                attributes.add(data[i]);
            }
        }
    }

    public Tuple(Schema schema, ArrayList<Object> attributes){
        this.schema = schema;
        this.attributes = attributes;
    }

    /**
     * A method that used to create a new tuple based on the merge of given two tuples
     * @param t1 First tuple to merge.
     * @param t2 Second tuple to merge.
     * @return Merged tuple.
     */
    public Tuple(Tuple t1, Tuple t2){
        // all the fields needed to creat a new tuple
        ArrayList<Object> merged_Attributes = new ArrayList<>();
        List<Term> merged_AttributesNames = new ArrayList<>();
        List<String> merged_AttributesTypes = new ArrayList<>();

        ArrayList<Object> t1Attributes = t1.getAttributes();
        Schema t1Schema = t1.getSchema();
        List<Term> t1AttributesNames = t1Schema.getAttributesNames();
        List<String> t1AttributesTypes = t1Schema.getAttributesTypes();

        ArrayList<Object> t2Attributes = t2.getAttributes();
        Schema t2Schema = t2.getSchema();
        List<Term> t2AttributesNames = t2Schema.getAttributesNames();
        List<String> t2AttributesTypes = t2Schema.getAttributesTypes();

        // extracting from the provided two tuples
        for (int i =0; i < t1AttributesNames.size(); i++) {
            Term term = t1AttributesNames.get(i);
            if(!merged_AttributesNames.contains(term)){
                merged_AttributesNames.add(term);
                merged_Attributes.add(t1Attributes.get(i));
                merged_AttributesTypes.add(t1AttributesTypes.get(i));
            }
        }
        for (int i =0; i < t2AttributesNames.size(); i++) {
            Term term = t2AttributesNames.get(i);
            if(!merged_AttributesNames.contains(term)){
                merged_AttributesNames.add(term);
                merged_Attributes.add(t2Attributes.get(i));
                merged_AttributesTypes.add(t2AttributesTypes.get(i));
            }
        }

        Schema new_schema = new Schema(merged_AttributesNames,merged_AttributesTypes);
        this.attributes = merged_Attributes;
        this.schema = new_schema;
    }

    /**
     * A method that used to get the value of specific attribute with given term.
     * @param term Term that used to get value.
     * @return The corresponding value of this attribute.
     */
    public Object getAttrWithTerm(Term term){
        return attributes.get(schema.getAttributesNames().indexOf(term));
    }

    public ArrayList<Object> getAttributes() {
        return attributes;
    }

    public Schema getSchema() {
        return schema;
    }

    @Override
    public String toString() {
        return "Tuple{" +
                "attributes=" + attributes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple)) return false;

        Tuple tuple = (Tuple) o;

        if (getAttributes() != null ? !getAttributes().equals(tuple.getAttributes()) : tuple.getAttributes() != null)
            return false;
        return getSchema() != null ? getSchema().equals(tuple.getSchema()) : tuple.getSchema() == null;
    }

    @Override
    public int hashCode() {
        int result = getAttributes() != null ? getAttributes().hashCode() : 0;
        result = 31 * result + (getSchema() != null ? getSchema().hashCode() : 0);
        return result;
    }
}
