# SyntaxAnalyzer
Final project for CS362 - Programming Languages &amp; Translators. The application reads in a text file which consists of a program and determines whether the program is syntactically correct based on a given context free grammar.
### Grammar
The grammar that was given (on the assignment sheet) constist of these rules:

### Simplified Grammar

**‘xxx’**: quoted boldface is used for terminals

xxx: regular typeface is used for non-terminals

(): parentheses are used for grouping of constructs

x | y: indicates that either x or y can appear

x*: indicates that x appears 0 or more times.


Lexical Elements: There are four types of terminal elements

keyword: **‘BEGIN’** | **‘END’** | **‘READ’** | **‘WRITE’**

symbol: **‘(‘** | **‘)’** | **‘,’** | **‘;’** | **‘+’** | **‘-‘**

identifier: A sequence of letters, digits, and underscore (‘_’) not starting with a digit or a symbol.

integerConstant: A decimal number


Program Structure:

program: **‘BEGIN’** statements **‘END’**

varName: identifier


Statements:

statements: statement*

statement:  initStatement | readStatement | writeStatement

initStatement: varName **‘:=’** expression **‘;’**

readStatement: **‘READ’** **‘(‘** identifierList **‘)’** **‘;’**

writeStatement: **‘WRITE’** **‘(‘** expressionList **‘)’** **’:’**

identifierList: varName (**‘,’** varName)*


Expressions:

expression: term (op term)*

term: **‘(‘** expression **‘)’** | varName | integerConstant

op: **‘+’** | **‘-‘**

expressionList: expression (**‘,’** expression)*



