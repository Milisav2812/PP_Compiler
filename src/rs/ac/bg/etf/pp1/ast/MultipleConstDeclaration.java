// generated with ast extension for cup
// version 0.8
// 2/1/2022 10:16:9


package rs.ac.bg.etf.pp1.ast;

public class MultipleConstDeclaration extends ConstDeclarationList {

    private ConstDeclarationList ConstDeclarationList;
    private ConstDeclarationItem ConstDeclarationItem;

    public MultipleConstDeclaration (ConstDeclarationList ConstDeclarationList, ConstDeclarationItem ConstDeclarationItem) {
        this.ConstDeclarationList=ConstDeclarationList;
        if(ConstDeclarationList!=null) ConstDeclarationList.setParent(this);
        this.ConstDeclarationItem=ConstDeclarationItem;
        if(ConstDeclarationItem!=null) ConstDeclarationItem.setParent(this);
    }

    public ConstDeclarationList getConstDeclarationList() {
        return ConstDeclarationList;
    }

    public void setConstDeclarationList(ConstDeclarationList ConstDeclarationList) {
        this.ConstDeclarationList=ConstDeclarationList;
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
        if(ConstDeclarationList!=null) ConstDeclarationList.accept(visitor);
        if(ConstDeclarationItem!=null) ConstDeclarationItem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstDeclarationList!=null) ConstDeclarationList.traverseTopDown(visitor);
        if(ConstDeclarationItem!=null) ConstDeclarationItem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstDeclarationList!=null) ConstDeclarationList.traverseBottomUp(visitor);
        if(ConstDeclarationItem!=null) ConstDeclarationItem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleConstDeclaration(\n");

        if(ConstDeclarationList!=null)
            buffer.append(ConstDeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDeclarationItem!=null)
            buffer.append(ConstDeclarationItem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleConstDeclaration]");
        return buffer.toString();
    }
}
