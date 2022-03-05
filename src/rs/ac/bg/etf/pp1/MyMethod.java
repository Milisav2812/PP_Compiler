package rs.ac.bg.etf.pp1;

import java.util.ArrayList;

import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class MyMethod {
	public Obj obj;
	public ArrayList<Struct> formalParams;
	
	MyMethod(Obj obj){
		this.obj = obj;
		formalParams = new ArrayList<>();
	}
	
}
