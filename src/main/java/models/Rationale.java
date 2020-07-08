package models;

import export.*;

public class Rationale extends Node {

    public Rationale(String alias, String label) {
        super(alias, label);
    }

    @Override
    public void accept(JDVisitor jDVisitor) {
        jDVisitor.visitRationale(this);
    }
    
}
