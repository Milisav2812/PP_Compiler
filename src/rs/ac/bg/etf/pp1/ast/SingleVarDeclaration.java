// generated with ast extension for cup
// version 0.8
// 2/1/2022 10:16:9


package rs.ac.bg.etf.pp1.ast;

public class SingleVarDeclaration extends VarDeclarationList {

    private VarDeclarationError VarDeclarationError;

    public SingleVarDeclaration (VarDeclarationError VarDeclarationError) {
        this.VarDeclarationError=VarDeclarationError;
        if(VarDeclarationError!=null) VarDeclarationError.setParent(this);
    }

    public VarDeclarationError getVarDeclarationError() {
        return VarDeclarationError;
    }

    public void setVarDeclarationError(VarDeclarationError VarDeclarationError) {
        this.VarDeclarationError=VarDeclarationError;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclarationError!=null) VarDeclarationError.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclarationError!=null) VarDeclarationError.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclarationError!=null) VarDeclarationError.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleVarDeclaration(\n");

        if(VarDeclarationError!=null)
            buffer.append(VarDeclarationError.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleVarDeclaration]");
        return buffer.toString();
    }
}
