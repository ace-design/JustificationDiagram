package models;

import java.util.ArrayList;

import export.*;

public class Conclusion extends Node {
    public String restriction;

    public Conclusion(String alias, String label, String restriction,ArrayList<String> realizationResult) {
        super(alias, label,realizationResult);
        this.restriction = restriction;
    }
    
    @Override
    public void accept(JDVisitor JDVisitor) {
        JDVisitor.visitConclusion(this);
    } 

}
