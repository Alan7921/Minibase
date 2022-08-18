package ed.inf.adbs.minibase;

import ed.inf.adbs.minibase.base.*;
import ed.inf.adbs.minibase.parser.QueryParser;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

/**
 * This class takes the responsibility of minimizing conjunctive queries.
 * The main strategy of checking query homomorphism starts with decomposing
 * it to checking homomorphism between atoms. For each atom in query1, try
 * to find a atom homo for it, then we move to next atom in query1, again
 * find a atom homo for it and then see whether they can be merged. If they
 * can be merged, repeat this procedure till we finish the last atom in
 * query1 or we cannot find a atom homo for a atom in query1. In the former
 * case, we check whether this mapping on head1 is equal to head2. In the
 * latter case, we just go backtrack. The implementation of this searching
 * procedure rely on deep first searching method.
 */
public class CQMinimizer {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("Usage: CQMinimizer input_file output_file");
            return;
        }
        String inputFile = args[0];
        String outputFile = args[1];
        minimizeCQ(inputFile, outputFile);
    }

    /**
     * CQ minimization procedure
     * Assume the body of the query from inputFile has no comparison atoms
     * but could potentially have constants in its relational atoms.
     * @param inputFile A String typed object representing the path of inputfile
     * @param outputFile A String typed object representing the path of outputfile
     */
    public static void minimizeCQ(String inputFile, String outputFile) {
        Query query = null;
        try {
            query = QueryParser.parse(Paths.get(inputFile));
            List<Atom> body = query.getBody();
            while (true) {
                boolean changed = false;
                // Try to remove each atom and check whether homomorphism
                // exist between the original {atoms} and {atoms-1}
                // If so, remove that atom and repeat this procedure
                for (int i = 0; i < body.size(); i++) {
                    // Use a temp Query object to record the changed body
                    // In order to realize the deep copy, use
                    // string and parse to generate a new object
                    Query temp = QueryParser.parse(query.toString());
                    RelationalAtom tempHead = temp.getHead();
                    List<Atom> tempBody = temp.getBody();
                    tempBody.remove(i);

                    // check containment, make sure that the free variables
                    // are still kept, if not just drop this try and go to
                    // next loop
                    if (!checkFreeVariableContained(tempHead, tempBody)) {
                        continue;
                    } else {
                        // then check homomorphism between query and temp query
                        if (hasQueryHomo(query, temp)) {
                            // if one homomorphism is found, remove this atom
                            // from the original query and mark the chang flag
                            body.remove(i);
                            changed = true;
                            break;
                        }
                    }
                }
                if (!changed) {
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Exception occurred during parsing");
            e.printStackTrace();
        }
        // Write the minimized query back to file
        FileWriter fw = null;
        try {
            fw = new FileWriter(outputFile);
            if(query != null){
                fw.write(query.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fw != null){
                try {
                    fw.close();
                } catch (IOException e) {
                    System.err.println("Exception occurred during writing query back to file");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Check whether there exist homomorphism between these two Queries.
     * This function is kept and separated as an interface in order to
     * allow different implementation of homomorphism searching methods
     * @param q1 Firsty query to be checked
     * @param q2 Second query to be checked
     * @return  a boolean value, true if homomorphism between the two provided queries does exist, else return false.
     */
    public static boolean hasQueryHomo(Query q1,Query q2){
        HashMap<Term,Term> currentMap = new HashMap<>();
        return dfs(q1,q2,0,currentMap);
    }

    /**
     * Check whether given body homomorphism applied on head1 is equal to head2,
     * i.e. whether this body homomorphism is a query homomorphism.
     *  @return a boolean value, true for equal, false for not.
     */
    public static boolean checkHeadEqual(RelationalAtom head1, RelationalAtom head2, HashMap<Term,Term> mappings){
        List<Term> head1Terms = head1.getTerms();

        // Applying mappings on head1
        List<Term> mappedTerms1 = new ArrayList<>();
        for (Term term:head1Terms) {
            mappedTerms1.add(mappings.get(term));
        }
        RelationalAtom mappedHead1 = new RelationalAtom(head1.getName(),mappedTerms1);
        return mappedHead1.equals(head2);
    }


    /**
     *  A depth-first searching method that is used to explore the possible mapping.
     *  The searching procedure looks like we start with the left top corner of a chess board.
     *  We start with trying to find the homo between first atom in body1 and any atom in body2,
     *  if it does exist, start exploring any homo between atom2 in body1 and any atoms in body2.
     *  If we found it, check whether the two mappings can merge, if so go further and continue.
     *  Whenever we finish the last atoms in body1 we get a whole mappings for these two queries.
     *  Then we need to check the head to figure out whether it is a query homo.
     *  If for any atoms in body1 we touch the last atom in body2 we need to backtrack to last top level.
     * @param q1 First query to be checked.
     * @param q2 Second query to be checked.
     * @param currentMap A hashmap used to record current mapping.
     * @return Return true referring to the existence of valid query homo, if it does exist, else return false.
     */
    public static boolean dfs(Query q1, Query q2,int q1index,HashMap<Term,Term> currentMap){
        List<Atom> body1 = q1.getBody();
        List<Atom> body2 = q2.getBody();
        // if we find a mapping that satisfy all the atoms in body1,
        // good, return true which means this homo does exist
        if(q1index == body1.size()){
            return checkHeadEqual(q1.getHead(),q2.getHead(),currentMap);
        }
        RelationalAtom ra = (RelationalAtom) body1.get(q1index);
        for(int i=0; i<body2.size();i++){
            // if we find a homo between current atom in body1 and any atom in body2
            if(homoBetween2Atom(ra,(RelationalAtom) body2.get(i))!=null){
                HashMap<Term,Term> newMap = homoBetween2Atom(ra,(RelationalAtom) body2.get(i));
                // check whether it can be merged to previous mappings
                if(newMap != null){
                    if(canMerge(currentMap,newMap)){
                        // if so, based on this new mapping continue the search
                        HashMap<Term,Term> mergedMap = new HashMap<>();
                        mergedMap.putAll(currentMap);
                        mergedMap.putAll(newMap);
                        if(dfs(q1, q2,q1index+1,mergedMap)){
                            return true;
                        }
                    }
                }
            }
        }
        // if for any step of search, we have touched the last atom of body2 and still cannot
        // find a mapping, or find it but cannot merge it, return false
        return false;
    }

    /**
     *  A method that is used to check whether there exist homomorphism between two atoms.
     * @param ra1 First relation atom to be checked.
     * @param ra2 Second relation atom to be checked.
     * @return If found, return a HashMap<Term,Term> that records the mapping, else return NULL.
     */
    static HashMap<Term,Term> homoBetween2Atom (RelationalAtom ra1, RelationalAtom ra2){

        // If two atoms are different relations, directly return NULL
        if(!(ra1.getName().equals(ra2.getName()))){
            return null;
        }

        // Again, if two atoms have different size, directly return NULL
        if(ra1.getTerms().size() != ra2.getTerms().size()){
            return null;
        }

        // If both the name and the size is identical, then start mapping
        HashMap<Term,Term> map = new HashMap<>();
        for (int i=0; i<ra1.getTerms().size();i++){
            map.put(ra1.getTerms().get(i),ra2.getTerms().get(i));
        }

        // If constants are not mapped to themselves, drop this mapping and return NULL
        for (Term key: map.keySet()) {
            if(key instanceof Constant && !(key.equals(map.get(key)))){
                return null;
            }
        }
        return map;
    }

    /**
     *  A method that is used to check whether two mappings are able to merge.
     * @param map1 The first map to be merged.
     * @param map2 The second map to be merged.
     * @return Return true if they are able to, else return false.
     */
    static boolean canMerge(HashMap<Term,Term> map1, HashMap<Term,Term> map2){
        Set<Term> key1 = map1.keySet();
        Set<Term> key2 = map2.keySet();

        HashSet<Term> keys = new HashSet<>(key1);
        // try to get the intersection terms of these two sets
        keys.retainAll(key2);
        HashSet<Term> intersections = new HashSet<>(keys);
        boolean canMerge = true;
        // if there are identical keys in the two mappings
        // check whether they have a different value
        // which means whether a variable is mapped to two
        // values in these two mappings
        if(intersections.size()>0) {
            Iterator<Term> iterator = intersections.iterator();
            while (iterator.hasNext()) {
                Term key = (Term) iterator.next();
                if (!map1.get(key).equals(map2.get(key))) {
                    canMerge = false;
                    break;
                }
            }
        }
        return canMerge;
    }

    /**
     * Check the containment of free variables in body atoms.
     * A fast check that avoid further exploring of homomorphism.
     * @param body  A list of atom to be checked.
     * @param head The head relational atom to be checked.
     * @return a boolean value, if do contained, else return false.
     */
    public static boolean checkFreeVariableContained(RelationalAtom head, List<Atom> body){
        HashSet<Variable> freeVariables = new HashSet<>();
        Set<Variable> variables = new HashSet<>();
        // If it is a boolean query, directly return ture
        if (head.getTerms() == null){
            return true;
        }
        // get the set of free variables
        for (Term term: head.getTerms()
             ) {
            freeVariables.add((Variable) term);
        }
        // get the set of the body's variables
        for (Atom a:body) {
            if(a instanceof RelationalAtom){
                RelationalAtom ra = (RelationalAtom) a;
                for (Term term : ra.getTerms()) {
                    if(term instanceof Variable){
                        variables.add((Variable) term);
                    }
                }
            }
        }
        return variables.containsAll(freeVariables);
    }
}
