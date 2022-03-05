package rs.ac.bg.etf.pp1;
import java_cup.runtime.Symbol;

%%

%{
	private Symbol new_symbol(int type) {
			return new Symbol(type, yyline+1, yycolumn);
	}
	
	private Symbol new_symbol(int type, Object value) {
			return new Symbol(type, yyline+1, yycolumn, value);
	}
%}

%cup
%line
%column

%xstate COMMENT

%eofval{ 
	return new_symbol(sym.EOF);
%eofval}

%%

" " {}
"\b" {}
"\t" {}
"\n" {}
"\r\n" {}
"\f" {}

"program"   	{return new_symbol(sym.PROGRAM);}
"const"   		{return new_symbol(sym.CONST);}
"new"   		{return new_symbol(sym.NEW);}
"print" 		{return new_symbol(sym.PRINT);}
"read" 			{return new_symbol(sym.READ);}
"return" 		{return new_symbol(sym.RETURN);}
"void" 			{return new_symbol(sym.VOID);}
"goto" 			{return new_symbol(sym.GOTO);}

"+" {return new_symbol(sym.PLUS);}
"-" {return new_symbol(sym.MINUS);}
"*" {return new_symbol(sym.MUL);} 
"/" {return new_symbol(sym.DIV);}
"%" {return new_symbol(sym.MOD);}

"=" {return new_symbol(sym.EQUAL);}
"++" {return new_symbol(sym.INC);}
"--" {return new_symbol(sym.DEC);}

":" {return new_symbol(sym.COLON);}
";" {return new_symbol(sym.SEMI);}
"," {return new_symbol(sym.COMMA);}
"." {return new_symbol(sym.DOT);}

"(" {return new_symbol(sym.LPAREN);}
")" {return new_symbol(sym.RPAREN);}
"{" {return new_symbol(sym.LBRACE);}
"}" {return new_symbol(sym.RBRACE);}
"[" {return new_symbol(sym.LSQUARE);}
"]" {return new_symbol(sym.RSQUARE);}

"//" 				{yybegin(COMMENT);}
<COMMENT>"\n" 		{yybegin(YYINITIAL);}
<COMMENT>. 			{yybegin(COMMENT);}
<COMMENT>"\r\n" 	{yybegin(YYINITIAL);}

"true"								{return new_symbol(sym.BOOLEAN, new Integer(1));}
"false"								{return new_symbol(sym.BOOLEAN, new Integer(0));}
[0-9]+ 								{return new_symbol(sym.NUMBER, new Integer (yytext()));}
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 		{return new_symbol(sym.IDENT, yytext());}
"'"[\040-\176]"'" 					{return new_symbol(sym.CHARCONST, new Character (yytext().charAt(1)));}

. 									{System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1));}
