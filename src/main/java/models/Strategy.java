package models;

import java.util.ArrayList;

import export.*;

public class Strategy extends Node {

    public Strategy(String alias, String label,ArrayList<String> realizationResult) {
        super(alias, label,realizationResult);
    }
    
    @Override
    public void accept(JDVisitor JDVisitor) {
        JDVisitor.visitStrategy(this);
    }

}
