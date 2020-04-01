public class Support extends Node {

    public Support(String alias, String label) {
        super(alias, label);
    }

    public void accept(GraphDrawer visitor) {
        visitor.visitSupport(this);
    }

}
