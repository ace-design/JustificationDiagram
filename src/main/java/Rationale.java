public class Rationale extends Node {

    public Rationale(String alias, String label) {
        super(alias, label);
    }

    public void accept(GraphDrawer visitor) {
        visitor.visitRationale(this);
    }
}
