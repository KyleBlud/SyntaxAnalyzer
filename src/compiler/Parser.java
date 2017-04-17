package compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser
{
    private ArrayList<String> tokens;
    private String currToken;
    private int currIndex;
    
    public Parser(File programFile) throws FileNotFoundException
    {
        Tokenizer t = new Tokenizer(new Scanner(programFile));
        tokens = t.getTokens();
        currIndex = 0;
        currToken = tokens.get(currIndex);
        parseProgram();
    }
    
    private void parseProgram()
    {
        
    }
    
    private void parseStatements()
    {
        
    }
    
    private void parseInitStatement()
    {
        
    }
    
    private void parseReadStatement()
    {
        
    }
    
    private void parseWriteStatement()
    {
        
    }
    
    private void next()
    {
        currIndex++;
        currToken = tokens.get(currIndex);
    }
}
