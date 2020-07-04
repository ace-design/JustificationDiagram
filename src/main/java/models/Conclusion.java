package models;

import export.*;

public class Conclusion extends Node {
    public final String restriction;

    public Conclusion(String alias, String label, String restriction) {
        super(alias, label);
        this.restriction = restriction;
    }
    
    @Override
    public void accept(JDVisitor jDVisitor) {
        jDVisitor.visitConclusion(this);
    } 

}
