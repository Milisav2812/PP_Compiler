// generated with ast extension for cup
// version 0.8
// 2/1/2022 10:16:9


package rs.ac.bg.etf.pp1.ast;

public class MultipleLocalParam extends LocalParamList {

    private LocalParamList LocalParamList;
    private VarDeclaration VarDeclaration;

    public MultipleLocalParam (LocalParamList LocalParamList, VarDeclaration VarDeclaration) {
        this.LocalParamList=LocalParamList;
        if(LocalParamList!=null) LocalParamList.setParent(this);
        this.VarDeclaration=VarDeclaration;
        if(VarDeclaration!=null) VarDeclaration.setParent(this);
    }

    public LocalParamList getLocalParamList() {
        return LocalParamList;
    }

    public void setLocalParamList(LocalParamList LocalParamList) {
        this.LocalParamList=LocalParamList;
    }

    public VarDeclaration getVarDeclaration() {
        return VarDeclaration;
    }

    public void setVarDeclaration(VarDeclaration VarDeclaration) {
        this.VarDeclaration=VarDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LocalParamList!=null) LocalParamList.accept(visitor);
        if(VarDeclaration!=null) VarDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LocalParamList!=null) LocalParamList.traverseTopDown(visitor);
        if(VarDeclaration!=null) VarDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LocalParamList!=null) LocalParamList.traverseBottomUp(visitor);
        if(VarDeclaration!=null) VarDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleLocalParam(\n");

        if(LocalParamList!=null)
            buffer.append(LocalParamList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclaration!=null)
            buffer.append(VarDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleLocalParam]");
        return buffer.toString();
    }
}
