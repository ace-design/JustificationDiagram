package models;

import export.*;

public class Domain extends Node {

    public Domain(String alias, String label) {
        super(alias, label);
    }

    public void accept(GraphDrawer visitor) {
        visitor.visitDomain(this);
    }
}
