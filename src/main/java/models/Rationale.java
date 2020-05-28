package models;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import export.*;

public class Rationale extends Node {

    public Rationale(String alias, String label) {
        super(alias, label);
    }

    public void accept(JDVisitor JDVisitor) {
        JDVisitor.visitRationale(this);
    }
    
    /**
     * Analyse the file 'realization.txt' and return the correct State for this node
     * 
     * @return State corresponding to the node
     */
    public void setState() {
    	
    	File realization = new File("output/realization/realization.txt");
    	if(realization.exists()) {
    		try {
				analyseRealisation(realization);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
    	}
    	
     }
    
    /**
     * used to analyse the file 'realization.txt' who correpond to the tasks acomplished
     * 
     * @param realization list of tasks acomplished
     * @throws IOException
     */
    public void analyseRealisation(File realization) throws IOException {

    	RandomAccessFile ranRealization = new RandomAccessFile(realization,"r");
    	String lineRealization;    	
    	
    	while((lineRealization = ranRealization.readLine()) != null) {
    		if(label.contains('\"' + lineRealization + '\"')) {
    			this.state = State.DONE;
    			break;
			}
    	}
    	ranRealization.close();
    }
}
