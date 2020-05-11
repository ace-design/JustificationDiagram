import org.junit.jupiter.api.Test;
import parsing.JDCompiler;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutputTests {

	String outputPath = "output/images/";
    @Test
    public void dummy() {
        assertEquals(0, 0);
        assertEquals(1, 1);
    }

    @Test
    public void basic() throws IOException {
    	
        JDCompiler.main(new String[] {"-i", "src/test/resources/basic.jd", "-o", outputPath+"basic.png", "-gv"});
    }

    @Test
    public void fig1() throws IOException {
        JDCompiler.main(new String[] {"-i", "src/test/resources/fig1.jd", "-o", outputPath+"fig1.png", "-gv"});
    }

    @Test
    public void fig2() throws IOException {
        JDCompiler.main(new String[] {"-i", "src/test/resources/fig2.jd", "-o", outputPath+"fig2.png", "-gv"});
    }

    @Test
    public void fig3() throws IOException {
        JDCompiler.main(new String[] {"-i", "src/test/resources/fig3.jd", "-o", outputPath+"fig3.png", "-gv"});
    }
}
