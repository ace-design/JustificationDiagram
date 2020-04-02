package justificationDiagram;

import export.*;
import models.Node;
import models.Relation;
import models.Visitable;
import java.util.ArrayList;

public class JustificationDiagram implements Visitable {
    public ArrayList<Node> nodes;
    public ArrayList<Relation> relations;

    public JustificationDiagram() {
        nodes = new ArrayList<>();
        relations = new ArrayList<>();
    }

    @Override
    public void accept(GraphDrawer visitor) {
        visitor.visitDiagram(this);
    }

}
