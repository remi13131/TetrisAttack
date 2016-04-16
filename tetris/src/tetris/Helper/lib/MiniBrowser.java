package tetris.Helper.lib;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.*;
 
/**
 *
 * @author http://www.java-tips.org/java-se-tips-100019/15-javax-swing/1394-how-to-create-a-simple-browser-in-swing.html
 * EDITED By Remi
 * @see <a href="http://www.java-tips.org/java-se-tips-100019/15-javax-swing/1394-how-to-create-a-simple-browser-in-swing.html"> http://www.java-tips.org/java-se-tips-100019/15-javax-swing/1394-how-to-create-a-simple-browser-in-swing.html </a>
 */

// The Simple Web Browser.
public class MiniBrowser extends JFrame
         
    implements HyperlinkListener {
     
    // Editor pane for displaying pages.
    private final JEditorPane displayEditorPane;
     
    private final JButton backButton;
            
    String index = "";
    
    // Browser's list of pages that have been visited.
    private final ArrayList<String> pageList = new ArrayList<String>();
     
    // Constructor for Mini Web Browser.
    public MiniBrowser(String url) {
        // Set application title.
        super("Mini Browser");
         
        index = url;
        
        // Set window size.
        setSize(640, 480);
         
        // Handle closing events.
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE );
         
        // Set up file menu.
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        JMenuItem fileExitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        fileExitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionExit();
            }
        });
        fileMenu.add(fileExitMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
         
        JPanel buttonPanel = new JPanel();
        backButton = new JButton("< Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionBack();
            }
        });
        buttonPanel.add(backButton);
        
        // Set up page display.
        displayEditorPane = new JEditorPane();
        displayEditorPane.setContentType("text/html");
        displayEditorPane.setEditable(false);
        displayEditorPane.addHyperlinkListener(this);
         
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buttonPanel, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(displayEditorPane),BorderLayout.CENTER);
        
       showPage(getClass().getResource(url), true);
    }
     
    // Exit this program.
    private void actionExit() {
        this.dispose();
    }   
     
    // Show dialog box with error message.
    private void showError(String errorMessage) {
        //JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        System.out.println("MiniBrowser : "+errorMessage+" Error "+JOptionPane.ERROR_MESSAGE);
    }
     
    // Verify URL format.
    private URL verifyUrl(String url) {
        // Only allow HTTP URLs.
        if (!url.toLowerCase().startsWith("http://"))
            return null;
         
        // Verify format of URL.
        URL verifiedUrl = null;
        try {
            verifiedUrl = new URL(url);
        } catch (Exception e) {
            return null;
        }
         
        return verifiedUrl;
    }
     
  /* Show the specified page and add it to
     the page list if specified. */
    private void showPage(URL pageUrl, boolean addToList) {
        // Show hour glass cursor while crawling is under way.
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
         
        try {
            // Get URL of page currently being displayed.
            URL currentUrl = displayEditorPane.getPage();
             
            // Load and display specified page.
            displayEditorPane.setPage(pageUrl);
             
            // Get URL of new page being displayed.
            URL newUrl = displayEditorPane.getPage();
             
            // Add page to list if specified.
            if (addToList) {
                int listSize = pageList.size();
                if (listSize <= 0) {
                  return;
                }
                int pageIndex = pageList.indexOf(currentUrl.toString());
                if (pageIndex >= listSize - 1) {
                  return;
                }
                for (int i = listSize - 1; i > pageIndex; i--) {
                  pageList.remove(i);
                }
                pageList.add(newUrl.toString());
            }
            
        } catch (Exception e) {
            // Show error messsage.
            showError("Unable to load page");
        } finally {
            // Return to default cursor.
            setCursor(Cursor.getDefaultCursor());
        }
    }
    
    // Go back to the page viewed before the current page.
    private void actionBack() {
        showPage(getClass().getResource(index), true);
    }
    
    // Handle hyperlink's being clicked.
    @Override
    public void hyperlinkUpdate(HyperlinkEvent event) {
        HyperlinkEvent.EventType eventType = event.getEventType();
        if (eventType == HyperlinkEvent.EventType.ACTIVATED) {
            if (event instanceof HTMLFrameHyperlinkEvent) {
                HTMLFrameHyperlinkEvent linkEvent =
                        (HTMLFrameHyperlinkEvent) event;
                HTMLDocument document =
                        (HTMLDocument) displayEditorPane.getDocument();
                document.processHTMLFrameHyperlinkEvent(linkEvent);
            } else {
                showPage(event.getURL(), true);
            }
        }
    }
}
