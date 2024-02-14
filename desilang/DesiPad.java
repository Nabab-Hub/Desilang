package desilang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;

public class DesiPad extends JFrame implements ActionListener,CaretListener,KeyListener,WindowListener
{
    private final int font_size=15,percent=100;
    private int per=percent;
    private Font font1,font_for_textarea;
    private JMenuBar menubar;
    private JMenu file,edit,execute,view,settings,zoom;
    private JMenuItem newfile,newWindow,open,save,saveas,pagesetup,print,exit;
    private JMenuItem undo,redo,cut,copy,paste,delete,find,findnext,findprev,replace,goTo,selectAll,timedate,font;
    private JMenuItem run;
    private JMenuItem poprun,popundo,popredo,popcut,popcopy,poppaste,popdelete,popselectAll;
    private JMenuItem zoomin,zoomout,resetzoom;
    private JCheckBoxMenuItem statusbar,wordwrap;
    private JMenuItem theme,color;
    private JPanel statusBar;
    private JLabel rowcol,zoomPercent,windows,utf;
    private JTextArea textarea;
    private JScrollPane scroll;
    private UndoManager undomanager;
    private ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("logo.png")));
    private JFileChooser filechooser;
    private String filePath="",fileName="Untitled";
    private int initLength=0,AfterLength=0;
    
    private static int frameWidth=850,frameHeight=600;
    private static String intialPath="D://";
    
    public DesiPad()
    {
        this.setTitle(fileName+"-Desipad");
        this.setLayout(new BorderLayout());
        this.setSize(frameWidth, frameHeight);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(3);
        font1 = new Font("serif", Font.PLAIN,18 );
        this.setFont(font1);
        this.setIconImage(image.getImage());
        this.setVisible(true);
        
        menubar = new JMenuBar();
        
        file = new JMenu("File  ");
        edit = new JMenu("Edit  ");
        execute = new JMenu("Execute");
        view = new JMenu("View  ");
        zoom = new JMenu("Zoom");
        settings = new JMenu("Settings");
        
        menubar.add(file);
        menubar.add(edit);
        menubar.add(execute);
        menubar.add(view);
        menubar.add(settings);
        
        newfile = new JMenuItem("New                     Ctrl+N");
        newWindow = new JMenuItem("New Window      Ctrl+Shift+N");
        open = new JMenuItem("Open                   Ctrl+O");
        save = new JMenuItem("Save                    Ctrl+S");
        saveas = new JMenuItem("Save as               Ctrl+Shift+S");
        pagesetup = new JMenuItem("page setup");
        print = new JMenuItem("Print                     Ctrl+P");
        exit = new JMenuItem("Exit                       Alt+F4");
        
        file.add(newfile);
        file.add(newWindow);
        file.add(open);
        file.add(save);
        file.add(saveas);
        file.addSeparator();
        file.add(pagesetup);
        file.add(print);
        file.addSeparator();
        file.add(exit);
        
        run = new JMenuItem("Build & Run");
        
        execute.add(run);
        
        undo = new JMenuItem("Undo                     Ctrl+Z");
        redo = new JMenuItem("Redo                     Ctrl+Y");
        cut = new JMenuItem("Cut                        Ctrl+X");
        copy = new JMenuItem("Copy                     Ctrl+C");
        paste = new JMenuItem("Paste                    Ctrl+V");
        delete = new JMenuItem("Delete                  Del");
        find = new JMenuItem("Find                      Ctrl+F");
        findnext = new JMenuItem("Find next              F3");
        findprev = new JMenuItem("Find Previous     Shift+F3");
        replace = new JMenuItem("Replace               Ctrl+H");
        goTo = new JMenuItem("Go to                    Ctrl+G");
        selectAll = new JMenuItem("Select all              Ctrl+A");
        timedate = new JMenuItem("Time/Date             F5");
        font = new JMenuItem("Font");
        
        edit.add(undo);
        edit.add(redo);
        edit.addSeparator();
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(delete);
        edit.addSeparator();
        edit.add(find);
        edit.add(findnext);
        edit.add(findprev);
        edit.add(replace);
        edit.add(goTo);
        edit.addSeparator();
        edit.add(selectAll);
        edit.add(timedate);
        edit.addSeparator();
        edit.add(font);
        
        cut.setEnabled(false);
        copy.setEnabled(false);
        delete.setEnabled(false);
        find.setEnabled(false);
        findnext.setEnabled(false);
        replace.setEnabled(false);
        
        zoomin = new JMenuItem("Zoom in                            Ctrl+Plus");
        zoomout = new JMenuItem("Zoom out                          Ctrl+Minus");
        resetzoom = new JMenuItem("Restore default zoom    Ctrl+0");
        
        zoom.add(zoomin);
        zoom.add(zoomout);
        zoom.add(resetzoom);
        statusbar = new JCheckBoxMenuItem("Status bar");
        statusbar.setSelected(true);
        wordwrap = new JCheckBoxMenuItem("Word wrap");
        
        view.add(zoom);
        view.add(statusbar);
        view.add(wordwrap);
        
        theme = new JMenuItem("Theme");
        color = new JMenuItem("Color");
        
        settings.add(theme);
        settings.add(color);
        
        this.setJMenuBar(menubar);
        
        statusBar = new JPanel(new GridBagLayout());
        statusBar.setFont(font1);
//        statusBar.setBackground(Color.red);
        rowcol = new JLabel("Ln 1,Col 1");
        zoomPercent = new JLabel(Integer.toString(percent)+"%");
        windows = new JLabel("Windows (CRLF)");
        utf = new JLabel("UTF-8");
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.weightx=2.5;
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.ipady=10;
        statusBar.add(new JLabel("    "));
        gbc.gridx=1;
        gbc.gridy=0;
        gbc.gridwidth=1;
        statusBar.add(rowcol,gbc);
        statusBar.add(new JSeparator(SwingConstants.HORIZONTAL));
        gbc.fill=0;
        gbc.weightx=0.5;
        gbc.gridx=2;
        gbc.gridy=0;
        gbc.gridwidth=1;
        statusBar.add(zoomPercent,gbc);
        statusBar.add(separator);
        gbc.gridx=3;
        gbc.gridy=0;
        gbc.gridwidth=1;
        statusBar.add(windows,gbc);
        statusBar.add(separator);
        gbc.gridx=4;
        gbc.gridy=0;
        statusBar.add(utf,gbc);
        this.add(statusBar,BorderLayout.PAGE_END);
        
        statusbar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            
                if(statusbar.isSelected())
                {
                    statusBar.setVisible(true);
                }
                else
                {
                    statusBar.setVisible(false);
                }
            }
        });
        wordwrap.addActionListener(this);
        
        newfile.addActionListener(this);
        newWindow.addActionListener(this);
        open.addActionListener(this);
        save.addActionListener(this);
        saveas.addActionListener(this);
        pagesetup.addActionListener(this);
        print.addActionListener(this);
        exit.addActionListener(this);
        
        undo.addActionListener(this);
        redo.addActionListener(this);
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        delete.addActionListener(this);
        find.addActionListener(this);
        findnext.addActionListener(this);
        findprev.addActionListener(this);
        replace.addActionListener(this);
        goTo.addActionListener(this);
        selectAll.addActionListener(this);
        timedate.addActionListener(this);
        font.addActionListener(this);
        
        zoomin.addActionListener(this);
        zoomout.addActionListener(this);
        resetzoom.addActionListener(this);
        
        textarea = new JTextArea();
        font_for_textarea = new Font(Font.SANS_SERIF, Font.PLAIN, font_size);
        textarea.setFont(font_for_textarea);
        scroll = new JScrollPane(textarea);
        scroll.setBorder(null);
        this.add(scroll,BorderLayout.CENTER);
        textarea.addCaretListener(this);
        
        poprun = new JMenuItem("Build & Run");
        popundo = new JMenuItem("Undo                     Ctrl+Z");
        popredo = new JMenuItem("Redo                     Ctrl+Y");
        popcut = new JMenuItem("Cut                        Ctrl+X");
        popcopy = new JMenuItem("Copy                     Ctrl+C");
        poppaste = new JMenuItem("Paste                    Ctrl+V");
        popdelete = new JMenuItem("Delete                  Del");
        popselectAll = new JMenuItem("Select all              Ctrl+A");
        popcut.setEnabled(false);
        popcopy.setEnabled(false);
        popdelete.setEnabled(false);
        poprun.addActionListener(this);
        popundo.addActionListener(this);
        popredo.addActionListener(this);
        popcut.addActionListener(this);
        popcopy.addActionListener(this);
        poppaste.addActionListener(this);
        popdelete.addActionListener(this);
        popselectAll.addActionListener(this);
        
        JPopupMenu popupmenu = new JPopupMenu();
        popupmenu.add(poprun);
        popupmenu.addSeparator();
        popupmenu.add(popundo);
        popupmenu.add(popredo);
        popupmenu.addSeparator();
        popupmenu.add(popcut);
        popupmenu.add(popcopy);
        popupmenu.add(poppaste);
        popupmenu.add(popdelete);
        popupmenu.addSeparator();
        popupmenu.add(popselectAll);
        textarea.add(popupmenu);
        
        textarea.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                
                if(e.getButton()==MouseEvent.BUTTON3)
                {
                    var x = textarea.getWidth();
                    var y = textarea.getHeight();
                    if(e.getX()+183>x && e.getY()+181>y)
                    popupmenu.show(textarea, e.getX()-170, e.getY()-170); 
                    else if(e.getX()+183>x)
                    popupmenu.show(textarea, e.getX()-170, e.getY());
                    else if(e.getY()+181 >y)
                    popupmenu.show(textarea, e.getX(), e.getY()-170);
                    else
                    popupmenu.show(textarea, e.getX(), e.getY());
                }
            }
        
            
        });
        
        undomanager = new UndoManager();
        
        textarea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
            
                undomanager.addEdit(e.getEdit());
            }
        });
        
        
        textarea.addKeyListener(this);
        
        filechooser = new JFileChooser(intialPath);
        
//        filechooser.setAcceptAllFileFilterUsed(false);
        
        FileNameExtensionFilter filter1 = new FileNameExtensionFilter("only text files (.txt)", "txt");
        FileNameExtensionFilter filter2 = new FileNameExtensionFilter("only java files (.java)", "java");
        FileNameExtensionFilter filter3 = new FileNameExtensionFilter("only python files (.py)", "py");
        FileNameExtensionFilter filter4 = new FileNameExtensionFilter("only desi language files (.desi)", "desi");
        FileNameExtensionFilter filter5 = new FileNameExtensionFilter("only html files (.html)", "html");
        FileNameExtensionFilter filter6 = new FileNameExtensionFilter("only css files (.css)", "css");
        FileNameExtensionFilter filter7 = new FileNameExtensionFilter("only javascript files (.js)", "js");
        filechooser.addChoosableFileFilter(filter4);
        filechooser.addChoosableFileFilter(filter1);
        filechooser.addChoosableFileFilter(filter2);
        filechooser.addChoosableFileFilter(filter3);
        filechooser.addChoosableFileFilter(filter5);
        filechooser.addChoosableFileFilter(filter6);
        filechooser.addChoosableFileFilter(filter7);
        run.addActionListener(this);
        this.addWindowListener(this);
        this.validate();
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource()==wordwrap)
        {
            if(wordwrap.isSelected())
            {
                textarea.setLineWrap(true);
                textarea.setWrapStyleWord(true);
            }
            else
            {
                textarea.setLineWrap(false);
                textarea.setWrapStyleWord(false);
            }
        }
        if(e.getSource()==newfile)
        {
            newFile();
        }
        if(e.getSource()==newWindow)
        {
            DesiPad n = new DesiPad();
        }
        if(e.getSource()==open)
        {
            openFile();
        }
        if(e.getSource()==save)
        {
            saveFile();
        }
        if(e.getSource()==saveas)
        {
            saveAsFile();
        }
        if(e.getSource()==pagesetup)
        {
            
        }
        if(e.getSource()==print)
        {
            
        }
        if(e.getSource()==exit)
        {
            exitNotepad();
        }
        
        if(e.getSource() == poprun)
        {
            saveFile();
            if(this.fileName.equalsIgnoreCase("Untitled")==false)
                new RunAction(this.getContentPane(),textarea, filePath,fileName).actionPerformed(e);
        }
        if(e.getSource()==undo || e.getSource()==popundo)
        {
            if(undomanager.canUndo())
            {
                undomanager.undo();
            }
        }
        if(e.getSource()==redo || e.getSource()==popredo)
        {
            if(undomanager.canRedo())
            {
                undomanager.redo();
            }
        }
        if(e.getSource()==cut || e.getSource()==popcut)
        {
            textarea.cut();
        }
        if(e.getSource()==copy || e.getSource()==popcopy)
        {
            textarea.copy();
        }
        if(e.getSource()==paste || e.getSource()==poppaste)
        {
            textarea.paste();
        }
        if(e.getSource()==delete || e.getSource()==popdelete)
        {
            var selectStart = textarea.getSelectionStart();
            var selectEnd = textarea.getSelectionEnd();
            if(selectStart!=selectEnd)
            {
                textarea.replaceRange("", selectStart, selectEnd);
            }
        }
        if(e.getSource()==find)
        {
            
        }
        if(e.getSource()==findnext)
        {
            
        }
        if(e.getSource()==findprev)
        {
            
        }
        if(e.getSource()==replace)
        {
            
        }
        if(e.getSource()==goTo)
        {
            
        }
        if(e.getSource()==selectAll || e.getSource()==popselectAll)
        {
            textarea.selectAll();
        }
        if(e.getSource()==timedate)
        {
            
        }
        if(e.getSource()==font)
        {
            
        }
        
        if(e.getSource()==run)
        {
            saveFile();
            if(this.fileName.equalsIgnoreCase("Untitled")==false)
                new RunAction(this.getContentPane(),textarea, filePath,fileName).actionPerformed(e);
        }
        
        if(e.getSource()==zoomin)
        {
            if(textarea.getFont().getSize()<81 )
            {
                per+=5;
                textarea.setFont(new Font(textarea.getFont().getName(), textarea.getFont().getStyle(), textarea.getFont().getSize()+1));
                zoomPercent.setText(Integer.toString(per)+"%");
            }
        }
        if(e.getSource()==zoomout)
        {
            if(textarea.getFont().getSize()>4)
            {
                per-=5;
                textarea.setFont(new Font(textarea.getFont().getName(), textarea.getFont().getStyle(), textarea.getFont().getSize()-1));
                zoomPercent.setText(Integer.toString(per)+"%");
            }
        }
        if(e.getSource()==resetzoom)
        {
            per=percent;
            textarea.setFont(new Font(textarea.getFont().getName(), textarea.getFont().getStyle(), font_size));
            zoomPercent.setText(Integer.toString(percent)+"%");
        }
    }

    @Override
    public void caretUpdate(CaretEvent e) 
    {
        if(e.getSource()==textarea)
        {
            var dot = e.getDot();
            var row = 1;
            var col = 1;
            
            try{
                row = textarea.getLineOfOffset(dot)+1;
                col = dot - textarea.getLineStartOffset(row-1)+1;
            }
            catch(Exception ee)
            {
                ee.printStackTrace();
            }
            rowcol.setText("Ln "+row+", Col "+col);
            
            if(textarea.getSelectionStart()!=textarea.getSelectionEnd())
            {
                cut.setEnabled(true);
                copy.setEnabled(true);
                delete.setEnabled(true);
                popcut.setEnabled(true);
                popcopy.setEnabled(true);
                popdelete.setEnabled(true);
                find.setEnabled(true);
                findnext.setEnabled(true);
                findprev.setEnabled(true);
                replace.setEnabled(true);
            }
            else
            {
                cut.setEnabled(false);
                copy.setEnabled(false);
                delete.setEnabled(false);
                popcut.setEnabled(false);
                popcopy.setEnabled(false);
                popdelete.setEnabled(false);
                find.setEnabled(false);
                findnext.setEnabled(false);
                findprev.setEnabled(false);
                replace.setEnabled(false);
            }
            
            if(undomanager.canUndo())
            {
                undo.setEnabled(true);
                popundo.setEnabled(true);
            }
            else
            {
                undo.setEnabled(false); 
                popundo.setEnabled(false); 
            }
            if(undomanager.canRedo())
            {
                redo.setEnabled(true);
                popredo.setEnabled(true);
            }
            else
            {
                redo.setEnabled(false); 
                popredo.setEnabled(false); 
            }
            AfterLength=textarea.getDocument().getLength();
            if(initLength != AfterLength)
            {
                this.setTitle("*"+fileName+"-Desipad");
            }
        }
    } 

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        
        if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_Z)
        {
            if(undomanager.canUndo())
            {
                undomanager.undo();
            }
        }
        if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_Y)
        {
            if(undomanager.canRedo())
            {
                undomanager.redo();
            }
        }
        if(e.isControlDown() && e.isShiftDown() && e.getKeyCode()==KeyEvent.VK_N)
        {
            new DesiPad();
        }
        if(e.isControlDown() && e.getKeyCode()==107)
        {
            if(textarea.getFont().getSize()<81 )
            {
                per+=5;
                textarea.setFont(new Font(textarea.getFont().getName(), textarea.getFont().getStyle(), textarea.getFont().getSize()+1));
                zoomPercent.setText(Integer.toString(per)+"%");
            }
        }
        if(e.isControlDown() && e.getKeyCode()==109)
        {
            if(textarea.getFont().getSize()>4)
            {
                per-=5;
                textarea.setFont(new Font(textarea.getFont().getName(), textarea.getFont().getStyle(), textarea.getFont().getSize()-1));
                zoomPercent.setText(Integer.toString(per)+"%");
            }
        }
        if(e.isControlDown() && (e.getKeyCode()==KeyEvent.VK_NUMPAD0 || e.getKeyCode()==KeyEvent.VK_0))
        {
            per=percent;
            textarea.setFont(new Font(textarea.getFont().getName(), textarea.getFont().getStyle(), font_size));
            zoomPercent.setText(Integer.toString(percent)+"%");
        }
        if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_O)
        {
            openFile();
        }
        if(e.isControlDown() && e.isShiftDown() && e.getKeyCode()==KeyEvent.VK_S)
        {
            saveAsFile();
        }
        if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_S)
        {
            saveFile();
        }
        if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_N)
        {
            newFile();
        }
//        if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_R)
//        {
//            new RunAction(this.getContentPane(),textarea, filePath,fileName).actionPerformed(new ActionEvent);
//        }
        if(e.isAltDown() && e.getKeyCode()==KeyEvent.VK_F4)
        {
            exitNotepad();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
    private void openFile()
    {
        newFile();
        if(filechooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION)
            {
                filePath=filechooser.getSelectedFile().getAbsolutePath();
                fileName=filechooser.getSelectedFile().getName();
                
                
                File openFile = new File(filePath);
                
                try {
                    Scanner sc = new Scanner(openFile);
                    
                    textarea.setText(null);
                    while(sc.hasNext())
                    {
                        textarea.setText(textarea.getText()+sc.nextLine()+"\n");
                    }
                } catch (FileNotFoundException ex) {
                }
                initLength=textarea.getDocument().getLength();
                this.setTitle(fileName+"-Desipad");
            }
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
    
        exitNotepad();
        
    }

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
    
    private void saveFile()
    {
        if(filePath.length()==0)
            {
                if(filechooser.showSaveDialog(this)==JFileChooser.APPROVE_OPTION)
                {
                    filePath=filechooser.getSelectedFile().getAbsolutePath();
                    fileName=filechooser.getSelectedFile().getName();
                    
                
                    File newFile = new File(filePath);
                
                    try{
                        newFile.createNewFile();
                        FileWriter fw = new FileWriter(newFile);
                        fw.append(textarea.getText());
                    
                        fw.close();
                    }catch(Exception ep){}
                    initLength=textarea.getDocument().getLength();
                    this.setTitle(fileName+"-Desipad");
                }
            }
            else
            {
                File newFile = new File(filePath);
                
                    try
                    {
                        newFile.createNewFile();
                        FileWriter fw = new FileWriter(newFile);
                        fw.append(textarea.getText());
                    
                        fw.close();
                    }catch(Exception ep){}
                initLength=textarea.getDocument().getLength();
                this.setTitle(fileName+"-Desipad");
            }
    }
    private void saveAsFile()
    {
        if(filechooser.showSaveDialog(this)==JFileChooser.APPROVE_OPTION)
            {
                filePath=filechooser.getSelectedFile().getAbsolutePath();
                fileName=filechooser.getSelectedFile().getName();
                this.setTitle(fileName+"-Desipad");
                
                File newFile = new File(filePath);
                
                try{
                    newFile.createNewFile();
                    FileWriter fw = new FileWriter(newFile);
                    fw.append(textarea.getText());
                    
                    fw.close();
                }catch(Exception ep){}
                initLength=textarea.getDocument().getLength();
            }
    }
    private void newFile()
    {
        AfterLength=textarea.getDocument().getLength();
            if(AfterLength==0 && fileName.equalsIgnoreCase("untitled"))
            {
                fileName="Untitled";
                initLength=0;
                AfterLength=0;
                textarea.setText("");
                filePath="";
                this.setTitle(fileName+"-Desipad");
            }
            else if(AfterLength!=0 && fileName.equals("Untitled"))
            {
                int option = JOptionPane.showConfirmDialog(this, "Do you want to save changes to "+fileName+" ?", "DesiPad", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(option == JOptionPane.OK_OPTION)
                {
                    saveAsFile();
                    fileName="Untitled";
                    textarea.setText("");
                    initLength=0;
                    AfterLength=0;
                    filePath="";
                    this.setTitle(fileName+"-Desipad");
                }
                
            }
            else
            {
                int option = JOptionPane.showConfirmDialog(this, "Do you want to save changes to\n"+filePath+" ?", "DesiPad", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(option == JOptionPane.OK_OPTION)
                {
                    saveFile();
                    fileName="Untitled";
                    textarea.setText("");  
                    initLength=0;
                    AfterLength=0;
                    filePath="";
                    this.setTitle(fileName+"-Desipad");
                }
            }
    }
    
    private void exitNotepad()
    {
        
        File saveState = new File("D:\\.log.dat");
        
        try{
            saveState.createNewFile();
            FileWriter writeState = new FileWriter(saveState);
            
            String text = Integer.toString(this.getWidth()) + "\n"+Integer.toString(this.getHeight())+ "\n" + filePath.replace(fileName, "");
            
            
            writeState.append(text);
            
            writeState.close();
            
            
        }catch(Exception foe){}
        newFile();
//        saveFile();
    }
    
    static
    {
        try{
            File intialState = new File("D:\\.log.dat");
            Scanner sc = new Scanner(intialState);
            
            if(sc.hasNext())
            frameWidth=Integer.parseInt(sc.nextLine().trim());
            
            if(sc.hasNext())
            frameHeight=Integer.parseInt(sc.nextLine().trim());
            
            if(sc.hasNext())
            intialPath=sc.nextLine().trim();
            
        }catch(Exception fiexc){}
    }
}
