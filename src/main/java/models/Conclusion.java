package models;

import export.*;

public class Conclusion extends Node {
    public String restriction;

    public Conclusion(String alias, String label, String restriction) {
        super(alias, label);
        this.restriction = restriction;
    }

    public void accept(GraphDrawer visitor) {
        visitor.visitConclusion(this);
    }

}
