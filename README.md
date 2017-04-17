# SyntaxAnalyzer
Final project for CS362 - Programming Languages &amp; Translators. The application reads in a text file which consists of a program and determines whether the program is syntactically correct based on a given context free grammar.
### Grammar
The grammar that was given (on the assignment sheet) constist of these rules:

### Simplified Grammar

**‘xxx’**: quoted boldface is used for terminals<br>
xxx: regular typeface is used for non-terminals<br>
(): parentheses are used for grouping of constructs<br>
x | y: indicates that either x or y can appear<br>
x*: indicates that x appears 0 or more times.<br>

#### Lexical Elements: <br>
There are four types of terminal elements<br>
keyword: **‘BEGIN’** | **‘END’** | **‘READ’** | **‘WRITE’**<br>
symbol: **‘(‘** | **‘)’** | **‘,’** | **‘;’** | **‘+’** | **‘-‘**<br>
identifier: A sequence of letters, digits, and underscore (‘_’) not starting with a digit or a symbol.<br>
integerConstant: A decimal number<br>

#### Program Structure:<br>
program: **‘BEGIN’** statements **‘END’**<br>
varName: identifier<br>

#### Statements:<br>
statements: statement*<br>
statement:  initStatement | readStatement | writeStatement<br>
initStatement: varName **‘:=’** expression **‘;’**<br>
readStatement: **‘READ’** **‘(‘** identifierList **‘)’** **‘;’**<br>
writeStatement: **‘WRITE’** **‘(‘** expressionList **‘)’** **’:’**<br>
identifierList: varName (**‘,’** varName)*<br>

#### Expressions:<br>
expression: term (op term)*<br>
term: **‘(‘** expression **‘)’** | varName | integerConstant<br>
op: **‘+’** | **‘-‘**<br>
expressionList: expression (**‘,’** expression)*<br>




