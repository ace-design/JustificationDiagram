package models;

import export.*;

public class Strategy extends Node {

    public Strategy(String alias, String label) {
        super(alias, label);
    }

    public void accept(GraphDrawer visitor) {
        visitor.visitStrategy(this);
    }

}
