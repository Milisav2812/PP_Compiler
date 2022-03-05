package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.List;

public class MyLabel {
	String name;
	int addr; // Adresa na kojoj se nalazi labela
	int changePC; // Adresa na kojoj treba da se azurira jmp
	ArrayList<Integer> listOfChangePC;
	
	MyLabel(String name, int addr){
		this.name = name;
		this.addr = addr;
		listOfChangePC = new ArrayList<>();
	}
	
	MyLabel(String name){
		this.name = name;
		listOfChangePC = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAddr() {
		return addr;
	}

	public void setAddr(int addr) {
		this.addr = addr;
	}

	public int getChangePC() {
		return changePC;
	}

	public void setChangePC(int changePC) {
		this.changePC = changePC;
	}
	
	
	
	
}
