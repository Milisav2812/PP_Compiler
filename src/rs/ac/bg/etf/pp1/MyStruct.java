package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;

public class MyStruct extends Struct{
	
	public static final int Label = 6;
	public static final int Matrix = 6;
	
	public static Struct AddNewArray(Struct elemType) {
		return new Struct(Struct.Array, elemType);
	}
	
	public MyStruct(int kind) {
		super(kind);
	}

	public MyStruct(int kind, SymbolDataStructure members) {
		super(kind, members);
	}
}
