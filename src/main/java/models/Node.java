package models;

import export.*;

public class Node implements Visitable {
    public String alias;
    public String label;

    public Node(String alias, String label) {
        this.alias = alias;
        this.label = label;
    }

    public void accept(GraphDrawer visitor) {
        visitor.visitNode(this);
    }

}
