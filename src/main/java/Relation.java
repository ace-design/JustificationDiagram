public class Relation implements Visitable {
    String from;
    String to;

    public Relation(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public void accept(JDVisitor JDVisitor) {
        JDVisitor.visitRelation(this);
    }

}
