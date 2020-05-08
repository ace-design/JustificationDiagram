package models;

import export.*;

public class Support extends Node {

    public Support(String alias, String label) {
        super(alias, label);
    }

    public void accept(JDVisitor JDVisitor) {
        JDVisitor.visitSupport(this);
    }

}
