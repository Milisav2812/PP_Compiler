package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class MyTab extends Tab{
	public static final Struct boolType = new Struct(Struct.Bool);
	
	public static void myInit() {
		Tab.init();
		Tab.insert(Obj.Type, "bool", boolType);
	}
	
	public static Obj findInCurrentScope(String name) {
		Obj resultObj = null;
		Scope s = currentScope;
		if (s.getLocals() != null) {
			resultObj = s.getLocals().searchKey(name);
		}

		return (resultObj != null) ? resultObj : noObj;
	}
}
