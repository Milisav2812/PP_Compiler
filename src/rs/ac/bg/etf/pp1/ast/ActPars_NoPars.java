// generated with ast extension for cup
// version 0.8
// 2/1/2022 10:16:9


package rs.ac.bg.etf.pp1.ast;

public class ActPars_NoPars extends ActPars {

    public ActPars_NoPars () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActPars_NoPars(\n");

        buffer.append(tab);
        buffer.append(") [ActPars_NoPars]");
        return buffer.toString();
    }
}
