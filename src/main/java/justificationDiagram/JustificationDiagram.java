package justificationDiagram;

import export.*;
import models.InformationNode;
import models.Node;
import models.Relation;
import models.Visitable;

import java.util.*;
import java.util.Map.Entry;

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
    
    
    // TODO: faire la javadoc ! 
    /**
	 *
     */
    public void analysesInformationNode(HashMap<String,InformationNode> informationNodes) {
   
    	if(informationNodes != null) {
    		    		
    		for (Entry<String, Node> node : nodes.entrySet()) {
    			
    			String realLabel = node.getValue().label;
    			realLabel = realLabel.substring(1,realLabel.length()-1);

    			if(informationNodes.containsKey(realLabel)) {
    	    	 	node.getValue().setInformationNode(informationNodes.get(realLabel));
    	    	 	System.out.println(informationNodes.get(realLabel).optional);
    			}	
            }
    	}
    	else {
    		//TODO : ajouter une gestion d'erreur ici !
    	}
     	


    }
     
    /**
     * Change the state of the node in fonction of there childrens.
     * Uses a TopologicalSort but does not change the order of the nodes in the diagram (to avoid problems).
     * call Node.analyseRelation();
     */
    public void analysesDiagrammeRelation(ArrayList<String> labelList) {
    	
    	TopologicalSort sort = new TopologicalSort(this);
                
        for (Node node : sort.getOrder()) {
            node.prerequisiteAnalysis(labelList);
        } 


    }

}
