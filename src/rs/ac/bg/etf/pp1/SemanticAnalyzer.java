package rs.ac.bg.etf.pp1;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;

public class SemanticAnalyzer extends VisitorAdaptor {
	/*
	 * BELESKE - Designator ce se uvek koristiti prilikom pristupa vec deklarisanoj
	 * promenljivi - IDENT mora biti definisan kao String unutar parsera da bi se
	 * dodao kao promenljiva cvorovima -
	 */

	/* Pomocne f-je za ispis info i gresaka =========================== */
	private static Logger log = Logger.getLogger("info");
	private static Logger logError = Logger.getLogger("error");

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder("ERROR: " + message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		logError.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder("\n" + message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}
	//////////////////////////////////////////////////////////////////////

	// Cuva vrednost tipa liste deklaracija promenljivih
	private boolean errorDetected = false;
	private Struct varType = MyTab.noType;
	private MyMethod currentMethod = null;
	private ArrayList<String> listOfLabels = new ArrayList();
	private ArrayList<Struct> listOfActualParams = null;
	private ArrayList<MyMethod> listOfMethods = new ArrayList();
	private MyMethod calledMethod = null;
	int nVars;
	
	DumpSymbolTableVisitor tableVisitor = new DumpSymbolTableVisitor();
	
	// Program Obj
	private Obj programObj; 
	
	// Array
	private int arrayDimensionCount = 0;
	private String currentVariableName;
	private String currentDesignatorName;
	private Struct newArrayType = null;
	
	public Obj getProgramObj() {
		return programObj;
	}
	
	public boolean checkListOfActualParams(SyntaxNode info) {
		if (listOfActualParams == null) {
			report_error("Lista stvarnih parametara je prazna!", info);
			return false;
		}

		return true;
	}

	public boolean checkCalledMethod(SyntaxNode info) {
		if (calledMethod == null) {
			report_error("CalledMethod je null!", info);
			return false;
		}

		return true;
	}

	/*
	 * OBILAZAK GLAVNOG PROGRAMA
	 * =====================================================
	 */
	// Pocetak
	public void visit(ProgramName programName) {
		programName.obj = MyTab.insert(Obj.Prog, programName.getProgName(), MyTab.noType);
		MyTab.openScope();
	}

	// Kraj
	public void visit(Program program) {
		nVars = MyTab.currentScope.getnVars();
		// Vezujemo simbole trenutnog scope-a za glavni program(prvi scope)
		programObj = program.getProgramName().obj;
		MyTab.chainLocalSymbols(programObj);
		MyTab.closeScope();
	}
	////////////////////////////////////////////////////////////////////////////////////

	public void visit(Type type) {
		// Pronadji tip u tabeli simbola
		Obj temp = MyTab.find(type.getTypeName());

		// noObj se vraca ako nije pronadjeno u tabeli simbola
		if (temp == MyTab.noObj) {
			// Nije pronadjen tip

			report_error("Greska: Tip " + type.getTypeName() + " nije pronadjen u tabeli simbola!", null);
			varType = type.struct = MyTab.noType;
		} else {
			// Tip je pronadjen

			// Da li je tipa Type?
			if (temp.getKind() == Obj.Type) {
				// Jeste

				varType = type.struct = temp.getType();
			} else {
				// Nije

				report_error("Greska: Pronadjen tip " + type.getTypeName() + " nije tipa Type!", type);
				varType = type.struct = MyTab.noType;
			}
		}
	}

	/*
	 * OBILAZAK GLOBALNIH I LOKALNIH PROMENLJIVIH
	 * ==========================================
	 */
	// Za sad ih samo dodajem u tabelu simbola

	public void visit(VarDeclaration_Item varDeclaration_Item) {
		if(arrayDimensionCount == 1) {
			// Array
			Tab.insert(Obj.Var, currentVariableName, MyStruct.AddNewArray(varType));
		}else if(arrayDimensionCount == 0) {
			// Variable
			Tab.insert(Obj.Var, currentVariableName, varType);
		}else {
			report_error("Varijabla " + currentVariableName + " ima nedozvoljen broj dimenzija!", varDeclaration_Item);
		}
		
		arrayDimensionCount = 0;
		currentVariableName = "";
	}
	
	public void visit(VarDeclaration_Variable varDeclVariable) {
		currentVariableName = varDeclVariable.getVarName();
		if(Tab.find(currentVariableName) != Tab.noObj) {
			report_error("Varijabla " + currentVariableName + " vec postoji!", varDeclVariable);
		}
	}

	public void visit(VarDeclaration_Array varDeclArray) {
		arrayDimensionCount++;
	}
	//////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * GLOBALNE KONSTANTE
	 * =============================================================================
	 * ===========
	 */
	public void visit(ConstNumber constNumber) {
		Obj o = MyTab.find(constNumber.getConstName());
		if(o == Tab.noObj) {
			// Tip mora da bude int
			if (varType == MyTab.intType) {
				MyTab.insert(Obj.Con, constNumber.getConstName(), varType);
				// report_info("Konstanta " + constNumber.getConstName() + " dodata u Tabelu
				// Simbola!", constNumber);
			} else {
				report_error("Konstanta " + constNumber.getConstName() + " nije tipa Integer!", constNumber);
			}
		}else {
			report_error("Vec postoji promenljiva sa imenom " + constNumber.getConstName(), constNumber);
		}
		
	}

	public void visit(ConstChar constChar) {
		Obj o = MyTab.find(constChar.getConstName());
		if(o == Tab.noObj) {
			// Tip mora da bude int
			if (varType == MyTab.charType) {
				MyTab.insert(Obj.Con, constChar.getConstName(), varType);
				// report_info("Konstanta " + constChar.getConstName() + " dodata u Tabelu
				// Simbola!", constChar);
			} else {
				report_error("Konstanta " + constChar.getConstName() + " nije tipa Char!", constChar);
			}
		}else {
			report_error("Vec postoji promenljiva sa imenom " + constChar.getConstName(), constChar);
		}
		

	}

	public void visit(ConstBool constBool) {
		Obj o = MyTab.find(constBool.getConstName());
		if(o == Tab.noObj) {
			// Tip mora da bude int
			if (varType == MyTab.boolType && (constBool.getValue() == 0 || constBool.getValue() == 1)) {

				MyTab.insert(Obj.Con, constBool.getConstName(), varType);
				// report_info("Konstanta " + constBool.getConstName() + " dodata u Tabelu
				// Simbola!", constBool);

			} else {
				report_error("Tip konstante " + constBool.getConstName() + " nije Boolean!", constBool);
			}
		}else {
			report_error("Vec postoji promenljiva sa imenom " + constBool.getConstName(), constBool);
		}
		

	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * DESIGNATOR STATEMENTS
	 * ===========================================================================
	 */

	public void visit(DesignatorStatement_Assignop desStmt_AssignOp) {
		Obj designatorObject = desStmt_AssignOp.getDesignatorTemp().obj;

		int designatorKind = designatorObject.getKind();
		
		if (desStmt_AssignOp.getAssignment().struct != null) {
		
			Struct rightType = checkIfArray(desStmt_AssignOp.getAssignment().struct);
			Struct leftType = checkIfArray(designatorObject.getType());

			if (rightType != leftType) {

				if (rightType.assignableTo(leftType)) {
					return;
				}
				report_error("Nekompatibilni tipovi!", desStmt_AssignOp);
			}
		} else {
			report_error("Vrednost sa desne strane iskaza nije korektna!", desStmt_AssignOp);
		}
	}

	public void visit(Assignment_Expr assignment_Expr) {
		assignment_Expr.struct = assignment_Expr.getExpr().struct;
	}

	public void visit(DesignatorTemp designatorTemp) {
		designatorTemp.obj = designatorTemp.getDesignator().obj;
		Obj desObj = Tab.find(designatorTemp.obj.getName());
		
		
		if(desObj != Tab.noObj) {
			
			if(desObj.getKind() == Obj.Var || desObj.getKind() == Obj.Elem || desObj.getKind() == Obj.Con) {
				if(arrayDimensionCount > 0) {
					// Array
					
					if(isVarLocal(desObj.getName())) {
						// Local
						report_info("Pristupa se lokalnom nizu [" + desObj.getName() + "]", designatorTemp);
					}else {
						// Global
						report_info("Pristupa se globalnom nizu [" + desObj.getName() + "]", designatorTemp);
					}
					
					arrayDimensionCount = 0;
					
				}else if(arrayDimensionCount == 0) {
					// Promenljiva
					
					if(isVarLocal(desObj.getName())) {
						// Local
						report_info("Pristupa se lokalnoj promenljivoj [" + desObj.getName() + "]", designatorTemp);
					}else {
						// Global
						report_info("Pristupa se globalnoj promenljivoj [" + desObj.getName() + "]", designatorTemp);
					}
					
				}
				
				// Poseti cvor objekta iz tabele simbola
				tableVisitor = new DumpSymbolTableVisitor();
				tableVisitor.visitObjNode(desObj);
				System.out.println(tableVisitor.getOutput() + "\n");
				tableVisitor = null;
				
			}else {
				report_error("Designator " + desObj.getName() + " nije Varijabla ni Element niza!", designatorTemp);
			}
			
			
		}else {
			report_error("Designator " + desObj.getName() + " nije pronadjena u tabeli simbola!", designatorTemp);
		}
		
	}
	
	public void visit(DesignatorName designatorName) {
		designatorName.obj = Tab.find(designatorName.getName());
	}
	
	public void visit(Designator_Var desVar) {
		desVar.obj = desVar.getDesignatorName().obj;
	}

	public void visit(Designator_Array designator_Array) {
		
		if(designator_Array.getExpr().struct != Tab.intType) {
			report_error("Dimenzije niza moraju da budu Integer!", designator_Array);
		}
		
		arrayDimensionCount++;
		designator_Array.obj = designator_Array.getDesignator().obj;
		
	}

	public void visit(DesignatorStatement_Inc designatorStatement_Inc) {
		Obj designatorObject = designatorStatement_Inc.getDesignatorTemp().obj;

		// desStmt_AssignOp.getDesignator().obj = LeftSideObj;
		int designatorKind = designatorObject.getKind();
		// Designator mora da bude promenljiva, elem niza ili polje objekta
		if (designatorKind == Obj.Var || designatorKind == Obj.Elem || designatorKind == Obj.Fld) {

			// Tip designatora mora biti Integer
			if (designatorObject.getType() != MyTab.intType) {
				report_error("Semanticka greska: Designator nije tipa Integer!", designatorStatement_Inc);
			} else {
				// report_info("Uspesno inkrementirana vrednost promenljivoj " +
				// designatorObject.getName(), null);
			}
		}
	}

	public void visit(DesignatorStatement_Dec designatorStatement_Dec) {
		Obj designatorObject = designatorStatement_Dec.getDesignatorTemp().obj;

		// desStmt_AssignOp.getDesignator().obj = LeftSideObj;
		int designatorKind = designatorObject.getKind();
		// Designator mora da bude promenljiva, elem niza ili polje objekta
		if (designatorKind == Obj.Var || designatorKind == Obj.Elem || designatorKind == Obj.Fld) {

			// Tip designatora mora biti Integer
			if (designatorObject.getType() != MyTab.intType) {
				report_error("Semanticka greska: Designator nije tipa Integer!", designatorStatement_Dec);
			} else {
				// report_info("Uspesno dekrementirana vrednost promenljivoj " +
				// designatorObject.getName(), null);
			}
		}
	}

	public void visit(DesignatorStatement_ActPars designatorStatement_ActPars) {

		if (!checkCalledMethod(designatorStatement_ActPars)) {
			return;
		}
		// report_info("Uspesan poziv funkcije " + calledMethod.obj.getName(),
		// designatorStatement_ActPars);
		// calledMethod = null;
		// listOfActualParams = null;
	}

	public void visit(CalledFunctionName calledFunctionName) {
		Obj designatorObj = calledFunctionName.getDesignator().obj;

		if (designatorObj != MyTab.noObj) {
			listOfActualParams = new ArrayList<>();

			// Designator mora biti tipa Method
			if (designatorObj.getKind() == Obj.Meth) {

				calledMethod = getMethod(designatorObj.getName());

				if (calledMethod != null) {
					// report_info("Pronadjen poziv funkcije " + designatorObj.getName(),
					// calledFunctionName);
				} else {
					report_error("Metoda " + designatorObj.getName() + " nije pronadjena!", calledFunctionName);
				}
			} else {
				report_error("Designator " + designatorObj.getName() + " nije tipa Method!", calledFunctionName);
			}
		} else {
			report_error("Pozvana metoda koja nije definisana!", calledFunctionName);
		}

	}

	public MyMethod getMethod(String name) {
		for (int i = 0; i < listOfMethods.size(); i++) {
			// report_info("Method name: " + listOfMethods.get(i).obj.getName(), null);
			if (name == listOfMethods.get(i).obj.getName()) {

				return listOfMethods.get(i);
			}
		}

		return null;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * Expressions
	 * ========================================================================
	 */
	public void visit(Expr_MinusTerm expr_MinusTerm) {

		if (expr_MinusTerm.getTerm().struct == MyTab.intType) {
			expr_MinusTerm.struct = expr_MinusTerm.getTerm().struct;
		} else {
			report_error("Term nije tipa int!", expr_MinusTerm);
		}

	}

	public void visit(Expr_AddopTerm expr_AddopTerm) {

		Struct exprType = expr_AddopTerm.getExpr().struct;
		Struct termType = expr_AddopTerm.getTerm().struct;

		if ((exprType == MyTab.intType && termType == MyTab.intType) || exprType.compatibleWith(termType)) {
			expr_AddopTerm.struct = expr_AddopTerm.getTerm().struct;
		} else {
			report_error("Expr i Term nisu kompatibilni", expr_AddopTerm);
		}

	}

	public void visit(Expr_Term expr_Term) {
		expr_Term.struct = expr_Term.getTerm().struct;
	}

	// Term
	public void visit(Term_MulopFactor term_MulopFactor) {
		Struct termType = term_MulopFactor.getTerm().struct;
		Struct factorType = term_MulopFactor.getFactor().struct;

		if (termType == MyTab.intType && factorType == MyTab.intType) {
			term_MulopFactor.struct = term_MulopFactor.getFactor().struct;
		} else {
			report_error("Term ili Factor nisu tipa int!", term_MulopFactor);
		}
	}

	public void visit(Term_Factor term_Factor) {
		term_Factor.struct = term_Factor.getFactor().struct;
	}

	// Factor
	public void visit(Factor_Number factor_Number) {
		factor_Number.struct = MyTab.intType;
	}

	public void visit(Factor_CharConst factor_CharConst) {
		factor_CharConst.struct = MyTab.charType;
	}

	public void visit(Factor_BoolConst factor_BoolConst) {
		factor_BoolConst.struct = MyTab.boolType;
	}

	public void visit(Factor_Designator factor_Designator) {
		factor_Designator.struct = checkIfArray(factor_Designator.getDesignatorTemp().obj.getType());
	}

	public void visit(FactorArray_Dimension factorArray_Dimension) {
		
		arrayDimensionCount++;
		factorArray_Dimension.struct = factorArray_Dimension.getFactorArray().struct;
		
		if(factorArray_Dimension.getExpr().struct != Tab.intType) {
			report_error("Expr u nizu mora biti tipe Integer!", factorArray_Dimension);
		}
		
	}
	
	public void visit(FactorArray_New factorArray_New) {
		factorArray_New.struct = factorArray_New.getType().struct;
	}
	
	public void visit(Factor_NewExpr factor_NewExpr) {
		if(arrayDimensionCount == 1) {
			Struct temp = factor_NewExpr.getFactorArray().struct;
			factor_NewExpr.struct = MyStruct.AddNewArray(temp);
		}
		
		arrayDimensionCount = 0;
	}

	public void visit(Factor_Expr factor_Expr) {
		factor_Expr.struct = factor_Expr.getExpr().struct;
	}
	/////////////////////////////////////////////////////////////////////////////////////////

	/* METHODS =============================================================== */
	public void visit(MethReturnType methReturnType) {
		Obj temp = MyTab.insert(Obj.Meth, methReturnType.getMethodName(), methReturnType.getType().struct);
		currentMethod = new MyMethod(temp);

		// Dodati metodu u listu metoda
		listOfMethods.add(currentMethod);

		if (currentMethod == null) {
			report_error("Metoda " + methReturnType.getMethodName() + " nije dodata u tabelu simbola!", methReturnType);
		}

		methReturnType.obj = currentMethod.obj;
		MyTab.openScope();
		// report_info("Obradjuje se funkcija " + methReturnType.getMethodName(),
		// methReturnType);
	}

	public void visit(VoidMethReturnType voidMethReturnType) {
		Obj temp = MyTab.insert(Obj.Meth, voidMethReturnType.getMethodName(), MyTab.noType);
		currentMethod = new MyMethod(temp);

		// Dodati metodu u listu metoda
		listOfMethods.add(currentMethod);

		if (currentMethod == null) {
			report_error("Metoda " + voidMethReturnType.getMethodName() + " nije dodata u tabelu simbola!",
					voidMethReturnType);
		}

		voidMethReturnType.obj = currentMethod.obj;
		MyTab.openScope();
		// report_info("Obradjuje se funkcija " + voidMethReturnType.getMethodName(),
		// voidMethReturnType);
	}

	public void visit(MethodDeclaration methodDeclaration) {
		MyTab.chainLocalSymbols(currentMethod.obj);

		// Proveriti sve labele na koje se naletelo preko goto iskaza
		if (listOfLabels.size() > 0) {
			checkGotoLabels();
		}

		MyTab.closeScope();

		// report_info("Zavrseno obradjivanje funkcije " + currentMethod.obj.getName(),
		// null);
		currentMethod = null;
	}

	/* SINGLE STATEMENTS */
	public void visit(SingleStatement_GotoLabel singleStatement_GotoLabel) {

		Obj label = MyTab.findInCurrentScope(singleStatement_GotoLabel.getLabel().getLabelName());
		// Ako ne postoji labela - Skace se unapred
		if (label == MyTab.noObj) {

			// Dodati labelu u listu labela
			listOfLabels.add(singleStatement_GotoLabel.getLabel().getLabelName());

		} else {

			if (label.getKind() == MyObj.Lab) {
				// Labela se nalazi unutar trenutnog Scope-a
				// report_info("Labela " + singleStatement_GotoLabel.getLabel().getLabelName() +
				// " uspesno procitana!", null);

			} else {
				// Labela se ne nalazi unutar trenutnog Scope-a
				report_error("Labela " + singleStatement_GotoLabel.getLabel().getLabelName() + " nije vrste Label!",
						null);
			}
		}
	}

	public void visit(Statement_LabelSingleStatement statement_LabelSingleStatement) {
		
		Obj newLabel = MyTab.find(statement_LabelSingleStatement.getLabel().getLabelName());
		if(newLabel == Tab.noObj) {
			newLabel = MyTab.insert(MyObj.Lab, statement_LabelSingleStatement.getLabel().getLabelName(),
					Tab.noType);
			if (newLabel == null) {
				report_error("Labela " + statement_LabelSingleStatement.getLabel().getLabelName()
						+ " je NEUSPESNO dodata u tabelu simbola!", statement_LabelSingleStatement);
			} else {
				// report_info("Labela " +
				// statement_LabelSingleStatement.getLabel().getLabelName() + " uspesno
				// dodata!", statement_LabelSingleStatement);
			}
		}else {
			report_error("Labela " + statement_LabelSingleStatement.getLabel().getLabelName() + " je vec definisana!", statement_LabelSingleStatement);
		}
	}

	public void checkGotoLabels() {
		for (int i = 0; i < listOfLabels.size(); i++) {
			Obj label = MyTab.findInCurrentScope(listOfLabels.get(i));

			if (label.getKind() == MyObj.Lab) {
				// Labela se nalazi unutar trenutnog Scope-a
				// report_info("Labela " + listOfLabels.get(i) + " uspesno procitana!", null);

			} else {
				// Labela se ne nalazi unutar trenutnog Scope-a
				report_error("Labela " + listOfLabels.get(i) + " se ne nalazi unutar trenutnog Scope-a!", null);
			}
		}
	}

	public void visit(SingleStatement_ReadStatement singleStatement_ReadStatement) {
		Obj desObj = singleStatement_ReadStatement.getDesignatorTemp().obj;

		if (desObj.getKind() == Obj.Var || desObj.getKind() == Obj.Elem || desObj.getKind() == Obj.Fld) {

			if (desObj.getType() == MyTab.intType || desObj.getType() == MyTab.charType
					|| desObj.getType() == MyTab.boolType) {

				// report_info("Read Statement uspesno obradjen!",
				// singleStatement_ReadStatement);

			} else {
				report_error("ReadStmt: Designator " + desObj.getName() + " nije odgovarajuceg tipa!",
						singleStatement_ReadStatement);
			}

		} else {
			report_error("ReadStmt: Designator " + desObj.getName() + " nije odgovarajuce vrste!",
					singleStatement_ReadStatement);
		}
	}

	public void visit(SingleStatement_PrintExpr singleStatement_PrintExpr) {
		Struct exprType = singleStatement_PrintExpr.getExpr().struct;

		if (exprType != MyTab.intType && exprType != MyTab.charType && exprType != MyTab.boolType) {
			report_error("PrintStmt: Expr nije odgovarajuceg tipa!", singleStatement_PrintExpr);
		}
	}

	public void visit(SingleStatement_PrintExprNumber singleStatement_PrintExprNumber) {
		Struct exprType = singleStatement_PrintExprNumber.getExpr().struct;

		if (exprType != MyTab.intType && exprType != MyTab.charType && exprType != MyTab.boolType) {
			report_error("PrintStmt: Expr nije odgovarajuceg tipa!", singleStatement_PrintExprNumber);
		}
	}

	/* FORM PARS & ACTUAL PARS - METHOD */
	public void visit(FormParam_Variable formParam_Variable) {
		formParam_Variable.struct = formParam_Variable.getType().struct;
		Obj temp = MyTab.insert(Obj.Var, formParam_Variable.getFormParamName(), formParam_Variable.getType().struct);
		if (temp == MyTab.noObj) {
			report_error(
					"Formalni parametar " + formParam_Variable.getFormParamName() + " nije dodat u tabelu simbola!",
					formParam_Variable);
		}
	}

	public void visit(FormParam_Array formParam_Array) {
		formParam_Array.struct = MyStruct.AddNewArray(formParam_Array.getType().struct);
		Obj temp = MyTab.insert(Obj.Var, formParam_Array.getFormParamName(),
				MyStruct.AddNewArray(formParam_Array.getType().struct));
		if (temp == MyTab.noObj) {
			report_error("Formalni parametar " + formParam_Array.getFormParamName() + " nije dodat u tabelu simbola!",
					formParam_Array);
		}
	}

	public void visit(MultipleFormParameter multipleFormParameter) {
		currentMethod.formalParams.add(multipleFormParameter.getParameterItem().struct);
		// report_info("Act Param: " +
		// currentMethod.formalParams.get(currentMethod.formalParams.size() - 1),
		// multipleFormParameter);
	}

	public void visit(SingleFormParameter singleFormParameter) {
		currentMethod.formalParams.add(singleFormParameter.getParameterItem().struct);
		// report_info("Act Param: " +
		// currentMethod.formalParams.get(currentMethod.formalParams.size() - 1),
		// singleFormParameter);
	}

	public void visit(ExprList_MultipleExpr exprList_MultipleExpr) {
		if (!checkListOfActualParams(exprList_MultipleExpr)) {
			return;
		}

		// Dodaje Expr u OBRNUTOM redosledu
		// report_info("Act Param: " + exprList_MultipleExpr.getExpr().struct,
		// exprList_MultipleExpr);
		listOfActualParams.add(exprList_MultipleExpr.getExpr().struct);
		// report_info("Act Param: " + listOfActualParams.get(listOfActualParams.size()
		// - 1), exprList_MultipleExpr);
	}

	public void visit(ExprList_SingleExpr exprList_SingleExpr) {
		if (!checkListOfActualParams(exprList_SingleExpr)) {
			return;
		}
		// Dodaje prvi Parametar
		listOfActualParams.add(exprList_SingleExpr.getExpr().struct);
	}

	public void visit(ActPars_List actPars_List) {

		if (!checkListOfActualParams(actPars_List) || !checkCalledMethod(actPars_List)) {
			return;
		}

		// Provera da li je jednak broj Form params i Act params
		if (listOfActualParams.size() == calledMethod.formalParams.size()) {

			int size = listOfActualParams.size();
			for (int i = 0; i < size; i++) {

				if (listOfActualParams.get(i) != calledMethod.formalParams.get(i)) {
					report_error("Tip " + (i + 1) + ". argumenta se ne poklapa!", actPars_List);
				}
			}

		} else {
			report_error("Broj argumenata nije isti!", actPars_List);

		}

	}

	public void visit(ActPars_NoPars actPars_NoPars) {

		if (!checkListOfActualParams(actPars_NoPars) || !checkCalledMethod(actPars_NoPars)) {
			return;
		}

		if (calledMethod.formalParams.size() > 0) {
			report_error("Broj argumenata nije isti!", actPars_NoPars);
		}

	}

	/* OSTATAK */

	/* True - Local, False - Global */
	public boolean isVarLocal(String varName) {
		Obj temp = Tab.find(varName);
		if(temp.getLevel() == 0) {
			return false;
		}else {
			return true;
		}
	}
	
	public Struct checkIfArray(Struct type) {
		Struct tempType = type;

		if (tempType.getKind() == Struct.Array) {
			tempType = tempType.getElemType();
		}

		return tempType;
	}

	public boolean passed() {
		return !errorDetected;
	}
}
