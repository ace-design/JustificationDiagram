package justificationDiagram;

import export.*;
import models.Node;
import models.Relation;
import models.Visitable;

import java.util.HashMap;
import java.util.HashSet;

public class JustificationDiagram implements Visitable {
    public HashMap<String, Node> nodes;
    public HashSet<Relation> relations;

    public JustificationDiagram() {
        nodes = new HashMap<>();
        relations = new HashSet<>();
    }

    @Override
    public void accept(GraphDrawer visitor) {
        visitor.visitDiagram(this);
    }

}
