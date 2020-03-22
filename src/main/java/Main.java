import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        JustificationDiagram diagram = createDiagram("JustificationDiagram.txt");
        diagram.createGraph("graph.gv");
    }

    public static JustificationDiagram createDiagram(String file) {
        // create a CharStream that reads from standard input
        CharStream input = null; // create a lexer that feeds off of input CharStream
        try {
            input = CharStreams.fromFileName(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PlantUMLLexer lexer = new PlantUMLLexer(input); // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer); // create a parser that feeds off the tokens buffer
        PlantUMLParser parser = new PlantUMLParser(tokens);
        ParseTree tree = parser.diagram();

        JustificationCompiler compiler = new JustificationCompiler();
        compiler.visit(tree);
        return compiler.diagram;
    }
}
