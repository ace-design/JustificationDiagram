package justificationDiagram;

import export.*;
import models.Node;
import models.Relation;
import models.Visitable;

import java.util.*;

public class JustificationDiagram implements Visitable {
    public HashMap<String, Node> nodes;
    public Set<Relation> relations;

    public JustificationDiagram() {
        nodes = new HashMap<>();
        relations = new HashSet<>();
    }

    @Override
    public void accept(JDVisitor visitor) {
        visitor.visitDiagram(this);
    }
    
    public void analyseDiagrammeRelation() {
    	for (Map.Entry<String, Node> entryNode : nodes.entrySet()) {
    		entryNode.getValue().analyseRelation(entryNode.getValue());

         }
    }

}
