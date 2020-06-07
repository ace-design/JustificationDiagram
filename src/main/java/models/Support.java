package models;

import java.util.ArrayList;

import export.*;

public class Support extends Node {

    public Support(String alias, String label,ArrayList<String> realizationResult) {
        super(alias, label,realizationResult);
    }

    @Override
    public void accept(JDVisitor JDVisitor) {
        JDVisitor.visitSupport(this);
    }
    
    

}
