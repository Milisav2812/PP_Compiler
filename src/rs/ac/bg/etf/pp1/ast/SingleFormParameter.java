// generated with ast extension for cup
// version 0.8
// 2/1/2022 10:16:9


package rs.ac.bg.etf.pp1.ast;

public class SingleFormParameter extends ParameterList {

    private ParameterItem ParameterItem;

    public SingleFormParameter (ParameterItem ParameterItem) {
        this.ParameterItem=ParameterItem;
        if(ParameterItem!=null) ParameterItem.setParent(this);
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
        if(ParameterItem!=null) ParameterItem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ParameterItem!=null) ParameterItem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ParameterItem!=null) ParameterItem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleFormParameter(\n");

        if(ParameterItem!=null)
            buffer.append(ParameterItem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleFormParameter]");
        return buffer.toString();
    }
}
