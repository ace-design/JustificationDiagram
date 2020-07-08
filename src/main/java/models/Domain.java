package models;

import export.*;

public class Domain extends Node {
	

    public Domain(String alias, String label) {
        super(alias, label);
    }

    @Override
    public void accept(JDVisitor jDVisitor) {
        jDVisitor.visitDomain(this);
    }
    
}
