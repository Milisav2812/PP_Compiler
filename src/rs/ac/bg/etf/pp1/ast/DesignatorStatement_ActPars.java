// generated with ast extension for cup
// version 0.8
// 2/1/2022 10:16:9


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatement_ActPars extends DesignatorStatement {

    private CalledFunctionName CalledFunctionName;
    private ActPars ActPars;

    public DesignatorStatement_ActPars (CalledFunctionName CalledFunctionName, ActPars ActPars) {
        this.CalledFunctionName=CalledFunctionName;
        if(CalledFunctionName!=null) CalledFunctionName.setParent(this);
        this.ActPars=ActPars;
        if(ActPars!=null) ActPars.setParent(this);
    }

    public CalledFunctionName getCalledFunctionName() {
        return CalledFunctionName;
    }

    public void setCalledFunctionName(CalledFunctionName CalledFunctionName) {
        this.CalledFunctionName=CalledFunctionName;
    }

    public ActPars getActPars() {
        return ActPars;
    }

    public void setActPars(ActPars ActPars) {
        this.ActPars=ActPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CalledFunctionName!=null) CalledFunctionName.accept(visitor);
        if(ActPars!=null) ActPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CalledFunctionName!=null) CalledFunctionName.traverseTopDown(visitor);
        if(ActPars!=null) ActPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CalledFunctionName!=null) CalledFunctionName.traverseBottomUp(visitor);
        if(ActPars!=null) ActPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatement_ActPars(\n");

        if(CalledFunctionName!=null)
            buffer.append(CalledFunctionName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActPars!=null)
            buffer.append(ActPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatement_ActPars]");
        return buffer.toString();
    }
}
