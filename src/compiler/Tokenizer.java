package compiler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Tokenizer
{
    private Scanner sc;
    ArrayList<String> tokens;
    
    public Tokenizer(Scanner sc) 
    {
        this.sc = sc;
        tokens = new ArrayList<>();
        tokenizeFile();
        printTokens();
    }
    
    private void tokenizeFile()
    {
        String line;
        char currChar;
        String token = "";
        
        while (sc.hasNextLine())
        {
            line = sc.nextLine();
            for (int i = 0; i < line.length(); i++)
            {
                currChar = line.charAt(i);
                if (isTerminalSymbol(currChar))
                {
                    if (!token.isEmpty())
                    {
                        token = addTokenAndClear(token);
                    }
                    if (currChar == ':')
                    {
                        token += "" + currChar + line.charAt(++i);
                        token = addTokenAndClear(token);
                    }
                    else
                    {
                        token += currChar;
                        token = addTokenAndClear(token);
                    }
                }
                else if (Character.isWhitespace(currChar)) 
                {
                    if (!token.isEmpty())
                    {
                        token = addTokenAndClear(token);
                    }
                }
                else 
                {
                    token += currChar;
                }
            }
            if (!token.isEmpty())
            {
                token = addTokenAndClear(token);
            }
        }
    }
    
    public ArrayList<String> getTokens()
    {
        return tokens;
    }
    
    private boolean isTerminalSymbol(char c)
    {
        switch (c) 
        {
            case '(': case ')': case ',': 
            case '+': case '-': case ';':
            case ':': case '=':
                return true;
            default:
                return false;
        }
    }
    
    private String addTokenAndClear(String token)
    {
        tokens.add(token);
        return "";
    }
    
    private void printTokens()
    {
        Iterator<String> i = tokens.iterator();
        while (i.hasNext()) 
        {
            System.out.println(i.next());
        }
    }
}
