package desilang;

public class FindChar
{
    public static int noOfChar(String str,char key)
    {
        int n=0;
        for(int i=0;i<str.length();i++)
        {
            if(str.charAt(i)==key)
            {
                n++;
            }
        }
        return n;
    }
    public static boolean afterBracketIsCharIsPresent(String str)
    {
        int k=0;
        
        for(int i=str.indexOf(')')+1;i<str.length();i++)
        {
            if(str.charAt(i) != ' ')
            {
                k++;
            }
        }
        if(k==0)
            return true;
        else
            return false;
    }
    public static boolean isNumberContains(String str)
    {
        int f=0;
        if(str.contains("0"))f=1;
        else if(str.contains("1"))f=1;
        else if(str.contains("2"))f=1;
        else if(str.contains("3"))f=1;
        else if(str.contains("4"))f=1;
        else if(str.contains("5"))f=1;
        else if(str.contains("6"))f=1;
        else if(str.contains("7"))f=1;
        else if(str.contains("8"))f=1;
        else if(str.contains("9"))f=1;
        
        if(f==0)return false;
        else return true;
    }
}
