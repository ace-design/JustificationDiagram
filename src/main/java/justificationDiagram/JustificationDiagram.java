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
    
    public JustificationDiagram(HashMap<String,Node> nodes, Set<Relation> relations) {
        this.nodes = nodes;
        this.relations = relations;
    }

    @Override
    public void accept(JDVisitor visitor) {
        visitor.visitDiagram(this);
    }
    
    /**
     * Change the state of the node in fonction of there childrens
     */
    public void analyseDiagrammeRelation() {
    	
    	TopologicalSort sort = new TopologicalSort(this);
        
        HashMap <String,Node> newOrder = new HashMap<String,Node> ();
        
        for (Node node : sort.getOrder()) {
            node.analyseRelation(node);
            newOrder.put(node.alias, node);

        }
        
        for (Node node : sort.getOrder()) {
        	this.nodes.get(node.alias).state = node.state;
        	System.out.println(node.state);
		}

    }

}
