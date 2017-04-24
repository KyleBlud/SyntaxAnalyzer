package compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Parser
{
    Map<String, NonTerminal> parseMap;
    ArrayList<String> tokens;
    Stack<String> stack;
    
    public Parser(File programFile) throws FileNotFoundException
    {
        parseMap = new HashMap<>();
        tokens = new ArrayList<>();
        stack = new Stack<>();

        mapProductions();
        pushStartingStateToStack();
        algorithm();
    }
    
    private void algorithm()
    {
        String topOfStack;
        String currToken;
        ArrayList<String> production;
        int i = 0;
        while (i < tokens.size())
        {
            topOfStack = stack.peek();
            currToken = tokens.get(i);
            if (isNonTerminal(topOfStack))
            {
                production = parseMap.get(topOfStack).getProduction(currToken);
                stack.pop();
                if (production != null)
                {
                    for (int j = production.size() - 1; j >= 0; j--)
                    {
                        stack.push(production.get(j));
                    }
                }
            }
            else if (topOfStack.equals(currToken))
            {
                stack.pop();
                i++;
            }
            else
            {
                // return "Error: expected topOfStack inplace of currToken"
            }
        }
        // return "Congratulations, there were no errors found."
    }
    
    private boolean isNonTerminal(String s)
    {
        return parseMap.containsKey(s);
    }
    
    private void pushStartingStateToStack()
    {
        stack.push("$");
        stack.push("<program>");
    }
    
    private void mapProductions()
    {
        // Non-terminals
        String program = "<program>";
        String stmtList = "<stmtlist>";
        String stmtListTail = "<stmtlisttail>";
        String stmt = "<stmt>";
        String identList = "<identlist>";
        String identListTail = "<identlisttail>";
        String exprList = "<exprlist>";
        String exprListTail = "<exprlisttail>";
        String expr = "<expr>";
        String exprTail = "<exprtail>";
        String factor = "<factor>";
        String op = "<op>";
        
        // <program>
        ArrayList<String> production;
        parseMap.put(program, new NonTerminal());
        production = new ArrayList<>();
        production.add("begin");
        production.add(stmtList);
        production.add("end");
        parseMap.get(program).addProduction("begin", production);
        // <stmtlist>
        parseMap.put(stmtList, new NonTerminal());
        production = new ArrayList<>();
        production.add(stmt);
        production.add(stmtListTail);
        parseMap.get(stmtList).addProduction("read", production);
        parseMap.get(stmtList).addProduction("write", production);
        parseMap.get(stmtList).addProduction("id", production);
        // <stmtlisttail>
        parseMap.put(stmtListTail, new NonTerminal());
        production = new ArrayList<>();
        production.add(stmtList);
        parseMap.get(stmtListTail).addProduction("read", production);
        parseMap.get(stmtListTail).addProduction("write", production);
        parseMap.get(stmtListTail).addProduction("id", production);
        parseMap.get(stmtListTail).addProduction("end", null);
        // <stmt>
        parseMap.put(stmt, new NonTerminal());
        production = new ArrayList<>();
        production.add("read");
        production.add("(");
        production.add(identList);
        production.add(")");
        production.add(";");
        parseMap.get(stmt).addProduction("read", production);
        production = new ArrayList<>();
        production.add("write");
        production.add("(");
        production.add(exprList);
        production.add(")");
        production.add(";");
        parseMap.get(stmt).addProduction("write", production);
        production = new ArrayList<>();
        production.add("id");
        production.add(":=");
        production.add(expr);
        production.add(";");
        parseMap.get(stmt).addProduction("id", production);
        // <identlist>
        parseMap.put(identList, new NonTerminal());
        production = new ArrayList<>();
        production.add("id");
        production.add(identListTail);
        parseMap.get(identList).addProduction("id", production);
        // <identlisttail>
        parseMap.put(identListTail, new NonTerminal());
        production = new ArrayList<>();
        production.add(",");
        production.add(identList);
        parseMap.get(identListTail).addProduction(",", production);
        parseMap.get(identListTail).addProduction(")", null);
        // <exprlist>
        parseMap.put(exprList, new NonTerminal());
        production = new ArrayList<>();
        production.add(expr);
        production.add(exprListTail);
        parseMap.get(exprList).addProduction("(", production);
        parseMap.get(exprList).addProduction("int", production);
        parseMap.get(exprList).addProduction("id", production);
        // <exprlisttail>
        parseMap.put(exprListTail, new NonTerminal());
        production = new ArrayList<>();
        production.add(",");
        production.add(identList);
        parseMap.get(exprListTail).addProduction(",", production);
        parseMap.get(exprListTail).addProduction(")", null);
        // <expr>
        parseMap.put(expr, new NonTerminal());
        production = new ArrayList<>();
        production.add(factor);
        production.add(exprTail);
        parseMap.get(expr).addProduction("(", production);
        parseMap.get(expr).addProduction("int", production);
        parseMap.get(expr).addProduction("id", production);
        // <exprtail>
        parseMap.put(exprTail, new NonTerminal());
        production = new ArrayList<>();
        production.add(op);
        production.add(expr);
        parseMap.get(exprTail).addProduction("+", production);
        parseMap.get(exprTail).addProduction("-", production);
        parseMap.get(exprTail).addProduction(";", null);
        parseMap.get(exprTail).addProduction(")", null);
        // <factor>
        parseMap.put(factor, new NonTerminal());
        production = new ArrayList<>();
        production.add("(");
        production.add(expr);
        production.add(")");
        parseMap.get(factor).addProduction("(", production);
        production = new ArrayList<>();
        production.add("int");
        parseMap.get(factor).addProduction("int", production);
        production = new ArrayList<>();
        production.add("id");
        parseMap.get(factor).addProduction("id", production);
        // <op>
        parseMap.put(op, new NonTerminal());
        production = new ArrayList<>();
        production.add("+");
        parseMap.get(op).addProduction("+", production);
        production = new ArrayList<>();
        production.add("-");
        parseMap.get(op).addProduction("-", production);
    }
}