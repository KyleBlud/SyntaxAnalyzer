package compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NonTerminal
{
    Map<String, ArrayList<String>> productions;
    
    public NonTerminal()
    {
        productions = new HashMap<>();
    }
    
    public void addProduction(String terminal, ArrayList<String> production)
    {
        productions.put(terminal, production);
    }
    
    public ArrayList<String> getProduction(String terminal)
    {
        return productions.get(terminal);
    }
    
    public boolean hasProduction(String terminal)
    {
        return productions.containsKey(terminal);
    }
}
