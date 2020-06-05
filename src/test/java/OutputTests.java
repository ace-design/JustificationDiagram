import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutputTests {

	String inputPath = "examples/";
	String outputPath = "output/images/";
    @Test
    public void dummy() {
        assertEquals(0, 0);
        assertEquals(1, 1);
    }

    @Test
    public void test_CI() throws IOException {
    	JDCompiler.main(new String[] { inputPath+"exampleCI/Pattern4CI.jd", "-o", outputPath+"Pattern4CI_Valid", "-td","-svg", "-svgR", inputPath+"exampleCI/realizationPattern4CI.txt", "-rea"});
    }
    
    
    @Test
    public void figToDOWithRea() throws IOException {
        JDCompiler.main(new String[] { inputPath+"fig3.jd", "-o", outputPath+"fig3", "-td", "-svg", "-svgR", inputPath+"realizationFig3.txt", "-rea"});
    }
    
    @Test
    public void figToDOWithReaAndFileVerification() throws IOException {
    	JDCompiler.main(new String[] { inputPath+"exampleCI/Pattern4CI.jd", "-o", outputPath+"Pattern4CI_NotValid", "-td", "-svgR", inputPath+"exampleCI/realizationPattern4CINotValid.txt", "-rea"});
    }
    
    @Test
    public void basic() throws IOException {
        JDCompiler.main(new String[] {inputPath+"basic.jd", "-o", outputPath+"basic", "-svg"});
    }

    @Test
    public void fig1() throws IOException {
        JDCompiler.main(new String[] {inputPath+"fig1.jd", "-o", outputPath+"fig1", "-svg"});
    }
 
    @Test
    public void fig2() throws IOException {
        JDCompiler.main(new String[] {inputPath+"fig2.jd", "-o", outputPath+"fig2", "-gv"});
    }
 
    @Test
    public void fig3() throws IOException {
        JDCompiler.main(new String[] { inputPath+"fig3.jd", "-o", outputPath+"fig3", "-svg"});
    }
    
    @Test
    public void figToDO() throws IOException {
        JDCompiler.main(new String[] { inputPath+"fig3.jd", "-o", outputPath+"fig2", "-td"});
    }


    
}
