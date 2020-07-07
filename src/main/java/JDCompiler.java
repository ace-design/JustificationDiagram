import export.GraphDrawer;
import export.GraphDrawerRealization;
import export.RequirementsLister;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import justificationDiagram.JustificationDiagram;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.cli.*;
import parsing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class JDCompiler {

	
	public static void main(String[] args) throws IOException {
		CommandLine cmd = setup(args);
		
		
		String outputOption = cmd.getOptionValue("output");
		if ( (outputOption != null) && (!outputIsValid(outputOption) ) ) {
				System.exit(1);
		}
		
		String inputFile = null;
		for (int i = 0; i < cmd.getArgs().length; ++i) {
			inputFile = cmd.getArgs()[i];
			if (inputIsValid(inputFile)) {
				break;
			}
		}
		
		if (inputFile == null) {
			System.err.println("A justification pattern is required : no input diagram in " + args);
			System.exit(1);
		}
		String inputRealizationFile = null;
		String inputActionFile = null;
		
		if (cmd.hasOption("rea")) {
				inputRealizationFile = cmd.getOptionValue("rea");
		}
		
		if (cmd.hasOption("act")) {
			inputActionFile = cmd.getOptionValue("act");
		}
			
		//TODO : Why this number?
		generateFiles(cmd, inputFile, 
				generateOutputName(outputOption, 0, inputFile), 
				inputRealizationFile,
				inputActionFile);
		
	}

	private static CommandLine setup(String[] args) {
		Options options = new Options();
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();

		Option output = new Option("o", "output", true, "output file path");
		options.addOption(output);
		
		Option rea = new Option("rea","realization",true, "indicate the realization file");
		Option action = new Option("act","action",true, "indicate the action file");

		options.addOption(rea);
		options.addOption(action);

		options.addOption("svg", "generate graph");
		options.addOption("gv", "generate gv file");
		options.addOption("td", "generate todo list");
		options.addOption("svgR", "generate realization graph");
		
		try {
			return parser.parse(options, args);
		} catch (org.apache.commons.cli.ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("utility-name", options);

			System.exit(1);
			return null;
		}
	}

	private static void generateFiles(CommandLine cmd, String inputFilePath, String outputFilePath,
			String inputRealizationFilePath,String inputActionFile) throws IOException {
		System.out.println("Generate from \n" + new File(inputFilePath).getAbsolutePath() + "\nTo  \n"
				+ new File(outputFilePath).getAbsolutePath());
		if (inputRealizationFilePath != null) {
			System.out.println("With Realization \n" + new File(inputRealizationFilePath).getAbsolutePath() + "\n");
		}
		if (inputActionFile != null) {
			System.out.println("And With Action \n" + new File(inputActionFile).getAbsolutePath() + "\n");
		}
		
		
		JustificationDiagram diagram = createDiagram(inputFilePath);
		
		// if inputActionFile is not null, I analyze the action file
		if(inputActionFile != null) {
			ActionNodeParsing actionNodeParsing = new ActionNodeParsing(inputActionFile);	
			diagram.setActionNode(actionNodeParsing.getActionNodes());
		}
		
		// if inputRealizationFilePath is not null, I analyze the realization file and the state of the nodes
		if(inputRealizationFilePath != null) {
			RealizationParser realizationParser;
			List<String> labels = new ArrayList<>();
			try {
				realizationParser = new RealizationParser(inputRealizationFilePath);
				labels = realizationParser.getLabelList();
			} catch (ExceptionParsingRealizationFile e) {
				System.out.println("Error reading Realzation File" + e);
			}
			diagram.analysesDiagrammeRelation(labels);

		}
		
		if (cmd.hasOption("svgR")) {
			if (cmd.hasOption("rea")) {
				GraphDrawerRealization drawer = new GraphDrawerRealization();
				StringBuilder gv = drawer.draw(diagram);

				InputStream dot = new ByteArrayInputStream(gv.toString().getBytes());
				MutableGraph g = new guru.nidi.graphviz.parse.Parser().read(dot);
				Graphviz.fromGraph(g).render(Format.SVG).toFile(new File(outputFilePath + "_REA" + ".svg"));
			} else {
				System.err.println(
						"\nimpossible to generate a realization diagram without a realization file. Please add a realization file and the action file\n"
								+ "'example.jd -o output/example -rea example/realization.txt -act example/action.json -svgR'");
			}

		}
		if (cmd.hasOption("svg")) {
			GraphDrawer drawer = new GraphDrawer();
			StringBuilder gv = drawer.draw(diagram);

			InputStream dot = new ByteArrayInputStream(gv.toString().getBytes());
			MutableGraph g = new guru.nidi.graphviz.parse.Parser().read(dot);
			Graphviz.fromGraph(g).render(Format.SVG).toFile(new File(outputFilePath + ".svg"));
		}
		if (cmd.hasOption("gv")) {
			GraphDrawer drawer = new GraphDrawer();
			StringBuilder gv = drawer.draw(diagram);

			PrintWriter out = new PrintWriter(new FileWriter(outputFilePath + ".gv"));
			out.print(gv);
			out.close();
		}
		if (cmd.hasOption("td")) {
			PrintWriter out = new PrintWriter(new FileWriter(outputFilePath + ".todo"));
			RequirementsLister lister = new RequirementsLister();
			out.print(lister.generate(diagram));
			out.close();
		}
	}

	private static boolean inputIsValid(String in) {
		if (!in.matches(".*\\.(jd|txt|json)")) {
			System.err.println(in + " is not a valid name. The input file should end with .jd or .txt");
			return false;
		}
		return true;
	}

	private static String generateOutputName(String outputOption, int index, String inputOption) {
		if (outputOption != null) {
			if (index > 0) {
				return outputOption + index;
			} else {
				return outputOption;
			}
		} else {
			return inputOption.split("\\.")[0];
		}
	}

	private static boolean outputIsValid(String out) {
		if (out.matches(".*\\..*")) {
			System.err.println(out + "The output file should not have an extension");
			return false;
		}

		// Create path to outputfile if needed
		File outputFile = new File(out);
		if (!(outputFile.exists())) {
			String[] parts = out.split("/");
			if (parts.length != 1) {
				String outDir = out.substring(0, out.lastIndexOf('/'));
				new File(outDir).mkdirs();
			}
		}

		return true;
	}

	public static JustificationDiagram createDiagram(String file) {

		JDInitializer factory = new JDInitializer();
		factory.visit(parseAntlr(file));
		JDLinker linker = new JDLinker(factory.getDiagram());
		linker.visit(parseAntlr((file)));

		return linker.diagram;
	}

	public static ParseTree parseAntlr(String file) {
		// create a CharStream that reads from standard input
		CharStream input = null; // create a lexer that feeds off of input CharStream
		try {
			input = CharStreams.fromFileName(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JustificationDiagramLexer lexer = new JustificationDiagramLexer(input); // create a buffer of tokens pulled from
																				// the lexer
		CommonTokenStream tokens = new CommonTokenStream(lexer); // create a parser that feeds off the tokens buffer
		JustificationDiagramParser parser = new JustificationDiagramParser(tokens);
		return parser.diagram();
	}
 
}
