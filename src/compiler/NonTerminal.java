package compiler;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class NonTerminal
{
    Map<String, Stack<String>> productions;
    
    public NonTerminal()
    {
        productions = new HashMap<>();
    }
    
    public void addProduction(String terminal, Stack<String> production)
    {
        productions.put(terminal, production);
    }
    
    public Stack<String> getProduction(String terminal)
    {
        return productions.get(terminal);
    }
}
