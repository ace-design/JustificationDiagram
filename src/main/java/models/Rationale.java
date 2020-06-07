package models;

import java.util.ArrayList;

import export.*;

public class Rationale extends Node {

    public Rationale(String alias, String label,ArrayList<String> realizationResult) {
        super(alias, label,realizationResult);
    }

    public void accept(JDVisitor JDVisitor) {
        JDVisitor.visitRationale(this);
    }
    
}
