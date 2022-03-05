// generated with ast extension for cup
// version 0.8
// 2/1/2022 10:16:9


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatement_Assignop extends DesignatorStatement {

    private DesignatorTemp DesignatorTemp;
    private Assignop Assignop;
    private Assignment Assignment;

    public DesignatorStatement_Assignop (DesignatorTemp DesignatorTemp, Assignop Assignop, Assignment Assignment) {
        this.DesignatorTemp=DesignatorTemp;
        if(DesignatorTemp!=null) DesignatorTemp.setParent(this);
        this.Assignop=Assignop;
        if(Assignop!=null) Assignop.setParent(this);
        this.Assignment=Assignment;
        if(Assignment!=null) Assignment.setParent(this);
    }

    public DesignatorTemp getDesignatorTemp() {
        return DesignatorTemp;
    }

    public void setDesignatorTemp(DesignatorTemp DesignatorTemp) {
        this.DesignatorTemp=DesignatorTemp;
    }

    public Assignop getAssignop() {
        return Assignop;
    }

    public void setAssignop(Assignop Assignop) {
        this.Assignop=Assignop;
    }

    public Assignment getAssignment() {
        return Assignment;
    }

    public void setAssignment(Assignment Assignment) {
        this.Assignment=Assignment;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorTemp!=null) DesignatorTemp.accept(visitor);
        if(Assignop!=null) Assignop.accept(visitor);
        if(Assignment!=null) Assignment.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorTemp!=null) DesignatorTemp.traverseTopDown(visitor);
        if(Assignop!=null) Assignop.traverseTopDown(visitor);
        if(Assignment!=null) Assignment.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorTemp!=null) DesignatorTemp.traverseBottomUp(visitor);
        if(Assignop!=null) Assignop.traverseBottomUp(visitor);
        if(Assignment!=null) Assignment.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatement_Assignop(\n");

        if(DesignatorTemp!=null)
            buffer.append(DesignatorTemp.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Assignop!=null)
            buffer.append(Assignop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Assignment!=null)
            buffer.append(Assignment.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatement_Assignop]");
        return buffer.toString();
    }
}
