package models;

import java.util.ArrayList;

import export.*;

public class SubConclusion extends Node {
    public String restriction;

    public SubConclusion(String alias, String label,ArrayList<String> realizationResult) {
        super(alias, label,realizationResult);
    }

    @Override
    public void accept(JDVisitor JDVisitor) {
        JDVisitor.visitSubConclusion(this);
    }
}
 