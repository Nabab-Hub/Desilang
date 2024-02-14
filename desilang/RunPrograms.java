package desilang;

import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class RunPrograms implements Runnable{
    static JTextArea text;
    String path;
    Container c;
    private static boolean flag=false;
    private JScrollPane scroll;
    
    Thread runThis; 
    
    RunPrograms(Container c,String path)
    {
        this.path=path;
        this.c=c;
        flag=false;
        runThis = new Thread(this);
        runThis.start();
    }
    public void run()
    {
        
        JDialog d = new JDialog();
        d.setSize(500, 400);
        d.setLocationRelativeTo(null);
        d.setTitle("Output");
        d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        d.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("left-quote.png")));
        
        
        text = new JTextArea();
        text.setFont(new Font("Arial",Font.BOLD,20));
        text.setEditable(false);
        scroll = new JScrollPane(text);
        scroll.setBorder(null);
        d.add(scroll);
        
        d.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                d.dispose();
                flag=true;
                Path deltePath = Paths.get(path);
                try{
                    Files.delete(deltePath);
                }
                catch(Exception ep)
                {
                    
                }
            }
            
        });
        
        d.setVisible(true);
        try {
            // Specify the commands to compile and run Java programs
         //   List<String> compileCommand = new ArrayList<>(List.of("javac", "C:\\Users\\skahi\\Desktop\\T.java"));
            List<String> runCommand = new ArrayList<>(List.of("java", path));
//            List<String> delCommand = new ArrayList<>(List.of("del ", path));

            // Compile and run YourOtherProgram
            if(executeCommand(runCommand)==0)
            {
                Path deltePath = Paths.get(path);
                try{
                    Files.delete(deltePath);
                }
                catch(Exception ep)
                {
                    
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static int executeCommand(List<String> command) throws IOException, InterruptedException {
        int status;
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        // Optionally, you can read the output of the process
         InputStream inputStream = process.getInputStream();
         BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
         String line;
         while ((line = reader.readLine()) != null) {
//             System.out.println(line);
             text.setText(text.getText()+line+"\n");
             
             if(flag==true)break;
         }

        // Wait for the process to finish
        status=process.waitFor();
        reader.close();
        
        return status;
    }
}