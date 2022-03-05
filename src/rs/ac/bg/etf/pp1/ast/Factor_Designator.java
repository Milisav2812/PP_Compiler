// generated with ast extension for cup
// version 0.8
// 2/1/2022 10:16:9


package rs.ac.bg.etf.pp1.ast;

public class Factor_Designator extends Factor {

    private DesignatorTemp DesignatorTemp;

    public Factor_Designator (DesignatorTemp DesignatorTemp) {
        this.DesignatorTemp=DesignatorTemp;
        if(DesignatorTemp!=null) DesignatorTemp.setParent(this);
    }

    public DesignatorTemp getDesignatorTemp() {
        return DesignatorTemp;
    }

    public void setDesignatorTemp(DesignatorTemp DesignatorTemp) {
        this.DesignatorTemp=DesignatorTemp;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorTemp!=null) DesignatorTemp.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorTemp!=null) DesignatorTemp.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorTemp!=null) DesignatorTemp.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Factor_Designator(\n");

        if(DesignatorTemp!=null)
            buffer.append(DesignatorTemp.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Factor_Designator]");
        return buffer.toString();
    }
}
