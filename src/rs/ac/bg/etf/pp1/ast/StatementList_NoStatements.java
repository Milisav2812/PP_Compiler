// generated with ast extension for cup
// version 0.8
// 2/1/2022 10:16:9


package rs.ac.bg.etf.pp1.ast;

public class StatementList_NoStatements extends StatementList {

    public StatementList_NoStatements () {
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
        buffer.append("StatementList_NoStatements(\n");

        buffer.append(tab);
        buffer.append(") [StatementList_NoStatements]");
        return buffer.toString();
    }
}
