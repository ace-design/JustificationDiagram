package models;

import export.*;

public class Strategy extends Node {

    public Strategy(String alias, String label ) {
        super(alias, label);
    }
    
    @Override
    public void accept(JDVisitor jDVisitor) {
        jDVisitor.visitStrategy(this);
    }

}

