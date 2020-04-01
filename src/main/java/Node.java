public class Node implements Visitable {
    String alias;
    String label;

    public Node(String alias, String label) {
        this.alias = alias;
        this.label = label;
    }

    public void accept(GraphDrawer visitor) {
        visitor.visitNode(this);
    }

}
