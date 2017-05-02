package compiler;

public class Token
{
    public String token;
    public int lineNum;
    public Token(String token, int lineNum)
    {
        this.token = token;
        this.lineNum = lineNum;
    }
}
