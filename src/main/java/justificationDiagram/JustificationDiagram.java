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
    
    /**
     * Used to set the informationNode to the corresponding node.
     * 
     * @param informationNodes Map of information found in the informations file with the label of the node and the informationNode
     */
    public void setInformationNode(HashMap<String,InformationNode> informationNodes) {
   
    	if(informationNodes != null) {
    		    		
    		for (Entry<String, Node> node : nodes.entrySet()) {
    			
    			String realLabel = node.getValue().getLabel();
    			realLabel = realLabel.substring(1,realLabel.length()-1);

    			if(informationNodes.containsKey(realLabel)) {
    	    	 	node.getValue().setInformationNode(informationNodes.get(realLabel));
    			}	
            }
    	}
    	else {
    		System.err.println("InformationNodes is null, there is no information in this file.");
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
