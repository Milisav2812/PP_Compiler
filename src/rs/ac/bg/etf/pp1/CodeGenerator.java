package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.bg.etf.pp1.CounterVisitor.*;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.*;

public class CodeGenerator extends VisitorAdaptor {

	private Obj scope;
	private int mainPC;
	private int constPositionCounter = 0;
	private int globalVarPositionCounter = 0;
	private int localVarPositionCounter = 0;
	private boolean newArray = false;
	private int arrayDimensionCounter = 0;
	
	// Labels
	public ArrayList<MyLabel> listOfLabels = new ArrayList<>();
	public ArrayList<MyLabel> listOfFutureLabels = new ArrayList<>();
	
	CodeGenerator(Obj programObj){
		super();
		this.scope = programObj;
	}
	
	/* HELPER FUNCTIONS */
	
	public MyLabel getLabel(String name, ArrayList<MyLabel> list) {
		for(int i=0; i<list.size(); i++) {
			// System.out.println(i + ". Labela: " + list.get(i));
			if(name.equalsIgnoreCase(list.get(i).getName())) {
				return list.get(i);
			}
		}
		return null;
	}
	
	public int getMainPC() {
		return mainPC;
	}
	
	public void PrintAllVariables() {
		Collection<Obj> globalScope = scope.getLocalSymbols();
		Obj temp = null;
		
		for(int i=0; i < globalScope.toArray().length; i++) {
			
			temp = (Obj) globalScope.toArray()[i];
			
			if(temp.getKind() == Obj.Meth) {
				Collection<Obj> localScope = temp.getLocalSymbols();
				
				for(int j=0; j < localScope.toArray().length; j++) {
					temp = (Obj)localScope.toArray()[j];
					
					System.out.println(temp.getName() + " Lvl: " + temp.getLevel() + " Adr: " + temp.getAdr() + " Position: " + temp.getFpPos());
				}
			}else {
				System.out.println(temp.getName() + " Lvl: " + temp.getLevel() + " Adr: " + temp.getAdr() + " Position: " + temp.getFpPos());
			}
		}
	}
	
	public Obj getVariableConstantFromScope(String name) {
		Collection<Obj> globalScope = scope.getLocalSymbols();
		Obj temp = null;
		
		for(int i=0; i < globalScope.toArray().length; i++) {
			
			temp = (Obj) globalScope.toArray()[i];
			
			if(temp.getKind() == Obj.Meth) {
				Collection<Obj> localScope = temp.getLocalSymbols();
				
				for(int j=0; j < localScope.toArray().length; j++) {
					temp = (Obj)localScope.toArray()[j];
					
					if(temp.getName().equalsIgnoreCase(name)) {
						return temp;
					}
				}
			}else {
				if(temp.getName().equalsIgnoreCase(name)) {
					return temp;
				}
			}
		}
		
		return temp;
	}
	
	public boolean isArray(Obj o) {
		if(o.getType().getKind() == Struct.Array) return true;
		else return false;
	}
	
	/* POCETAK I KRAJ Main METODE */
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
	
	public void visit(MethodDeclaration methodDeclaration) {
		// PrintAllVariables();
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	/* CONST DECLARATIONS */
	public void visit(ConstNumber constNumber) {
		Obj constNum = getVariableConstantFromScope(constNumber.getConstName());
		constNum.setLevel(0);
		constNum.setAdr(constNumber.getValue());
		constNum.setFpPos(constPositionCounter);
		constPositionCounter++;
	}
	
	public void visit(ConstChar constChar) {
		Obj constCh = getVariableConstantFromScope(constChar.getConstName());
		constCh.setLevel(0);
		constCh.setAdr(constChar.getValue());
		constCh.setFpPos(constPositionCounter);
		constPositionCounter++;
	}
	
	public void visit(ConstBool constBool) {
		Obj constBl = getVariableConstantFromScope(constBool.getConstName());
		constBl.setLevel(0);
		constBl.setAdr(constBool.getValue());
		constBl.setFpPos(constPositionCounter);
		constPositionCounter++;
	}

	/* VARIABLE DECLARATIONS */
	public void visit(VarDeclaration_Variable varDeclaration_Variable) {
		Obj varObj = getVariableConstantFromScope(varDeclaration_Variable.getVarName());

		if(varObj.getLevel() == 0) {
			// Global
			varObj.setAdr(globalVarPositionCounter);
			globalVarPositionCounter++;
		}else {
			// Local
			varObj.setAdr(localVarPositionCounter);
			localVarPositionCounter++;
		}
	}

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
	
	/* TERM */
	
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
	
	public void visit(Factor_NewExpr factor_NewExpr) {
		Code.put(Code.newarray);
		if(factor_NewExpr.struct == Tab.charType) {
			Code.put(0);
		}else {
			Code.put(1);
		}
	}
	
	public void visit(FactorArray_New factorArray_New) {
		newArray = true;
	}
	
	
	/* DESIGNATOR */
	
	public void visit(DesignatorStatement_Assignop designatorStatement_Assignop) {
	
		Obj des = getVariableConstantFromScope(designatorStatement_Assignop.getDesignatorTemp().obj.getName());
		
		if(newArray) {
			// new expression
			
			if(isArray(des)) {
				Code.store(des);
			}
			
			newArray = false;
			
		}else {
			
			if(isArray(des)) {
				if(des.getType().getElemType() == Tab.charType) {
					Code.put(Code.bastore);
				}else {
					Code.put(Code.astore);
				}
			}else {
				Code.store(des);
			}
		}

	}
	
	public void visit(DesignatorTemp designatorTemp) {
		Obj des = getVariableConstantFromScope(designatorTemp.obj.getName());
		
		SyntaxNode parent = designatorTemp.getParent();
		
		if(parent.getClass() == DesignatorStatement_Assignop.class) {
			// Do nothing
			
		}else {
			
			if(isArray(des)) {
				if(des.getType().getElemType() == Tab.charType) {
					Code.put(Code.baload);
				}else {
					Code.put(Code.aload);
				}
			}else {
				Code.load(des);
			}
				
			
		}
	}
	
	public void visit(Designator_Var designator_Var) {
		
		Obj des = getVariableConstantFromScope(designator_Var.getDesignatorName().getName());
		
		SyntaxNode parent = designator_Var.getParent();
		
		if(parent.getClass() == Designator_Array.class) {
			Code.load(des);
		}
	}

	/* PRINT & READ */
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
		
		Obj des = getVariableConstantFromScope(singleStatement_ReadStatement.getDesignatorTemp().obj.getName());
		
		// TODO: PROVERITI!
		if(des.getType() == Tab.charType) {
			Code.put(Code.bread);
		}else {
			Code.put(Code.read);
		}
		Code.store(des);
	}
	
	/* DESIGNATOR INC & DEC */
	public void visit(DesignatorStatement_Inc designatorStatement_Inc) {
		Obj des = getVariableConstantFromScope(designatorStatement_Inc.getDesignatorTemp().obj.getName());
		
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(des);
	}	

	public void visit(DesignatorStatement_Dec designatorStatement_Dec) {
		Obj des = getVariableConstantFromScope(designatorStatement_Dec.getDesignatorTemp().obj.getName());
		
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(des);
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










































