package models;

import export.*;

public class SubConclusion extends Node {

    public SubConclusion(String alias, String label) {
        super(alias, label);
    }

    public void accept(GraphDrawer visitor) {
        visitor.visitSubConclusion(this);
    }
}
