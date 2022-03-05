package rs.ac.bg.etf.pp1;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.CounterVisitor.*;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class CodeGenerator_OLD extends VisitorAdaptor {

	public CodeGenerator_OLD(Obj scope) {
		programScope = scope;
	}
	
	/* Pomocne f-je za ispis info i gresaka =========================== */
	private static Logger log = Logger.getLogger("info");
	private static Logger logError = Logger.getLogger("error");
	boolean errorDetected = false;

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder("CodeGen ERROR: " + message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		logError.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder("\n CodeGen INFO: " + message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString() + "\n");
	}
	//////////////////////////////////////////////////////////////////////
	
	// Promenljive
	public int mainPC;

	public Obj currentDesignatorObj = null;
	public Struct varDeclarationType = null;
	public boolean newArray = false;
	public Obj programScope;
	
	public ArrayList<Obj> tempVariables = new ArrayList<>();
	public ArrayList<Obj> variableList = new ArrayList<>();
	
	public ArrayList<Obj> globalConstants = new ArrayList<>();
	public ArrayList<Obj> localVariables = new ArrayList<>();
	public ArrayList<Obj> globalVariables = new ArrayList<>();
	
	int localVarCounter = 0;
	int globalVarCounter = 0;
	
	public ArrayList<MyLabel> listOfLabels = new ArrayList<>();
	public ArrayList<MyLabel> listOfFutureLabels = new ArrayList<>();
	
	public Obj getVarConst(String name) {
		
		for(int i=0; i<variableList.size(); i++) {
			if(variableList.get(i).getName().equalsIgnoreCase(name)) {
				return variableList.get(i);
			}
		}
		
		return null;
		
	}
	
	public MyLabel getLabel(String name, ArrayList<MyLabel> list) {
		for(int i=0; i<list.size(); i++) {
			// System.out.println(i + ". Labela: " + list.get(i));
			if(name.equalsIgnoreCase(list.get(i).getName())) {
				return list.get(i);
			}
		}
		return null;
	}
	
	public boolean isLocal(Obj obj) {
		if(obj.getLevel() == 0) {
			// Global
			return false;
		}
		else {
			// local
			return true;
		}
	}

	public int getMainPC() {
		return mainPC;
	}
	
	/* GENERISANJE KODA ZA POCETAK I KRAJ Main METODE */
	// Kod za ulaz u funkciju
	public void visit(VoidMethReturnType voidMethReturnType) {
		
		// Proveriti da li se radi o main f-ji, i postaviti vrednost mainPC-a
		if(voidMethReturnType.getMethodName().equalsIgnoreCase("main")){
			mainPC = Code.pc;
		}
		
		voidMethReturnType.obj.setAdr(Code.pc);
		
		// Prikupljanje broja formalnih argumenata i lokalnih promenljivih
		SyntaxNode methodNode = voidMethReturnType.getParent();
	
		VarCounter varCnt = new VarCounter();
		methodNode.traverseBottomUp(varCnt);
		
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseBottomUp(fpCnt);
		
		// enter formal formal+local
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(fpCnt.getCount() + varCnt.getCount());
	}
	
	// Kraj metode
	public void visit(MethodDeclaration methodDeclaration) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	/* PRINT STATEMENTS */
	public void visit(SingleStatement_PrintExpr singleStatement_PrintExpr) {
		Struct exprType = singleStatement_PrintExpr.getExpr().struct;
		
		if(exprType == MyTab.intType || exprType == MyTab.boolType) {
			Code.loadConst(5);
			Code.put(Code.print);
		}else if(exprType == MyTab.charType) {
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}
	
	public void visit(SingleStatement_PrintExprNumber singleStatement_PrintExprNumber) {
		Struct exprType = singleStatement_PrintExprNumber.getExpr().struct;
		
		if(exprType == MyTab.intType || exprType == MyTab.boolType) {
			// Drugi broj predstavlja sirinu 
			Code.loadConst(singleStatement_PrintExprNumber.getN2());
			Code.put(Code.print);
		}else if(exprType == MyTab.charType) {
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}

	
	public void visit(SingleStatement_ReadStatement singleStatement_ReadStatement) {
		// TODO: PROVERITI!
		if(currentDesignatorObj.getType() == Tab.charType) {
			Code.put(Code.bread);
		}else {
			Code.put(Code.read);
		}
		Code.store(currentDesignatorObj);
	}
	
	/* GLOBALNE KONSTANTE I PROMENLJIVE */
	public void visit(ConstNumber constNumber) {
		Obj newConst = new Obj(Obj.Con, constNumber.getConstName(), Tab.intType);
		newConst.setLevel(0);
		newConst.setAdr(constNumber.getValue());
		
		variableList.add(newConst);
	}
	public void visit(ConstChar constChar) {
		Obj newConst = new Obj(Obj.Con, constChar.getConstName(), Tab.charType);
		newConst.setLevel(0);
		newConst.setAdr(constChar.getValue());
		
		variableList.add(newConst);
	}	
	public void visit(ConstBool constBool) {
		Obj newConst = new Obj(Obj.Con, constBool.getConstName(), MyTab.boolType);
		newConst.setLevel(0);
		newConst.setAdr(constBool.getValue());
		
		variableList.add(newConst);
	}
	
	public void visit(VarDeclarations varDeclarations) {
		
		SyntaxNode parent = varDeclarations.getParent();
		
		if(DeclarationItem_VarDecl.class == parent.getClass()) {
			// Global
			for(int i=0; i<tempVariables.size(); i++) {
				
				Obj newVar = tempVariables.get(i);
				newVar.setLevel(0);
				newVar.setAdr(globalVarCounter);
				globalVarCounter++;
				
				variableList.add(newVar);
				//System.out.println("Nova globalna prom: " + newVar.getName());
			}
			
		}else {
			// Local
			for(int i=0; i<tempVariables.size(); i++) {
				
				Obj newVar = tempVariables.get(i);
				newVar.setLevel(1);
				newVar.setAdr(localVarCounter);
				localVarCounter++;
				
				variableList.add(newVar);
				//System.out.println("Nova lokalna prom: " + newVar.getName());
				
			}
		}
		
		tempVariables.clear();
	}

	public void visit(VarDeclaration_Variable varDeclaration_Variable) {
		Obj newVar = new Obj(Obj.Var, varDeclaration_Variable.getVarName(), varDeclarationType);
		tempVariables.add(newVar);
	}
	
//	public void visit(VarDeclaration_Array varDeclaration_Array) {
//		MyObj newVar = new MyObj(Obj.Var, varDeclaration_Array.getVarName(), MyStruct.AddNewArray(varDeclarationType));
//		tempVariables.add(newVar);
//	}
	
	public void visit(Type type) {
		SyntaxNode parent = type.getParent();
		if(parent.getClass() == VarDeclarations.class) {
			varDeclarationType = type.struct;
		}
	}
	
	/* FACTORS */
	public void visit(Factor_Number factor_Number) {
		Code.loadConst(factor_Number.getNum());
	}
	public void visit(Factor_CharConst factor_CharConst) {
		Code.loadConst(factor_CharConst.getCharConst());
	}
	public void visit(Factor_BoolConst factor_BoolConst) {
		Code.loadConst(factor_BoolConst.getBoolConst());
	}
	
	public void visit(Factor_Designator factor_Designator) {
		
	}

//	public void visit(Factor_NewExpr factor_NewExpr) {
//		Code.put(Code.newarray);
//		if(factor_NewExpr.getType().struct == Tab.charType) {
//			Code.put(0);
//		}else {
//			Code.put(1);
//		}
//		newArray = true;
//	}
	
	/* GLOBAL VARIABLES */

	/* EXPR */
	public void visit(Expr_MinusTerm expr_MinusTerm) {
		Code.put(Code.neg);
	}
	
	public void visit(Expr_AddopTerm expr_AddopTerm) {
		
		if(expr_AddopTerm.getAddop().getClass() == Addop_Plus.class) {
			Code.put(Code.add);
		}
		else if(expr_AddopTerm.getAddop().getClass() == Addop_Minus.class) {
			Code.put(Code.sub);
		}
		
		
	}
	
	public void visit(Term_MulopFactor term_MulopFactor) {
		
		if(term_MulopFactor.getMulop().getClass() == Mulop_Mul.class) {
			Code.put(Code.mul);
		}
		else if(term_MulopFactor.getMulop().getClass() == Mulop_Div.class) {
			Code.put(Code.div);
		}
		else if(term_MulopFactor.getMulop().getClass() == Mulop_Mod.class) {
			Code.put(Code.rem);
		}
	
	}
	
	
	/* DESIGNATOR STATEMENTS */
//	public void visit(DesignatorStatement_Assignop designatorStatement_Assignop) {
//
//			Obj o = getVarConst(designatorStatement_Assignop.getDesignator().obj.getName());
//			if(o != null) {
//				if(o.getType().getKind() == Struct.Array) {
//					
//					if(newArray) {
//						Code.store(o);
//						
//					}else {
//						if(o.getType().getElemType() == Tab.charType) {
//							Code.put(Code.bastore);
//						}else {
//							Code.put(Code.astore);
//						}
//					}
//
//				}else {
//					Code.store(o);
//				}
//			}
//			
//			newArray = false;
//		
//	}

//	public void visit(DesignatorStatement_Inc designatorStatement_Inc) {
//		Code.loadConst(1);
//		Code.put(Code.add);
//		Code.store(currentDesignatorObj);
//		currentDesignatorObj = null;
//	}
	
//	public void visit(DesignatorStatement_Dec designatorStatement_Dec) {
//		Code.loadConst(1);
//		Code.put(Code.sub);
//		Code.store(currentDesignatorObj);
//		currentDesignatorObj = null;
//	}
	
//	public void visit(Designator_Array designator_Array) {
//		
//		SyntaxNode parent = designator_Array.getParent();
//		
//		if(parent.getClass() == DesignatorStatement_Assignop.class) {
//			
//		}else {
//			Obj o = getVarConst(designator_Array.getDesignatorName().getName());
//			
//			if(o.getType().getKind() == Struct.Array) {
//				if(o.getType().getElemType() == Tab.charType) {
//					Code.put(Code.baload);
//				}else {
//					Code.put(Code.aload);
//				}
//			}
//		}
//
//	}
	
	public void visit(DesignatorName designatorName) {
		
		Obj o = getVarConst(designatorName.getName());
		if(o != null) {
			Code.load(o);
		}else {
			// System.out.println("Des " + designatorName.getName() + " nije pronadjen!");
		}
		
	}
	
	public void visit(Designator_Var designator_Var) {
		Obj o = getVarConst(designator_Var.obj.getName());
		
		currentDesignatorObj = o;
		
		SyntaxNode parent = designator_Var.getParent();
		
		if(parent.getClass() == DesignatorStatement_Assignop.class ||
				parent.getClass() == SingleStatement_ReadStatement.class) {
			
		}else {
			Code.load(o);
		}
	}

	/* LABEL */

	public void visit(Label label) {
		SyntaxNode parent = label.getParent();
		
		if(parent.getClass() == Statement_LabelSingleStatement.class) {
			
			MyLabel newLabel = getLabel(label.getLabelName(), listOfFutureLabels);
			if(newLabel != null) {
				newLabel.setAddr(Code.pc);
				
			}else {
				newLabel = new MyLabel(label.getLabelName(), Code.pc);
			}
			
			listOfLabels.add(newLabel);
		}
	}
	
	public void visit(SingleStatement_GotoLabel singleStatement_GotoLabel) {
		MyLabel lab = getLabel(singleStatement_GotoLabel.getLabel().getLabelName(), listOfLabels);
		if(lab == null) {
			
			lab = getLabel(singleStatement_GotoLabel.getLabel().getLabelName(), listOfFutureLabels);
			
			if(lab == null) {
				lab = new MyLabel(singleStatement_GotoLabel.getLabel().getLabelName());
				lab.listOfChangePC.add(Code.pc);
				listOfFutureLabels.add(lab);
				Code.pc += 3;
			}else {
				lab.listOfChangePC.add(Code.pc);
				Code.pc += 3;
			}
			
		}else {
			Code.putJump(lab.getAddr());
		}
	}
	
	public void visit(Program program) {
		
		MyLabel label;
		for(int i=0; i<listOfFutureLabels.size(); i++) {
			label = listOfFutureLabels.get(i);
			
			int oldPC = Code.pc;
			for(int j=0; j < label.listOfChangePC.size(); j++) {
				// System.out.println("Labela: " + label.getName() +  " \nPC: " + label.listOfChangePC.get(j) + "\n Label Addr: " + label.getAddr());
				Code.pc = label.listOfChangePC.get(j);
				Code.putJump(label.getAddr());
			}
			Code.pc = oldPC;
			
		}
		
	}


}
































