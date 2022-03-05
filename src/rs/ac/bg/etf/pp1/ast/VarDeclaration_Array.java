// generated with ast extension for cup
// version 0.8
// 2/1/2022 10:16:9


package rs.ac.bg.etf.pp1.ast;

public class VarDeclaration_Array extends VarDeclarationItem {

    private VarDeclarationItem VarDeclarationItem;

    public VarDeclaration_Array (VarDeclarationItem VarDeclarationItem) {
        this.VarDeclarationItem=VarDeclarationItem;
        if(VarDeclarationItem!=null) VarDeclarationItem.setParent(this);
    }

    public VarDeclarationItem getVarDeclarationItem() {
        return VarDeclarationItem;
    }

    public void setVarDeclarationItem(VarDeclarationItem VarDeclarationItem) {
        this.VarDeclarationItem=VarDeclarationItem;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclarationItem!=null) VarDeclarationItem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclarationItem!=null) VarDeclarationItem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclarationItem!=null) VarDeclarationItem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclaration_Array(\n");

        if(VarDeclarationItem!=null)
            buffer.append(VarDeclarationItem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclaration_Array]");
        return buffer.toString();
    }
}
