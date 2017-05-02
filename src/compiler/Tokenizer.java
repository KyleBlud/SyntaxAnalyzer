package compiler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Tokenizer
{
    private Scanner sc;
    private ArrayList<Token> tokens;
    
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
        int lineNum = 1;
        char currChar;
        String token = "";
        
        while (sc.hasNextLine())
        {
            line = sc.nextLine();
            for (int i = 0; i < line.length(); i++)
            {
                currChar = line.charAt(i);
                if (isTerminalSymbol("" + currChar))
                {
                    if (!token.isEmpty())
                    {
                        token = addTokenAndClear(token, lineNum);
                    }
                    if (currChar == ':')
                    {
                        token += "" + currChar + line.charAt(++i);
                        token = addTokenAndClear(token, lineNum);
                    }
                    else
                    {
                        token += currChar;
                        token = addTokenAndClear(token, lineNum);
                    }
                }
                else if (Character.isWhitespace(currChar)) 
                {
                    if (!token.isEmpty())
                    {
                        token = addTokenAndClear(token, lineNum);
                    }
                }
                else 
                {
                    token += currChar;
                }
            }
            if (!token.isEmpty())
            {
                token = addTokenAndClear(token, lineNum);
            }
            lineNum++;
        }
        tokens.add(new Token("$", lineNum)); // End of program token
    }
    
    private boolean isTerminalSymbol(String s)
    {
        switch (s) 
        {
            case "(": case ")": case ",": case "+": 
            case "-": case ";": case ":": case "=":
            case ":=":
                return true;
            default:
                return false;
        }
    }
    
    private boolean isTerminalKeyword(String s)
    {
        switch (s)
        {
            case "begin": case "end": 
            case "read": case "write":
                return true;
            default:
                return false;
        }
    }
    
    private String addTokenAndClear(String token, int lineNum)
    {
        token = token.toLowerCase();
        if (!isTerminalKeyword(token) && !isTerminalSymbol(token))
        {
            try
            {
                int parseInt = Integer.parseInt(token);
                token = "int";
            }
            catch (NumberFormatException e)
            {
                if (isLegalIdentifier(token))
                {
                    token = "id";
                }
                else
                {
                    token = "ilid";
                }
            }
        }
        tokens.add(new Token(token, lineNum));
        return "";
    }
    
    private boolean isLegalIdentifier(String s)
    {
        if (!Character.isAlphabetic(s.charAt(0)))
        {
            return false;
        }
        for (int i = 1; i < s.length() - 1; i++)
        {
            if (!Character.isLetterOrDigit(s.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }
    
    public ArrayList<Token> getTokens()
    {
        return tokens;
    }
    
    private void printTokens()
    {
        Iterator<Token> i = tokens.iterator();
        while (i.hasNext()) 
        {
            System.out.print(i.next().token + " | ");
        }
        System.out.println();
    }
}