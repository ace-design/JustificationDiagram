import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

public class JDCompiler {

    public static void main(String[] args) throws IOException {
        JustificationDiagram diagram = createDiagram("JustificationDiagram.txt");
        GraphDrawer drawer = new GraphDrawer();
        drawer.draw(diagram, "graph.gv");
    }

    public static JustificationDiagram createDiagram(String file) {
        JDFactory factory = new JDFactory();
        factory.visit(parseAntlr(file));
        return factory.diagram;
    }

    public static ParseTree parseAntlr(String file) {
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
        return parser.diagram();
    }
}
