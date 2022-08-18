package ed.inf.adbs.minibase.base;

import ed.inf.adbs.minibase.Utils;

import java.util.List;
/**
 * This class defines the concept of Query, it contains a relational atom
 * as head and a list of atoms as its body.
 * For convenience, its toString, method is overridden.
 * Besides,it also provides some getters.
 */
public class Query {
    private RelationalAtom head;

    private List<Atom> body;

    public Query(RelationalAtom head, List<Atom> body) {
        this.head = head;
        this.body = body;
    }

    public RelationalAtom getHead() {
        return head;
    }

    public List<Atom> getBody() {
        return body;
    }

    @Override
    public String toString() {
        return head + " :- " + Utils.join(body, ", ");
    }
}
