import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;


import static org.junit.Assert.assertTrue;

 class OutputTests {

	String inputPath = "src/test/resources/";
	String inputPathJD = inputPath + "justificationDiagrams/";
	String outputPath = "justification/examples/output/images/";
	
	//todo : improve by deleting the generated files !
	@Test
	 void test_CI4CVS() throws IOException {
	    JDCompiler.main(new String[] { inputPath+"exampleCI/Pattern4CI.jd", "-o", outputPath+"Pattern4CI_CVS_Valid", "-rea", inputPath+"exampleCI/realizationPattern4CI.txt", "-act", inputPath+"exampleCI/actionValid4CVS.json", "-td","-svg", "-svgR"});
	    File fichier = new File(outputPath+"Pattern4CI_CVS_Valid.svg");
	    assertTrue(fichier.exists());
	    fichier = new File(outputPath+"Pattern4CI_CVS_Valid_REA.svg");
	    assertTrue(fichier.exists());
	    fichier = new File(outputPath+"Pattern4CI_CVS_Valid.todo");
	    assertTrue(fichier.exists());
	}
	    

    @Test
     void test_CI() throws IOException {
    	JDCompiler.main(new String[] { inputPath+"exampleCI/Pattern4CI.jd", "-o", outputPath+"Pattern4CI_Valid", "-rea", inputPath+"exampleCI/realizationPattern4CI.txt", "-act", inputPath+"exampleCI/actionValid.json", "-td","-svg", "-svgR"});
    }
    
    
    @Test
     void figToDOWithRea() throws IOException {
        JDCompiler.main(new String[] { inputPathJD+"fig3.jd", "-o", outputPath+"fig3","-rea", inputPath+"realization/realizationFig3.txt", "-act", inputPath+"action/actionFig3.json", "-td", "-svg", "-svgR"});
    }
    
    @Test
     void figToDOWithReaAndFileVerification() throws IOException {
    	JDCompiler.main(new String[] { inputPath+"exampleCI/Pattern4CI.jd", "-o", outputPath+"Pattern4CI_Invalid", "-rea", inputPath+"exampleCI/realizationPattern4CIInvalid.txt", "-act", inputPath+"exampleCI/actionInvalid.json", "-td", "-svgR"});
    }
    
    @Test
     void basicEmpty() throws IOException {
        JDCompiler.main(new String[] {inputPathJD+"basic.jd", "-o", outputPath+"basicEmpty","-td", "-svgR", "-svg", "-rea",inputPath+"realization/empty.txt"});
        File fichier = new File(outputPath+"basicEmpty.svg");
	    assertTrue(fichier.exists());
	    fichier = new File(outputPath+"basicEmpty.todo");
	    assertTrue(fichier.exists());
	    fichier = new File(outputPath+"basicEmpty_REA.svg");
	    assertTrue(fichier.exists());	
    }
    
    
    
    @Test
     void basic() throws IOException {
        JDCompiler.main(new String[] {inputPathJD+"basic.jd", "-o", outputPath+"basic", "-svg"});
    }

    @Test
     void fig1() throws IOException {
        JDCompiler.main(new String[] {inputPathJD+"fig1.jd", "-o", outputPath+"fig1", "-svg"});
    }
 
    @Test
     void fig2() throws IOException {
        JDCompiler.main(new String[] {inputPathJD+"fig2.jd", "-o", outputPath+"fig2", "-gv"});
    }
 
    @Test
     void fig3() throws IOException {
        JDCompiler.main(new String[] { inputPathJD+"fig3.jd", "-o", outputPath+"fig3", "-svg"});
    }
    

    @Test
	 void test_CI4ThisProject() throws IOException {
    	String pattern = "justification/Pattern4CI.jd";
    	String output="justification/output/local/CI";
    	String realization = "justification/local/realization.txt";
    	String actions = "justification/actions.json";
	    JDCompiler.main(new String[] { pattern, "-o", output, "-rea",realization, "-act", actions, "-td","-svg", "-svgR"});
	    File fichier = new File(output+".svg");
	    assertTrue(fichier.exists());
	    fichier = new File(output + "_REA.svg");
	    assertTrue(fichier.exists());
	    fichier = new File(output + ".todo");
	    assertTrue(fichier.exists());
	}

    
}
