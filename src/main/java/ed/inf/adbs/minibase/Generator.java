package ed.inf.adbs.minibase;

import ed.inf.adbs.minibase.base.*;
import ed.inf.adbs.minibase.base.operator.*;

import java.util.ArrayList;
import java.util.List;

// strategy: first step translate implicit conditions both for single relation and for join relations
// write two methods used to find referred conditions

/**
 * This class takes the responsibility of generating query plan for given query,
 * it follows this procedure: for any given query, firstly check that whether
 * there exist implicit selection condition such as R(x, y, 4). If found, it
 * would creat a new variable and add an equal condition from the new created
 * variable to the constant. After that it would loop the whole relations
 * for each relation, it would loop from the next relations and check whether
 * these two relation have an implicit equal-join condition. If found, add it
 * to the condition list of this query.
 */

public class Generator {
    private RelationalAtom head;
    private List<Atom> body;
    private List<RelationalAtom> relations;
    private ArrayList<ComparisonAtom> conditions;
    private List<Term> free_variables;
    private List<Term> relation_variables;
    private Operator root = null;

    public Generator(Query query){

        // initialization of fields
        head = query.getHead();
        body = query.getBody();
        relations = new ArrayList<>();
        conditions = new ArrayList<>();
        for(Atom atom:body){
            if(atom instanceof  RelationalAtom){
                relations.add((RelationalAtom) atom);
            }
            if(atom instanceof ComparisonAtom){
                conditions.add((ComparisonAtom) atom);
            }
        }
        free_variables = head.getTerms();
        relation_variables = new ArrayList<>();
        for (RelationalAtom ra: relations) {
            List<Term> terms = ra.getTerms();
            for (Term term:terms) {
                if(!relation_variables.contains(term)){
                    relation_variables.add(term);
                }
            }
        }

        // check whether there exist implicit selection
        checkImplicitSelectionConds();

        // check whether there exist implicit equai-join conditions
        // if there exist multiple relations
        if(relations.size() > 1){
            for(int i = 0; i< relations.size()-1;i++){
                for(int j=i+1; j< relations.size();j++){
                    checkEquiJoin(relations.get(i),relations.get(j));
                }
            }
        }
    }

    /**
     * This method is used to check implicit selection conditions.
     * It would fully scan all the relation atoms in the body of a query,
     * check if they hava a constant in the list of term, if so, create a
     * new variable for it and replace this relational atom.
     */
    private void checkImplicitSelectionConds(){
        for(RelationalAtom ra: relations){
            if(hasConstant(ra)){
                List<Term> old_terms = ra.getTerms();
                List<Term> new_terms = new ArrayList<>();
                for (Term term: old_terms) {
                    if(term instanceof Variable){
                        new_terms.add(term);
                    }else{
                        String new_variable_name = generateNewVariableName();
                        // make sure that the generated name do no crash with
                        // the existing variable name
                        while(relation_variables.contains(new_variable_name)){
                            new_variable_name = generateNewVariableName();
                        }
                        Variable replacement = new Variable(new_variable_name);
                        relation_variables.set(relation_variables.indexOf(term),replacement);
                        new_terms.add(replacement);
                        ComparisonAtom additional_cond = new ComparisonAtom(replacement,term,ComparisonOperator.EQ);
                        conditions.add(additional_cond);
                    }
                }
                RelationalAtom new_ra = new RelationalAtom(ra.getName(),new_terms);
                relations.set(relations.indexOf(ra),new_ra);
            }
        }
    }

    /**
     * This method is used to check whether there exists implicit Equi-condition between two given relations.
     * If so create a new comparison atom and add it to the conditions list.
     * @param ra1 The first given relational atom.
     * @param ra2 The first given relational atom.
     */
    private void checkEquiJoin(RelationalAtom ra1, RelationalAtom ra2) {
        List<Term> terms1 = ra1.getTerms();
        List<Term> terms2 = ra2.getTerms();

        for(int i=0; i<terms1.size(); i++){
            for(int j=0; j< terms2.size();j++){
                if(terms1.get(i).equals(terms2.get(j))){
                    Variable variable = (Variable) (terms2.get(j));
                    // if we find same variables in this two atoms, rename one of them with double its name
                    Variable new_variable = new Variable(variable.getName()+variable.getName());
                    terms2.set(j,new_variable);

                    for(int k =0; k <conditions.size(); k++){
                        ComparisonAtom ca = conditions.get(k);
                        Term left_term = ca.getTerm1();
                        Term right_term = ca.getTerm2();
                        if(left_term.equals(variable)){
                            left_term = new_variable;
                        }
                        if (right_term.equals(variable)){
                            right_term = new_variable;
                        }
                        ComparisonAtom new_ca = new ComparisonAtom(left_term,right_term,ca.getOp());
                        if (!conditions.contains(new_ca)){
                            conditions.add(new_ca);
                        }
                    }
                    conditions.add(new ComparisonAtom(variable,new_variable,ComparisonOperator.EQ));
                    relation_variables.add(new_variable);
                }
            }
        }
    }

    /**
     * This method is used to check whether given relational atom has constant or not
     * @return true for have, false for not.
     */
    private boolean hasConstant(RelationalAtom ra) {
        List<Term> terms = ra.getTerms();
        for (Term term:terms) {
            if(term instanceof Constant){
                return  true;
            }
        }
        return false;
    }

    /**
     * This method is used to generate a query plan.
     * @return Return the root operator of this plan.
     */
    public Operator generateQueryPlan(){
        // firstly, get the first relation and create a scan operator for it
        List<Term> joined_terms = new ArrayList<>();
        RelationalAtom ra = relations.get(0);
        joined_terms.addAll(ra.getTerms());
        root = new ScanOperator(ra);
        // then find that whether it is involved in selection operator
        // if so create a selection operator
        List<ComparisonAtom> involvedSelectionConds = findSelectionConds(ra);
        if(involvedSelectionConds != null){
        root = new SelectOperator(root,involvedSelectionConds, ra);
        }
        List<ComparisonAtom> joinConds = null;
        Operator op2 = null;

        // for the rest relations, repeat the procedure of create scan operator
        // check whether involved in selection
        for(int i=1; i< relations.size();i++){
            RelationalAtom ra2 = relations.get(i);
            op2 = new ScanOperator(ra2);
            involvedSelectionConds = findSelectionConds(ra2);
            if(involvedSelectionConds != null){
                op2 = new SelectOperator(op2,involvedSelectionConds, ra2);
            }
            // use the currently joined terms and the terms of the focused relation
            // to check whether they are involved in a join
            joinConds = findJoinConds(joined_terms,ra2.getTerms());
            root = new JoinOperator(root,op2,joinConds);
            joined_terms.addAll(ra2.getTerms());
        }

        // check whether the head contains an aggregation operation
        // if so create the corresponding operator
        if(head.getAggregateVariable() != null){
            if(head.getAggregateVariable().getType().equals("AVG")){
                root = new AvgOperator(root,head.getAggregateVariable(),head.getTerms());
            }else{
                root = new SumOperator(root,head.getAggregateVariable(),head.getTerms());
            }
            return root;
        }

        // if no aggregation operation and the free variables are not equal to
        // the relation variables of the body, do the projection
        if(!free_variables.equals(relation_variables)){
            root = new ProjectOperator(free_variables,root);
        }

        return root;
    }

    /**
     * This method is used to randomly generate a new variable name
     * @return Return a string representing the generated name.
     */
    public String generateNewVariableName(){
        String alphabetsLowerCase = "abcdefghijklmnopqrstuvwxyz";
        StringBuffer stringBuffer = new StringBuffer();
        // generate a random number between 0 and length of characters set
        int randomIndex = (int)(Math.random() * alphabetsLowerCase.length());
        stringBuffer.append(alphabetsLowerCase.charAt(randomIndex));
        randomIndex = (int)(Math.random() * alphabetsLowerCase.length());
        stringBuffer.append(alphabetsLowerCase.charAt(randomIndex));
        return new String(stringBuffer);
    }

    /**
     * This method is used to find all the involved conditions for any given
     * relational atom. It calls the containedIn method of ComparisonAtom.
     * @see ed.inf.adbs.minibase.base.ComparisonAtom#containedIn(List)
     * @param ra the relation atom that needs to check.
     * @return A list of conditions that this relational atom involves in.
     */
    public List<ComparisonAtom> findSelectionConds(RelationalAtom ra){
        List<ComparisonAtom> invlovedConds = new ArrayList<>();
        List<Term> raTerms = ra.getTerms();
        // If all the terms in the comparison atom are contained
        // in the relational atom's terms, it means that
        // this relational atom is involved in this condition
        for ( ComparisonAtom ca:conditions) {
            if(ca.containedIn(raTerms)){
                invlovedConds.add(ca);
            }
        }
        if(invlovedConds.size()>0){
            return invlovedConds;
        }
        return null;
    }

    /**
     * This method is used to find all the involved join conditions for any given two lists of terms.
     * The strategy here is quite simple, any conditions that are involved in this two lists of
     * terms must be the subset of their union. For instance, if we try to join R(x,y,z),S(xx,u,w),x=xx,x>4,z='adbs'
     * Only x = xx, are contained in {x,y,z,xx,u,w}, thus it is the join-conds to be returned.
     * @param terms1 A list that records currently joined terms.
     * @param terms2 Terms of the relational atom to be joined.
     */
    public List<ComparisonAtom> findJoinConds(List<Term> terms1, List<Term> terms2){
        List<ComparisonAtom> invlovedConds = new ArrayList<>();
        List<Term> unitonTerms = new ArrayList<>();
        unitonTerms.addAll(terms1);
        unitonTerms.addAll(terms2);

        for ( ComparisonAtom ca:conditions) {
            if(ca.containedIn(unitonTerms)){
                invlovedConds.add(ca);
            }
        }
        if(invlovedConds.size()>0){
            return invlovedConds;
        }
        return null;
    }

}


