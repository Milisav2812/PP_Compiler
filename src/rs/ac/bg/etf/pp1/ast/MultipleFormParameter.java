// generated with ast extension for cup
// version 0.8
// 2/1/2022 10:16:9


package rs.ac.bg.etf.pp1.ast;

public class MultipleFormParameter extends ParameterList {

    private ParameterList ParameterList;
    private ParameterItem ParameterItem;

    public MultipleFormParameter (ParameterList ParameterList, ParameterItem ParameterItem) {
        this.ParameterList=ParameterList;
        if(ParameterList!=null) ParameterList.setParent(this);
        this.ParameterItem=ParameterItem;
        if(ParameterItem!=null) ParameterItem.setParent(this);
    }

    public ParameterList getParameterList() {
        return ParameterList;
    }

    public void setParameterList(ParameterList ParameterList) {
        this.ParameterList=ParameterList;
    }

    public ParameterItem getParameterItem() {
        return ParameterItem;
    }

    public void setParameterItem(ParameterItem ParameterItem) {
        this.ParameterItem=ParameterItem;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ParameterList!=null) ParameterList.accept(visitor);
        if(ParameterItem!=null) ParameterItem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ParameterList!=null) ParameterList.traverseTopDown(visitor);
        if(ParameterItem!=null) ParameterItem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ParameterList!=null) ParameterList.traverseBottomUp(visitor);
        if(ParameterItem!=null) ParameterItem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleFormParameter(\n");

        if(ParameterList!=null)
            buffer.append(ParameterList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ParameterItem!=null)
            buffer.append(ParameterItem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleFormParameter]");
        return buffer.toString();
    }
}
