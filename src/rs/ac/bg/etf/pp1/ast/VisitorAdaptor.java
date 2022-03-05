// generated with ast extension for cup
// version 0.8
// 2/1/2022 10:16:9


package rs.ac.bg.etf.pp1.ast;

public abstract class VisitorAdaptor implements Visitor { 

    public void visit(DeclarationList DeclarationList) { }
    public void visit(Mulop Mulop) { }
    public void visit(VarDeclaration VarDeclaration) { }
    public void visit(ConstDeclarationItem ConstDeclarationItem) { }
    public void visit(ParameterList ParameterList) { }
    public void visit(DeclarationItem DeclarationItem) { }
    public void visit(VarDeclarationError VarDeclarationError) { }
    public void visit(StatementList StatementList) { }
    public void visit(MethodDeclarationList MethodDeclarationList) { }
    public void visit(ParameterItem ParameterItem) { }
    public void visit(Addop Addop) { }
    public void visit(Factor Factor) { }
    public void visit(Designator Designator) { }
    public void visit(Term Term) { }
    public void visit(Statements Statements) { }
    public void visit(ExprList ExprList) { }
    public void visit(VarDeclarationList VarDeclarationList) { }
    public void visit(Expr Expr) { }
    public void visit(ActPars ActPars) { }
    public void visit(DesignatorStatement DesignatorStatement) { }
    public void visit(Assignment Assignment) { }
    public void visit(MethodReturnType MethodReturnType) { }
    public void visit(FormParamList FormParamList) { }
    public void visit(VarDeclarationItem VarDeclarationItem) { }
    public void visit(Statement Statement) { }
    public void visit(LocalParamList LocalParamList) { }
    public void visit(ConstDeclarationList ConstDeclarationList) { }
    public void visit(SingleStatement SingleStatement) { }
    public void visit(FactorArray FactorArray) { }
    public void visit(Mulop_Mod Mulop_Mod) { visit(); }
    public void visit(Mulop_Div Mulop_Div) { visit(); }
    public void visit(Mulop_Mul Mulop_Mul) { visit(); }
    public void visit(Addop_Minus Addop_Minus) { visit(); }
    public void visit(Addop_Plus Addop_Plus) { visit(); }
    public void visit(FactorArray_New FactorArray_New) { visit(); }
    public void visit(FactorArray_Dimension FactorArray_Dimension) { visit(); }
    public void visit(Factor_Expr Factor_Expr) { visit(); }
    public void visit(Factor_NewExpr Factor_NewExpr) { visit(); }
    public void visit(Factor_Designator Factor_Designator) { visit(); }
    public void visit(Factor_BoolConst Factor_BoolConst) { visit(); }
    public void visit(Factor_CharConst Factor_CharConst) { visit(); }
    public void visit(Factor_Number Factor_Number) { visit(); }
    public void visit(Term_Factor Term_Factor) { visit(); }
    public void visit(Term_MulopFactor Term_MulopFactor) { visit(); }
    public void visit(Expr_Term Expr_Term) { visit(); }
    public void visit(Expr_AddopTerm Expr_AddopTerm) { visit(); }
    public void visit(Expr_MinusTerm Expr_MinusTerm) { visit(); }
    public void visit(ExprList_SingleExpr ExprList_SingleExpr) { visit(); }
    public void visit(ExprList_MultipleExpr ExprList_MultipleExpr) { visit(); }
    public void visit(ActPars_NoPars ActPars_NoPars) { visit(); }
    public void visit(ActPars_List ActPars_List) { visit(); }
    public void visit(Assignop Assignop) { visit(); }
    public void visit(Assignment_Error Assignment_Error) { visit(); }
    public void visit(Assignment_Expr Assignment_Expr) { visit(); }
    public void visit(DesignatorName DesignatorName) { visit(); }
    public void visit(Designator_Var Designator_Var) { visit(); }
    public void visit(Designator_Array Designator_Array) { visit(); }
    public void visit(DesignatorTemp DesignatorTemp) { visit(); }
    public void visit(CalledFunctionName CalledFunctionName) { visit(); }
    public void visit(DesignatorStatement_Dec DesignatorStatement_Dec) { visit(); }
    public void visit(DesignatorStatement_Inc DesignatorStatement_Inc) { visit(); }
    public void visit(DesignatorStatement_ActPars DesignatorStatement_ActPars) { visit(); }
    public void visit(DesignatorStatement_Assignop DesignatorStatement_Assignop) { visit(); }
    public void visit(StatementsDerived1 StatementsDerived1) { visit(); }
    public void visit(Label Label) { visit(); }
    public void visit(SingleStatement_GotoLabel SingleStatement_GotoLabel) { visit(); }
    public void visit(SingleStatement_PrintExprNumber SingleStatement_PrintExprNumber) { visit(); }
    public void visit(SingleStatement_PrintExpr SingleStatement_PrintExpr) { visit(); }
    public void visit(SingleStatement_ReadStatement SingleStatement_ReadStatement) { visit(); }
    public void visit(SingleStatement_DesignatorStatement SingleStatement_DesignatorStatement) { visit(); }
    public void visit(Statement_MultipleStatements Statement_MultipleStatements) { visit(); }
    public void visit(Statement_SingleStatement Statement_SingleStatement) { visit(); }
    public void visit(Statement_LabelSingleStatement Statement_LabelSingleStatement) { visit(); }
    public void visit(StatementList_NoStatements StatementList_NoStatements) { visit(); }
    public void visit(StatementList_MultipleStatements StatementList_MultipleStatements) { visit(); }
    public void visit(FormParam_Array FormParam_Array) { visit(); }
    public void visit(FormParam_Variable FormParam_Variable) { visit(); }
    public void visit(SingleFormParameter SingleFormParameter) { visit(); }
    public void visit(MultipleFormParameter MultipleFormParameter) { visit(); }
    public void visit(NoFormParams NoFormParams) { visit(); }
    public void visit(FormParams FormParams) { visit(); }
    public void visit(VoidMethReturnType VoidMethReturnType) { visit(); }
    public void visit(MethReturnType MethReturnType) { visit(); }
    public void visit(NoLocalParam NoLocalParam) { visit(); }
    public void visit(MultipleLocalParam MultipleLocalParam) { visit(); }
    public void visit(MethodDeclaration MethodDeclaration) { visit(); }
    public void visit(NoMethodList NoMethodList) { visit(); }
    public void visit(MultipleMethod MultipleMethod) { visit(); }
    public void visit(Type Type) { visit(); }
    public void visit(VarDeclaration_Array VarDeclaration_Array) { visit(); }
    public void visit(VarDeclaration_Variable VarDeclaration_Variable) { visit(); }
    public void visit(VarDeclaration_CommaError VarDeclaration_CommaError) { visit(); }
    public void visit(VarDeclaration_Item VarDeclaration_Item) { visit(); }
    public void visit(SingleVarDeclaration SingleVarDeclaration) { visit(); }
    public void visit(MultipleVarDeclaration MultipleVarDeclaration) { visit(); }
    public void visit(VarDeclaration_CommaError2 VarDeclaration_CommaError2) { visit(); }
    public void visit(VarDeclarations VarDeclarations) { visit(); }
    public void visit(ConstBool ConstBool) { visit(); }
    public void visit(ConstChar ConstChar) { visit(); }
    public void visit(ConstNumber ConstNumber) { visit(); }
    public void visit(MultipleConstDeclaration MultipleConstDeclaration) { visit(); }
    public void visit(SingleConstDeclaration SingleConstDeclaration) { visit(); }
    public void visit(ConstDeclaration ConstDeclaration) { visit(); }
    public void visit(DeclarationItem_VarDecl DeclarationItem_VarDecl) { visit(); }
    public void visit(DeclarationItem_ConstDecl DeclarationItem_ConstDecl) { visit(); }
    public void visit(NoDeclarationList NoDeclarationList) { visit(); }
    public void visit(MultipleDeclaration MultipleDeclaration) { visit(); }
    public void visit(ProgramName ProgramName) { visit(); }
    public void visit(Program Program) { visit(); }


    public void visit() { }
}
