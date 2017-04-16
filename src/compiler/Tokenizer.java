package compiler;

import java.util.ArrayList;
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
    }
    
    private void tokenizeFile()
    {
        String line;
        while (sc.hasNextLine())
        {
            line = sc.nextLine().replaceAll("\\s+", "");
            System.out.print(line);
        }
    }
    
    public ArrayList<String> getTokens()
    {
        return tokens;
    }
}
