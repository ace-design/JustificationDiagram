package models;

import export.*;

public class SubConclusion extends Node {
    public String restriction;

    public SubConclusion(String alias, String label) {
        super(alias, label);
    }

    @Override
    public void accept(JDVisitor JDVisitor) {
        JDVisitor.visitSubConclusion(this);
    }
}
 