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


public class JDCompiler {

	public static void main(String[] args) throws IOException {
		CommandLine cmd = setup(args);
		String outputOption = cmd.getOptionValue("output");

		if (outputOption != null) {
			if (!outputIsValid(outputOption)) {
				System.exit(1);
			}
		}
		int numOutput = -1;
		for (int i = 0; i < cmd.getArgs().length; ++i) {
			String inputFile = cmd.getArgs()[i];
			String inputRealizationFile = null;
			String inputInformationFile = null;
			numOutput++;
			if (!inputIsValid(inputFile)) {
				continue;
			}
			if (cmd.hasOption("rea") && inputIsValid(cmd.getArgs()[i + 1]) && i < cmd.getArgs().length) {
				i++;
				inputRealizationFile = cmd.getArgs()[i];

			}
			if (cmd.hasOption("info") && inputIsValid(cmd.getArgs()[i + 1]) && i < cmd.getArgs().length) {
				i++;
				inputInformationFile = cmd.getArgs()[i];

			}
			generateFiles(cmd, inputFile, generateOutputName(outputOption, numOutput, inputFile), inputRealizationFile,inputInformationFile);
		}
	}

	private static CommandLine setup(String[] args) {
		Options options = new Options();
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();

		Option output = new Option("o", "output", true, "output file path");
		options.addOption(output);

		options.addOption("svg", "generate graph");
		options.addOption("gv", "generate gv file");
		options.addOption("td", "generate todo list");
		options.addOption("svgR", "generate realization graph");
		options.addOption("rea", "indicate the realization file");
		options.addOption("info", "indicate the information file");

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
			String inputRealizationFilePath,String inputInformationFile) throws IOException {
		System.out.println("Generate from \n" + new File(inputFilePath).getAbsolutePath() + "\nTo  \n"
				+ new File(outputFilePath).getAbsolutePath());
		if (inputRealizationFilePath != null) {
			System.out.println("With Realization \n" + new File(inputRealizationFilePath).getAbsolutePath() + "\n");
		}
		if (inputInformationFile != null) {
			System.out.println("And With Information \n" + new File(inputInformationFile).getAbsolutePath() + "\n");
		}
		
		
		JustificationDiagram diagram = createDiagram(inputFilePath);
		
		// if inputInformationFile is not null, I analyse the information file
		if(inputInformationFile != null) {
			InformationNodeParsing informationNodeParsing = new InformationNodeParsing(inputInformationFile);	
			diagram.setInformationNode(informationNodeParsing.information);
		}
		
		// if inputRealizationFilePath is not null, I analyze the realization file and the state of the nodes
		if(inputRealizationFilePath != null) {
			RealizationParser realizationParser = new RealizationParser(inputRealizationFilePath);
			diagram.analysesDiagrammeRelation(realizationParser.labelList);

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
						"\nimpossible to generate a realization diagram without a realization file. Please add a realization file and the information file\n"
								+ "'example.jd -o output/example -rea example/realization.txt info example/information.json -svgR'");
			}

		}
		if (cmd.hasOption("svg")) {
			GraphDrawer drawer = new GraphDrawer();
			StringBuilder gv = drawer.draw(diagram);

			InputStream dot = new ByteArrayInputStream(gv.toString().getBytes());
			MutableGraph g = new guru.nidi.graphviz.parse.Parser().read(dot);
			Graphviz.fromGraph(g).render(Format.PNG).toFile(new File(outputFilePath + ".png"));
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
			String res = inputOption.split("\\.")[0];
			return res;
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
		JDLinker linker = new JDLinker(factory.diagram);
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
