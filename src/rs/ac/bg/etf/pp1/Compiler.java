package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;

public class Compiler {

	private static FileAppender fileAppender, fileAppenderError;
	private static Logger log = Logger.getLogger("info");
	private static Logger logError = Logger.getLogger("error");
	
	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	static void tsdump() {
		MyTab.dump();
	}
	
	// args[0] = "test/test301_A.mj"
	// args[1] = "test/program.obj"
	public static void main(String[] args) throws Exception {
		
		if(args.length < 2) {
			log.error("Nema dovoljno ulaznih argumenata! Ocekivano: ulaz.mj izlaz.obj");
			return;
		}
		
		if(args.length >= 3 && args[2].equalsIgnoreCase(">izlaz.out")) {
			fileAppender = new FileAppender();
			fileAppender.setLayout(new PatternLayout(PatternLayout.DEFAULT_CONVERSION_PATTERN));
			fileAppender.setFile("test/izlaz.out");
			fileAppender.activateOptions();
			log.addAppender(fileAppender);
		}
		
		if(args.length == 4 && args[3].equalsIgnoreCase("2>izlaz.err")) {
			fileAppenderError = new FileAppender();
			fileAppenderError.setLayout(new PatternLayout(PatternLayout.DEFAULT_CONVERSION_PATTERN));
			fileAppenderError.setFile("test/izlaz.err");
			fileAppenderError.activateOptions();
			logError.addAppender(fileAppenderError);
		}
		
		Reader br = null;
		try {
			File sourceCode = new File(args[0]);
			
			if(!sourceCode.exists()) {
				log.error("Ulazni fajl " + args[0] + " ne postoji!");
				return;
			}
			
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			MJParser parser = new MJParser(lexer);
	        Symbol s = parser.parse();  //pocetak parsiranja
	        
	        Program prog = (Program)(s.value); 
	        MyTab.myInit();
			// ispis sintaksnog stabla
			log.info("===================================");

			// ispis prepoznatih programskih konstrukcija
			SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
			prog.traverseBottomUp(semanticAnalyzer); 
			
			log.info("===================================");
			tsdump();
			
			if(!parser.errorOccured && semanticAnalyzer.passed()){
				File objFile = new File(args[1]);
				if(objFile.exists()) objFile.delete();
				
				CodeGenerator codeGenerator = new CodeGenerator(semanticAnalyzer.getProgramObj());
				prog.traverseBottomUp(codeGenerator);
				
				Code.dataSize = semanticAnalyzer.nVars;
				Code.mainPc = codeGenerator.getMainPC();
				Code.write(new FileOutputStream(objFile));
				
				log.info("Parsiranje uspesno zavrseno!");
			}else{
				log.error("Parsiranje NIJE uspesno zavrseno!");
			}
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}

	}
	

	
	
}
