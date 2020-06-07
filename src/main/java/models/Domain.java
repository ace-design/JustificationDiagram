package models;

import java.util.ArrayList;

import export.*;

public class Domain extends Node {
	

    public Domain(String alias, String label,ArrayList<String> realizationResult) {
        super(alias, label,realizationResult);
    }

    public void accept(JDVisitor JDVisitor) {
        JDVisitor.visitDomain(this);
    }
    
}
