// generated with ast extension for cup
// version 0.8
// 2/1/2022 10:16:9


package rs.ac.bg.etf.pp1.ast;

public class Factor_NewExpr extends Factor {

    private FactorArray FactorArray;

    public Factor_NewExpr (FactorArray FactorArray) {
        this.FactorArray=FactorArray;
        if(FactorArray!=null) FactorArray.setParent(this);
    }

    public FactorArray getFactorArray() {
        return FactorArray;
    }

    public void setFactorArray(FactorArray FactorArray) {
        this.FactorArray=FactorArray;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FactorArray!=null) FactorArray.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FactorArray!=null) FactorArray.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FactorArray!=null) FactorArray.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Factor_NewExpr(\n");

        if(FactorArray!=null)
            buffer.append(FactorArray.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Factor_NewExpr]");
        return buffer.toString();
    }
}
