package ed.inf.adbs.minibase.base;

import java.util.List;

/**
 * This class abstract the concept of schema.
 * It contains a list of terms which represent the attributes names
 * and a list of string which refers to the type of these attributes.
 * For convenience, its toString, hashcode and equals method are
 * overridden. Besides,it also provides some getter and setter.
 */
public class Schema {
    private List<Term> attributesNames;
    private List<String> attributesTypes;

    public Schema(){

    }

    public Schema(List<Term> names, List<String> types){
        this.attributesNames = names;
        this.attributesTypes = types;
    }

    public List<Term> getAttributesNames() {
        return attributesNames;
    }

    public List<String> getAttributesTypes() {
        return attributesTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schema)) return false;

        Schema schema = (Schema) o;

        if (getAttributesNames() != null ? !getAttributesNames().equals(schema.getAttributesNames()) : schema.getAttributesNames() != null)
            return false;
        return getAttributesTypes() != null ? getAttributesTypes().equals(schema.getAttributesTypes()) : schema.getAttributesTypes() == null;
    }

    @Override
    public int hashCode() {
        int result = getAttributesNames() != null ? getAttributesNames().hashCode() : 0;
        result = 31 * result + (getAttributesTypes() != null ? getAttributesTypes().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Schema{" +
                "attributesNames=" + attributesNames +
                ", attributesTypes=" + attributesTypes +
                '}';
    }
}
