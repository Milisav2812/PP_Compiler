// generated with ast extension for cup
// version 0.8
// 2/1/2022 10:16:9


package rs.ac.bg.etf.pp1.ast;

public class SingleConstDeclaration extends ConstDeclarationList {

    private ConstDeclarationItem ConstDeclarationItem;

    public SingleConstDeclaration (ConstDeclarationItem ConstDeclarationItem) {
        this.ConstDeclarationItem=ConstDeclarationItem;
        if(ConstDeclarationItem!=null) ConstDeclarationItem.setParent(this);
    }

    public ConstDeclarationItem getConstDeclarationItem() {
        return ConstDeclarationItem;
    }

    public void setConstDeclarationItem(ConstDeclarationItem ConstDeclarationItem) {
        this.ConstDeclarationItem=ConstDeclarationItem;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstDeclarationItem!=null) ConstDeclarationItem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstDeclarationItem!=null) ConstDeclarationItem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstDeclarationItem!=null) ConstDeclarationItem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleConstDeclaration(\n");

        if(ConstDeclarationItem!=null)
            buffer.append(ConstDeclarationItem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleConstDeclaration]");
        return buffer.toString();
    }
}
