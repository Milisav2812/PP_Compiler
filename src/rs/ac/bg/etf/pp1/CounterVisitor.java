package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;

public class CounterVisitor extends VisitorAdaptor {
	
	protected boolean localVar = false;
	
	protected int count;
	protected int varcount = 0;
	
	public int getCount(){
		return count;
	}
	
	public static class FormParamCounter extends CounterVisitor{
	
		// Formalni Parametri funkcije
		public void visit(FormParam_Variable formParam_Variable){
			count++;
		}
		
		public void visit(FormParam_Array formParam_Array) {
			count++;
		}
	}
	
	public static class VarCounter extends CounterVisitor{
		
		
		public void visit(VarDeclaration_Item varDeclaration_Item) {
			varcount++;
		}
		
		public void visit(VarDeclarations varDeclarations ) {
			SyntaxNode parent = varDeclarations.getParent();
			
			if(parent.getClass() == MultipleLocalParam.class) {
				count += varcount;
			}
			
			varcount = 0;
		}
	}
}






















