public class Relation implements Visitable {
    String from;
    String to;

    public Relation(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public void accept(GraphDrawer visitor) {
        visitor.visitRelation(this);
    }

}
