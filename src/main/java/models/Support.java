package models;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import export.*;

public class Support extends Node {

    public Support(String alias, String label) {
        super(alias, label);
        setState(this);
        
    }

    public void accept(JDVisitor JDVisitor) {
        JDVisitor.visitSupport(this);
    }
    
    /**
     * used to return the correct State 
     * 
     * @param node whose state is sought
     * @return State corresponding to the node
     */
    public void setState(Node node) {
    	
    	
    	File realization = new File("output/realization/realization.txt");
    	if(realization.exists()) {
    		try {
				analyseRealisation(realization,node);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
    	}
    	
     }
    
    /**
     * used to analyse the file 'realization.txt' who correpond to the tasks acomplished
     * 
     * @param realization list of tasks acomplished
     * @param node corresponding to the task
     * @throws IOException
     */
    public void analyseRealisation(File realization,Node node) throws IOException {

    	RandomAccessFile ranRealization = new RandomAccessFile(realization,"r");
    	String lineRealization;    	
    	
    	while((lineRealization = ranRealization.readLine()) != null) {
    		if(node.label.contains('\"' + lineRealization + '\"')) {
    			this.state = State.DONE;
    			break;
			}
    	}
    	ranRealization.close();
    }

}
