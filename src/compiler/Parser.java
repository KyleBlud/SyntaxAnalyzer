package compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
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
        tokens.add("begin");
        tokens.add("id");
        tokens.add(":=");
        tokens.add("(");
        tokens.add("id");
        tokens.add("+");
        tokens.add("1");
        tokens.add(")");
        tokens.add(";");
        tokens.add("end");
        tokens.add("$");
        mapProductions();
        pushStartingStateToStack();
        algorithm();
    }
    
    private void algorithm()
    {
        String topOfStack;
        String currToken;
        Stack<String> production;
        int i = 0;
        while (i < tokens.size())
        {
            topOfStack = stack.peek();
            currToken = tokens.get(i);
            System.out.println("Stack: " + stack.toString());
            System.out.println("Top: " + topOfStack + ", Current Token: " + currToken);
            if (parseMap.containsKey(topOfStack))
            {
                production = parseMap.get(topOfStack).getProduction(currToken);
                System.out.println("parseMap.get(\"" + topOfStack + "\").getProduction(\"" + currToken + "\");");
                stack.pop();
                if (production != null)
                {
                    System.out.println("Production: " + production.toString());
                    while (!production.empty())
                    {
                        stack.push(production.pop());
                    }
                }
            }
            else if (topOfStack == currToken)
            {
                stack.pop();
                i++;
            }
        }
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
        Stack<String> production;
        parseMap.put(program, new NonTerminal());
        production = new Stack<>();
        production.push("begin");
        production.push(stmtList);
        production.push("end");
        parseMap.get(program).addProduction("begin", production);
        // <stmtlist>
        parseMap.put(stmtList, new NonTerminal());
        production = new Stack<>();
        production.push(stmt);
        production.push(stmtListTail);
        parseMap.get(stmtList).addProduction("read", production);
        parseMap.get(stmtList).addProduction("write", production);
        parseMap.get(stmtList).addProduction("id", production);
        // <stmtlisttail>
        parseMap.put(stmtListTail, new NonTerminal());
        production = new Stack<>();
        production.push(stmtList);
        parseMap.get(stmtListTail).addProduction("read", production);
        parseMap.get(stmtListTail).addProduction("write", production);
        parseMap.get(stmtListTail).addProduction("id", production);
        parseMap.get(stmtListTail).addProduction("end", null);
        // <stmt>
        parseMap.put(stmt, new NonTerminal());
        production = new Stack<>();
        production.push("read");
        production.push("(");
        production.push(identList);
        production.push(")");
        production.push(";");
        parseMap.get(stmt).addProduction("read", production);
        production = new Stack<>();
        production.push("write");
        production.push("(");
        production.push(exprList);
        production.push(")");
        production.push(";");
        parseMap.get(stmt).addProduction("write", production);
        production = new Stack<>();
        production.push("id");
        production.push(":=");
        production.push(expr);
        production.push(";");
        parseMap.get(stmt).addProduction("id", production);
        // <identlist>
        parseMap.put(identList, new NonTerminal());
        production = new Stack<>();
        production.push("id");
        production.push(identListTail);
        parseMap.get(identList).addProduction("id", production);
        // <identlisttail>
        parseMap.put(identListTail, new NonTerminal());
        production = new Stack<>();
        production.push(",");
        production.push(identList);
        parseMap.get(identListTail).addProduction(",", production);
        parseMap.get(identListTail).addProduction(")", null);
        // <exprlist>
        parseMap.put(exprList, new NonTerminal());
        production = new Stack<>();
        production.push(expr);
        production.push(exprListTail);
        parseMap.get(exprList).addProduction("(", production);
        parseMap.get(exprList).addProduction("int", production);
        parseMap.get(exprList).addProduction("id", production);
        // <exprlisttail>
        parseMap.put(exprListTail, new NonTerminal());
        production = new Stack<>();
        production.push(",");
        production.push(identList);
        parseMap.get(exprListTail).addProduction(",", production);
        parseMap.get(exprListTail).addProduction(")", null);
        // <expr>
        parseMap.put(expr, new NonTerminal());
        production = new Stack<>();
        production.push(factor);
        production.push(exprTail);
        parseMap.get(expr).addProduction("(", production);
        parseMap.get(expr).addProduction("int", production);
        parseMap.get(expr).addProduction("id", production);
        // <exprtail>
        parseMap.put(exprTail, new NonTerminal());
        production = new Stack<>();
        production.push(op);
        production.push(expr);
        parseMap.get(exprTail).addProduction("+", production);
        parseMap.get(exprTail).addProduction("-", production);
        parseMap.get(exprTail).addProduction(";", null);
        parseMap.get(exprTail).addProduction(")", null);
        // <factor>
        parseMap.put(factor, new NonTerminal());
        production = new Stack<>();
        production.push("(");
        production.push(expr);
        production.push(")");
        parseMap.get(factor).addProduction("(", production);
        production = new Stack<>();
        production.push("int");
        parseMap.get(factor).addProduction("int", production);
        production = new Stack<>();
        production.push("id");
        parseMap.get(factor).addProduction("id", production);
        // <op>
        parseMap.put(op, new NonTerminal());
        production = new Stack<>();
        production.push("+");
        parseMap.get(op).addProduction("+", production);
        production = new Stack<>();
        production.push("-");
        parseMap.get(op).addProduction("-", production);
    }
}
