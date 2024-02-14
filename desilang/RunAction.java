package desilang;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class RunAction implements ActionListener,Keywords
{
    Container c;
    String filePath;
    String fileName;
    String textarea;
    JTextArea t2;
    final Object nalla=null;
    final boolean sahi=true,galat=false;
    

    public RunAction(Container c,JTextArea textarea, String filePath, String fileName) {
        this.c = c;
        this.textarea=textarea.getText();
        this.filePath = filePath;
        this.fileName = fileName;
        t2 = new JTextArea();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        String text = textarea;
        int no_of_line=0;
        int secondBracketOpen=0;
        int secondBracketClose=0;
        
        if(fileName.toLowerCase().endsWith(".desi") == false)
        {
//            System.out.println(fileName);
//            System.out.println(filePath);
            
            JOptionPane.showMessageDialog(c, "Error !!\nExtention of file sould be \".desi\"", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        File f = new File(filePath);
        File temp = new File(filePath.replace(fileName, "Temp.java"));
        
        try
        {
            FileWriter fw = new FileWriter(filePath);
            f.createNewFile();
            
            fw.append(text);
            fw.close();
        }
        catch(Exception ioex){}
        
        ArrayList<String> al = new ArrayList<String>();
        
        
        try(Scanner sc = new Scanner(f))
        {
            while(sc.hasNext())
            {
                String str=sc.nextLine();
                no_of_line++;
                if(str.trim().length()==0)
                {
                    continue;
                }
                if(str.trim().startsWith("#"))
                {
                    continue;
                }
                if(isFirstline(str))
                {
                    t2.setText("public class Temp\n{\n\tpublic static void main(String [] args)\n\t{\n");
                    break;
                }
                else
                {
                    JOptionPane.showMessageDialog(c, "Error !!\nFirst line should start with \'suru korlam\'", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            while(sc.hasNext())
            {
                String str=sc.nextLine();
                no_of_line++;
                
                if(str.trim().startsWith("#"))
                {
                    continue;
                }
                
                if(isLastLine(str))
                {
                    if(secondBracketOpen!=secondBracketClose)
                    {
                        JOptionPane.showMessageDialog(c, "Error !!\nSecond brackets are unbalanced", "ERROR", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(sc.hasNext())
                    {
                        JOptionPane.showMessageDialog(c, "Error !!\nLast line should end with only \'ses korlam\' \nremove all remaining text", "ERROR", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    else
                    {
                        t2.setText(t2.getText()+"\t}\n}");
                        break;
                    }
                }
                
                if(str.trim().length()==0)
                {
                    continue;
                }
                else if(str.trim().startsWith(PRINT))
                {
                    String newstr = str.trim().substring(PRINT.length());
                    int c=0;
                    int k=0;
                    for(int i=0;i<newstr.length();i++)
                    {
                        if(newstr.charAt(i)=='\"')c++;
                        if(newstr.charAt(i)==',')k++;
                    }
                    
                    if(newstr.contains("\"")==false)
                    {
                        int i=1;
                        int count=0;
                        String temp0="";
                        String temp1=newstr;
                        String temp2="";
                        while(i<=FindChar.noOfChar(newstr, ',')+1)
                        {
                            temp0=new String(temp1);
                            temp1=new String(temp1.substring(temp1.indexOf(',')+1));
                            temp2=new String(temp0.replaceAll(",(.*)", ""));
                            temp0=new String(temp1);
                            
                            if(al.contains(temp2.trim()))
                            {
                                count++;
                            }
                            i++;
                        }
                        
                        if(count==FindChar.noOfChar(newstr, ',')+1)
                        {
                            t2.setText(t2.getText()+"\t\tSystem.out.println("+newstr.replace(",", "+\",\"+")+");\n");
                        }
                        else
                        {
                            System.out.println(count+" "+(FindChar.noOfChar(newstr, ',')+1));
                            JOptionPane.showMessageDialog(this.c, "Error !!\nAt line no : "+no_of_line, "ERROR", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    else if(c%2==0 && ((c/2)-1)==k)
                    {
                        t2.setText(t2.getText()+"\t\tSystem.out.println("+newstr.replace(",", "+")+");\n");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this.c, "Error !!\nAt line no : "+no_of_line, "ERROR", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                else if(str.trim().startsWith(DATATYPE))
                {
                    String newstr = str.trim().substring(DATATYPE.length()+1);
                    int c=0;
                    newstr = newstr.replace(TRUE, "true");
                    newstr = newstr.replace(FALSE, "false");
                    newstr = newstr.replace(NULL, "null");
                    
                    if(FindChar.isNumberContains(newstr.replaceAll("=(.*)", "").trim()))
                    {
                        JOptionPane.showMessageDialog(this.c, "Error !!\nAt line no : "+no_of_line+"\nVariable name can only Alphabet!!!", "ERROR", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    for(int i=0;i<newstr.length();i++)
                    {
                        if(newstr.charAt(i)==' ' && newstr.charAt(i+1)!=' ')
                        {
                            c++;
                        }
                    }
                    if(c<=2 && newstr.contains("="))
                    {
                        t2.setText(t2.getText()+"\t\tvar "+newstr+";\n");
                        al.add(newstr.replaceAll("=(.*)", "").trim());
                    }
                    else if(newstr.contains("=") && newstr.contains("\"") && FindChar.noOfChar(str, '\"')==2)
                    {
                        t2.setText(t2.getText()+"\t\tvar "+newstr+";\n");
                        al.add(newstr.replaceAll("=(.*)", "").trim());
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this.c, "Error !!\nAt line no : "+no_of_line, "ERROR", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                else if(str.trim().startsWith(IF))
                {
                    String newstr = str.trim().substring(IF.length());
                    newstr = newstr.replace(TRUE, "true");
                    newstr = newstr.replace(FALSE, "false");
                    newstr = newstr.replace(NULL, "null");
                    
                    if(newstr.contains("{")==false && newstr.trim().length()!=0 && newstr.contains("(") && newstr.contains(")") && FindChar.afterBracketIsCharIsPresent(newstr))
                    {
                        t2.setText(t2.getText()+"\t\tif"+newstr+"\n");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this.c, "Error !!\nAt line no : "+no_of_line, "ERROR", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                else if(str.trim().startsWith(ELSEIF))
                {
                    String newstr = str.trim().substring(ELSEIF.length());
                    newstr = newstr.replace(TRUE, "true");
                    newstr = newstr.replace(FALSE, "false");
                    newstr = newstr.replace(NULL, "null");
                    
                    if(newstr.contains("{")==false && newstr.trim().length()!=0 && newstr.contains("(") &&newstr.contains(")") && FindChar.afterBracketIsCharIsPresent(newstr))
                    {
                        t2.setText(t2.getText()+"\t\telse if"+newstr+"\n");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this.c, "Error !!\nAt line no : "+no_of_line, "ERROR", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                else if(str.trim().startsWith(ELSE))
                {
                    String newstr = str.trim().substring(ELSE.length());
                    
                    if(newstr.contains("{")==false && newstr.trim().length()==0)
                    {
                        t2.setText(t2.getText()+"\t\telse\n");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this.c, "Error !!\nAt line no : "+no_of_line, "ERROR", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                else if(str.trim().startsWith(WHILE))
                {
                    String newstr = str.trim().substring(WHILE.length());
                    newstr = newstr.replace(TRUE, "true");
                    newstr = newstr.replace(FALSE, "false");
                    newstr = newstr.replace(NULL, "null");
                    if(newstr.contains("{")==false && newstr.trim().length()!=0 && newstr.contains("(") && newstr.contains(")") && FindChar.afterBracketIsCharIsPresent(newstr))
                    {
                        t2.setText(t2.getText()+"\t\twhile"+newstr+"\n");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this.c, "Error !!\nAt line no : "+no_of_line, "ERROR", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                else if(str.trim().startsWith(BREAK))
                {
                    if(str.trim().equals(BREAK))
                    {
                        t2.setText(t2.getText()+"\t\tbreak;\n");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this.c, "Error !!\nAt line no : "+no_of_line, "ERROR", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                else if(str.trim().startsWith(CONTINUE))
                {
                    if(str.trim().equals(CONTINUE))
                    {
                        t2.setText(t2.getText()+"\t\tcontinue;\n");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this.c, "Error !!\nAt line no : "+no_of_line, "ERROR", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                else if(str.trim().equals("{"))
                {
                    secondBracketOpen++;
                    t2.setText(t2.getText()+"\t\t{\n");
                }
                else if(str.trim().equals("}"))
                {
                    secondBracketClose++;
                    t2.setText(t2.getText()+"\t\t}\n");
                }
                else if(str.trim().contains(SLEEP))
                {
                    String newstr = str.trim().substring(SLEEP.length()+1);
                    
                    try
                    {
                        Integer.parseInt(newstr.trim());
                        
                        t2.setText(t2.getText()+"\t\ttry{Thread.sleep("+newstr+",10"+");}catch(Exception e){}\n");
                    }
                    catch(Exception nfe)
                    {
                        JOptionPane.showMessageDialog(this.c, "Error !!\nAt line no : "+no_of_line, "ERROR", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                else if(str.contains("+")||str.contains("-")||str.contains("*")||str.contains("/")||str.contains("+=")||str.contains("-=")||str.contains("/=")||str.contains("*=")||str.contains("--")||str.contains("++")||str.contains("="))
                {
                    String newstr = str.trim();
                    var present = true;
                    var tempst=newstr.replace('+', ' ');
                    tempst=tempst.replace('-', ' ');
                    tempst=tempst.replace('*', ' ');
                    tempst=tempst.replace('/', ' ');
                    tempst=tempst.replace('=', ' ');
                    tempst=tempst.replace('0', ' ');
                    tempst=tempst.replace('1', ' ');
                    tempst=tempst.replace('2', ' ');
                    tempst=tempst.replace('3', ' ');
                    tempst=tempst.replace('4', ' ');
                    tempst=tempst.replace('5', ' ');
                    tempst=tempst.replace('6', ' ');
                    tempst=tempst.replace('7', ' ');
                    tempst=tempst.replace('8', ' ');
                    tempst=tempst.replace('9', ' ');
                    int index=0;
                    String checkstr="",cutstr="";
                    while(true)
                    {
                        index = tempst.indexOf(' ');
                        cutstr= tempst.substring(index);
                        checkstr = tempst.replaceAll(" (.*)","");
                        tempst=cutstr.trim();
                        
                        if(al.contains(checkstr.trim()) == false)
                        {
                            present=false;
                            break;
                        }
                        
                         if(!tempst.contains(" "))
                             break;
                    }
                    
                    if(tempst.trim().length()!=0)
                    {
                        if(al.contains(tempst.trim())==false)
                            present=false;
                    }
                    
                    if(present)
                    {
                        t2.setText(t2.getText()+"\t\t"+newstr+";\n");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this.c, "Error !!\nAt line no : "+no_of_line, "ERROR", JOptionPane.ERROR_MESSAGE);
                        return;
                    }   
                }
                else
                {
                    JOptionPane.showMessageDialog(this.c, "Error !!\nAt line no : "+no_of_line, "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
        catch(Exception io){};
        
        try {
            temp.createNewFile();
            String textarea2=t2.getText();
            FileWriter fw2 = new FileWriter(filePath.replace(fileName, "Temp.java"));
            f.createNewFile();
            
            fw2.append(textarea2);
            fw2.close();
            
        } catch (IOException ex) { }
        
        new RunPrograms(c,filePath.replace(fileName, "Temp.java"));
    }
    private boolean  isFirstline(String str)
    {
        
        if(str.trim().equalsIgnoreCase(START))
        return true;
        else
            return false;
    }
    private boolean isLastLine(String str)
    {
        if(str.trim().equalsIgnoreCase(STOP))
        return true;
        else
            return false;
    }
    
}
