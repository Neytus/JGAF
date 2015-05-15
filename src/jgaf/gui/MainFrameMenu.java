/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.gui;

import java.io.File;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import jgaf.JgafFileException;
import jgaf.editor.Editor;
import jgaf.editor.EditorsHandler;
import jgaf.editor.EditorDescriptor;
import jgaf.editor.NoSuchEditorException;
import jgaf.environment.Environment;
import jgaf.procedure.ProcedureDescriptor;
import jgaf.register.EditorRegister;
import jgaf.environment.PropertiesHandler;
import jgaf.procedure.Procedure;
import jgaf.procedure.ProcedureDialog;
import jgaf.procedure.ProcedureHandler;
import org.dom4j.DocumentException;

/**
 *
 * @author hanis
 */
public class MainFrameMenu extends JMenuBar {

    private static final String MENU_ICONS_PATH = "/jgaf/icons/menubar/";
    private static final String ICON_FILE_NEW_PATH = MENU_ICONS_PATH + "file/new.png";
    private static final String ICON_FILE_OPEN_PATH = MENU_ICONS_PATH + "file/open.png";
    //private static final String ICON_FILE_OPENSTREAM_PATH = MENU_ICONS_PATH + "file/new.png";
    private static final String ICON_FILE_SAVE_PATH = MENU_ICONS_PATH + "file/save.png";
    private static final String ICON_FILE_SAVEAS_PATH = MENU_ICONS_PATH + "file/saveAs.png";
    private static final String ICON_FILE_PRINT_PATH = MENU_ICONS_PATH + "file/print.png";
    private static final String ICON_FILE_EXIT_PATH = MENU_ICONS_PATH + "file/exit.png";
    private static final String ICON_FILE_IMPORT_TXT_PATH = MENU_ICONS_PATH + "file/import/txt.png";
    private static final String ICON_FILE_IMPORT_XML_PATH = MENU_ICONS_PATH + "file/import/xml.png";
    private static final String ICON_FILE_EXPORT_PNG_PATH = MENU_ICONS_PATH + "file/export/png.png";
    private static final String ICON_FILE_EXPORT_JPG_PATH = MENU_ICONS_PATH + "file/export/jpg.png";
    private static final String ICON_FILE_EXPORT_SVG_PATH = MENU_ICONS_PATH + "file/export/svg.png";
    private static final String ICON_FILE_EXPORT_XML_PATH = MENU_ICONS_PATH + "file/export/xml.png";
    private static final String ICON_FILE_EXPORT_TXT_PATH = MENU_ICONS_PATH + "file/export/txt.png";
    private static final String ICON_FILE_EXPORT_HTM_PATH = MENU_ICONS_PATH + "file/export/htm.png";
    private static final String ICON_FILE_EXPORT_GIF_PATH = MENU_ICONS_PATH + "file/export/gif.png";

    private static final String ICON_EDIT_REDO_PATH = MENU_ICONS_PATH + "edit/redo.png";
    private static final String ICON_EDIT_UNDO_PATH = MENU_ICONS_PATH + "edit/undo.png";
    private static final String ICON_EDIT_SELECT_ALL_PATH = MENU_ICONS_PATH + "edit/selectAll.png";

    private static final String ICON_VIEW_ZOOM_IN_PATH = MENU_ICONS_PATH + "view/zoomIn.png";
    private static final String ICON_VIEW_ZOOM_OUT_PATH = MENU_ICONS_PATH + "view/zoomOut.png";
    private static final String ICON_VIEW_CLEAR_ZOOM_PATH = MENU_ICONS_PATH + "view/clearZoom.png";
    private static final String ICON_VIEW_BEST_FIT_PATH = MENU_ICONS_PATH + "view/adjust.png";

    private static final String ICON_VIEW_LAYOUTS_GEM_PATH = MENU_ICONS_PATH + "view/layouts/gem.png";

    private JMenu file;
    private JMenu fileNew;
    private JMenuItem fileOpen;
    private JMenu fileOpenRecent;
    private JMenuItem fileSave;
    private JMenuItem fileSaveAs;
    private JMenu fileExport;
    private JMenuItem fileExportTXT;
    private JMenuItem fileExportPNG;
    private JMenuItem fileExportSVG;
    private JMenuItem fileExportXML;
    private JMenuItem fileExportJSON;
    private JMenuItem fileExportJPG;
    private JMenuItem fileExportPDF;
    private JMenuItem fileExportGIF;
    private JMenu fileImport;
    private JMenuItem fileImportTXT;
    private JMenuItem fileImportXML;
    private JMenuItem fileImportJSON;
    private JMenuItem filePrint;
    private JMenuItem fileExit;

    
    
    
//    private JMenu edit;
//    private JMenuItem editUndo;
//    private JMenuItem editRedo;
//    private JMenuItem editSelectAll;
//    private JMenuItem editProperties;



//    private JMenu view;
//    private JMenuItem viewZoomIn;
//    private JMenuItem viewZoomOut;
//    private JMenuItem viewClearZoom;
//    private JMenuItem viewBestFit;
//    private JMenu viewLayouts;
//    private JMenuItem viewLayoutsGEM;
//    private JCheckBoxMenuItem viewShowLabels;

        
    private JMenu transformation;
    private JMenu analysis;
    private JMenu simulation;
    private JMenu tools;
    private JMenu procedures;



    private JMenu window;


    private JMenu help;
    private JMenuItem helpAbout;


    private MainFrame mainFrame;
    private EditorsHandler editorHandler;

    public MainFrameMenu(MainFrame mainFrame) {
        super();
        this.mainFrame = mainFrame;
        this.editorHandler = Environment.getInstance().getEditorHandler();
        init();
    }

    private void init() {
        /////////////////////////////////////////////////////
        //////////////////  FILE  ///////////////////////////
        /////////////////////////////////////////////////////
        file = new JMenu(jgaf.l18n.Resource.getValue("menu.file"));
        file.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                boolean shown = editorHandler.isEditorShown();
                boolean changed = true;
                fileExport.setEnabled(shown);
                fileSave.setEnabled(shown && changed);
                fileSaveAs.setEnabled(shown && changed);
            }
        });


        //////////////////  FILE -> NEW  ///////////////////////////
        fileNew = new JMenu(jgaf.l18n.Resource.getValue("menu.file.new"));

        fileNew.addMenuListener(new MenuListener() {
            public void menuCanceled(MenuEvent evt) {
            }
            public void menuDeselected(MenuEvent evt) {
            }
            public void menuSelected(MenuEvent evt) {
                generateNewFileSubMenu();
            }
        });
        file.add(fileNew);





        //////////////////  FILE -> OPEN  ///////////////////////////
        fileOpen = new JMenuItem();
        fileOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        fileOpen.setIcon(new ImageIcon(getClass().getResource(ICON_FILE_OPEN_PATH)));
        fileOpen.setText(jgaf.l18n.Resource.getValue("menu.file.open"));
        fileOpen.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    editorHandler.open();
                } catch (NoSuchEditorException ex) {
                    showWarningDialog(jgaf.l18n.Resource.getValue("exception.file.not-supported"),
                            jgaf.l18n.Resource.getValue("exception.title.warning"));
                } catch (DocumentException ex) {
                    showWarningDialog(jgaf.l18n.Resource.getValue("exception.file.not-supported"),
                            jgaf.l18n.Resource.getValue("exception.title.warning"));
                } catch (JgafFileException ex) {
                    showWarningDialog(jgaf.l18n.Resource.getValue("xception.file.cannot-open") + " " + ex,
                            jgaf.l18n.Resource.getValue("exception.title.warning"));
                }
            }
        });
        file.add(fileOpen);


        //////////////////  FILE -> OPEN RECENT FILE ///////////////////////////
        fileOpenRecent = new JMenu(jgaf.l18n.Resource.getValue("menu.file.openRecentFile"));
        fileOpenRecent.addMenuListener(new MenuListener() {

            public void menuCanceled(MenuEvent evt) {
            }

            public void menuDeselected(MenuEvent evt) {
            }

            public void menuSelected(MenuEvent evt) {
                generateRecentFilesSubMenu();
            }
        });
        file.add(fileOpenRecent);

        //    file.addSeparator();

        //////////////////  FILE -> SAVE  ///////////////////////////
        fileSave = new JMenuItem();
        fileSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        fileSave.setIcon(new ImageIcon(getClass().getResource(ICON_FILE_SAVE_PATH)));
        fileSave.setText(jgaf.l18n.Resource.getValue("menu.file.save"));
        fileSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editorHandler.save();
            }
        });
        file.add(fileSave);

        //////////////////  FILE -> SAVE AS  ///////////////////////////
        fileSaveAs = new JMenuItem();
      //  fileSaveAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        fileSaveAs.setIcon(new ImageIcon(getClass().getResource(ICON_FILE_SAVEAS_PATH)));
        fileSaveAs.setText(jgaf.l18n.Resource.getValue("menu.file.saveAs"));
        fileSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editorHandler.saveAs();
            }
        });
        file.add(fileSaveAs);
        //     file.addSeparator();




        //////////////////  FILE -> IMPORT ///////////////////////////
    //    fileImport = new JMenu(jgaf.l18n.Resource.getValue("menu.file.import"));

        //////////////////  FILE -> IMPORT -> TXT ///////////////////////////
//        fileImportTXT = new JMenuItem();
//        fileImportTXT.setIcon(new ImageIcon(getClass().getResource(ICON_FILE_IMPORT_TXT_PATH)));
//        fileImportTXT.setText("TXT");
//        fileImportTXT.addActionListener(new java.awt.event.ActionListener() {
//
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                mainFrame.ImportFileTXT();
//            }
//        });
      //  fileImport.add(fileImportTXT);

        //////////////////  FILE -> IMPORT -> XML  ///////////////////////////
//        fileImportXML = new JMenuItem();
//        fileImportXML.setIcon(new ImageIcon(getClass().getResource(ICON_FILE_IMPORT_XML_PATH)));
//        fileImportXML.setText("XML");
//        fileImportXML.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                editorsHandler.importXML();
//            }
//        });
//        fileImport.add(fileImportXML);

        //////////////////  FILE -> IMPORT -> JSON  ///////////////////////////
//        fileImportJSON = new JMenuItem();
//        fileImportJSON.setIcon(new ImageIcon(getClass().getResource(ICON_FILE_IMPORT_TXT_PATH)));
//        fileImportJSON.setText(jgaf.l18n.Resource.getValue("menu.file.type.json"));
//        fileImportJSON.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//              //  mainFrame.importFileJSON();
//            }
//        });
//     //   fileImport.add(fileImportJSON);
//
//
//        file.add(fileImport);

        //////////////////  FILE -> EXPORT  ///////////////////////////
        fileExport = new JMenu(jgaf.l18n.Resource.getValue("menu.file.export"));

        //////////////////  FILE -> EXPORT -> SVG  ///////////////////////////
        fileExportSVG = new JMenuItem();
        fileExportSVG.setIcon(new ImageIcon(getClass().getResource(ICON_FILE_EXPORT_SVG_PATH)));
        fileExportSVG.setText(jgaf.l18n.Resource.getValue("menu.file.type.svg"));
        fileExportSVG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editorHandler.exportSVG();
            }
        });
        fileExport.add(fileExportSVG);

//        //////////////////  FILE -> EXPORT -> TXT  ///////////////////////////
//        fileExportJSON = new JMenuItem();
//        fileExportJSON.setIcon(new ImageIcon(getClass().getResource(ICON_FILE_EXPORT_TXT_PATH)));
//        fileExportJSON.setText(jgaf.l18n.Resource.getValue("menu.file.type.txt"));
//        fileExportJSON.addActionListener(new java.awt.event.ActionListener() {
//
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                //mainFrame.exportFileJSON();
//            }
//        });
//    //    fileExport.add(fileExportJSON);
//
//        //////////////////  FILE -> EXPORT -> XML  ///////////////////////////
//        fileExportXML = new JMenuItem();
//        fileExportXML.setIcon(new ImageIcon(getClass().getResource(ICON_FILE_EXPORT_XML_PATH)));
//        fileExportXML.setText(jgaf.l18n.Resource.getValue("menu.file.type.xml"));
//        fileExportXML.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                editorHandler.exportXML();
//            }
//        });
//        fileExport.add(fileExportXML);

        //////////////////  FILE -> EXPORT -> PNG  ///////////////////////////
        fileExportPNG = new JMenuItem();
        fileExportPNG.setIcon(new ImageIcon(getClass().getResource(ICON_FILE_EXPORT_PNG_PATH)));
        fileExportPNG.setText(jgaf.l18n.Resource.getValue("menu.file.type.png"));
        fileExportPNG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editorHandler.exportPNG();
            }
        });
        fileExport.add(fileExportPNG);

        file.add(fileExport);

        //////////////////  FILE -> EXPORT -> JPG  ///////////////////////////
        fileExportJPG = new JMenuItem();
        fileExportJPG.setIcon(new ImageIcon(getClass().getResource(ICON_FILE_EXPORT_JPG_PATH)));
        fileExportJPG.setText(jgaf.l18n.Resource.getValue("menu.file.type.jpg"));
        fileExportJPG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editorHandler.exportJPG();
            }
        });
        fileExport.add(fileExportJPG);

        
        //////////////////  FILE -> EXPORT -> GIF  ///////////////////////////
        fileExportGIF = new JMenuItem();
        fileExportGIF.setIcon(new ImageIcon(getClass().getResource(ICON_FILE_EXPORT_GIF_PATH)));
        fileExportGIF.setText(jgaf.l18n.Resource.getValue("menu.file.type.gif"));
        fileExportGIF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editorHandler.exportGIF();
            }
        });
        fileExport.add(fileExportGIF);


        //////////////////  FILE -> EXPORT -> TXT  ///////////////////////////
        fileExportTXT = new JMenuItem();
        fileExportTXT.setIcon(new ImageIcon(getClass().getResource(ICON_FILE_EXPORT_TXT_PATH)));
        fileExportTXT.setText(jgaf.l18n.Resource.getValue("menu.file.type.txt"));
        fileExportTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editorHandler.exportTXT();
            }
        });
        fileExport.add(fileExportTXT);
        
        
        
        
        file.add(fileExport);




//        //  file.addSeparator();
//
//        filePrint = new JMenuItem();
//        filePrint.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
//        filePrint.setIcon(new ImageIcon(getClass().getResource(ICON_FILE_PRINT_PATH)));
//        filePrint.setText(jgaf.l18n.Resource.getValue("menu.file.print"));
//        filePrint.addActionListener(new java.awt.event.ActionListener() {
//
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
////
////                PrinterJob job = PrinterJob.getPrinterJob();
////                job.setPrintable(new Printable() {
////                    public int print(Graphics pg,
////                                     PageFormat pf,
////                                     int pageNum) {
////                        if (pageNum > 0) {
////                            return Printable.NO_SUCH_PAGE;
////                        }
////
////                        Graphics2D g2 = (Graphics2D) pg;
////                        g2.translate(0,0);//pf.getImageableX(), pf.getImageableY());
////                        editorHandler.getCurrentEditor().getFace().paint(g2);
////                        return Printable.PAGE_EXISTS;
////                    }
////                });
////
////
////
////         boolean ok = job.printDialog();
////         if (ok) {
////             try {
////                  job.print();
////             } catch (PrinterException ex) {
////              /* The job did not successfully complete */
////             }
////         }
//
//                PrinterJob pj = PrinterJob.getPrinterJob();
//                pj.setJobName("Subscriber Details for Msisdn=");
//                pj.setCopies(1);
//                PageFormat format = pj.defaultPage();
//                format.setOrientation(PageFormat.PORTRAIT);//LANDSCAPE);
//
//                pj.setPrintable(new Printable() {
//
//                    public int print(Graphics pg,
//                                     PageFormat pf,
//                                     int pageNum) {
//                        if (pageNum > 0) {
//                            return Printable.NO_SUCH_PAGE;
//                        }
//                        Graphics2D g2 = (Graphics2D) pg;
//                        g2.translate(pf.getImageableX(), pf.getImageableY());
//                        mainFrame.paint(g2);
//                        return Printable.PAGE_EXISTS;
//                    }
//                });
//                if (pj.printDialog() == false) {
//                    return;
//                }
//                try {
//                    pj.print();
//                    //PrintUtilities.printComponent(editorHandler.getCurrentEditor().getRepresenter());
//                } catch (PrinterException ex) {
//                    Logger.getLogger(MainFrameMenu.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
//        file.add(filePrint);
//
//        //  file.addSeparator();

        //////////////////  FILE -> EXIT  ///////////////////////////
        fileExit = new JMenuItem();
        fileExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        fileExit.setIcon(new ImageIcon(getClass().getResource(ICON_FILE_EXIT_PATH)));
        fileExit.setText(jgaf.l18n.Resource.getValue("menu.file.exit"));
        fileExit.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (editorHandler.allSaved()) {
                    mainFrame.exit();
                } else {
                    String message = "Some editors still have unsaved content. Do you want to exit anyways?";
                    int answer = JOptionPane.showConfirmDialog(mainFrame,
                        message, "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (answer == JOptionPane.YES_OPTION) {
                        mainFrame.exit();
                    }
                }
            }
        });
        file.add(fileExit);


        add(file);


//
//
////    private JMenu view;
////    private JMenu transformation;
////    private JMenu simulation;
////    private JMenu tools;
////    private JMenu viewMenu;
////    private JMenu window;
//
//
//        /////////////////////////////////////////////////////
//        //////////////////  EDIT  ///////////////////////////
//        /////////////////////////////////////////////////////
//        edit = new JMenu(jgaf.l18n.Resource.getValue("menu.edit"));
//
//
//        //////////////////  EDIT -> UNDO  ///////////////////////////
//        editUndo = new JMenuItem();
//      //  editRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
//        editUndo.setIcon(new ImageIcon(getClass().getResource(ICON_EDIT_UNDO_PATH)));
//        editUndo.setText(jgaf.l18n.Resource.getValue("menu.edit.undo"));
//        editUndo.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                editorHandler.getCurrentEditor().undo();
//            }
//        });
//        edit.add(editUndo);
//
//
//        //////////////////  EDIT -> REDO  ///////////////////////////
//        editRedo = new JMenuItem();
//      //  editRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
//        editRedo.setIcon(new ImageIcon(getClass().getResource(ICON_EDIT_REDO_PATH)));
//        editRedo.setText(jgaf.l18n.Resource.getValue("menu.edit.redo"));
//        editRedo.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                editorHandler.getCurrentEditor().redo();
//            }
//        });
//        edit.add(editRedo);
//
//
//        //////////////////  EDIT -> SELECT ALL  ///////////////////////////
//        editSelectAll = new JMenuItem();
//      //  editRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
//        editSelectAll.setIcon(new ImageIcon(getClass().getResource(ICON_EDIT_SELECT_ALL_PATH)));
//        editSelectAll.setText("Select all");
//        editSelectAll.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//            //    editorsHandler.getCurrentEditor().selectAll();
//
//
//
//
//
//
//
//
////    JInternalFrame[] allframes = mainFrame.getDesktop().getAllFrames();
////    int count = allframes.length;
////    if (count == 0)
////      return;
////
////    // Determine the necessary grid size
////    int sqrt = (int) Math.sqrt(count);
////    int rows = sqrt;
////    int cols = sqrt;
////    if (rows * cols < count) {
////      cols++;
////      if (rows * cols < count) {
////        rows++;
////      }
////    }
////
////    // Define some initial values for size & location.
////    Dimension size = mainFrame.getDesktop().getSize();
////
////    int w = size.width / cols;
////    int h = size.height / rows;
////    int x = 0;
////    int y = 0;
////
////    // Iterate over the frames, deiconifying any iconified frames and then
////    // relocating & resizing each.
////    for (int i = 0; i < rows; i++) {
////      for (int j = 0; j < cols && ((i * cols) + j < count); j++) {
////        JInternalFrame f = allframes[(i * cols) + j];
////
////        if (!f.isClosed() && f.isIcon()) {
////                            try {
////                                f.setIcon(false);
////                            } catch (PropertyVetoException ex) {
////                                Logger.getLogger(MainFrameMenu.class.getName()).log(Level.SEVERE, null, ex);
////                            }
////
////
////        }
////
////        mainFrame.getDesktop().getDesktopManager().resizeFrame(f, x, y, w, h);
////        x += w;
////      }
////      y += h; // start the next row
////      x = 0;
////    }
//
//
//
//
//
//
//
//
//            }
//        });
//        edit.add(editSelectAll);
//
//
//        //////////////////  EDIT -> SELECT ALL  ///////////////////////////
////        editProperties = new JMenuItem();
////      //  editRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
////        //editProperties.setIcon(new ImageIcon(getClass().getResource(ICON_EDIT_SELECT_ALL_PATH)));
////        editProperties.setText(jgaf.l18n.Resource.getValue("menu.edit.properties"));
////        editProperties.addActionListener(new java.awt.event.ActionListener() {
////            public void actionPerformed(java.awt.event.ActionEvent evt) {
////                EditorPropertiesDialog editorPropertiesDialog = new EditorPropertiesDialog(mainFrame, true);
////                //editorsHandler.getCurrentEditor().selectAll();
////            }
////        });
////        edit.add(editProperties);
////
////
////
////        add(edit);
//        /////////////////////////////////////////////////////
//        //////////////////  VIEW  ///////////////////////////
//        /////////////////////////////////////////////////////
//        view = new JMenu(jgaf.l18n.Resource.getValue("menu.view"));
//
//
//        //////////////////  VIEW -> ZOOM IN  ///////////////////////////
//        viewZoomIn = new JMenuItem();
//      //  editRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
//        viewZoomIn.setIcon(new ImageIcon(getClass().getResource(ICON_VIEW_ZOOM_IN_PATH)));
//        viewZoomIn.setText(jgaf.l18n.Resource.getValue("menu.view.zoomIn"));
//        viewZoomIn.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                editorHandler.getCurrentEditor().zoomIn();
//            }
//        });
//        view.add(viewZoomIn);
//
//
//
//        //////////////////  VIEW -> ZOOM OUT  ///////////////////////////
//        viewZoomOut = new JMenuItem();
//      //  editRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
//        viewZoomOut.setIcon(new ImageIcon(getClass().getResource(ICON_VIEW_ZOOM_OUT_PATH)));
//        viewZoomOut.setText(jgaf.l18n.Resource.getValue("menu.view.zoomOut"));
//        viewZoomOut.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                editorHandler.getCurrentEditor().zoomOut();
//            }
//        });
//        view.add(viewZoomOut);
//
//
//        //////////////////  VIEW -> CLEAR ZOOM ///////////////////////////
//        viewClearZoom = new JMenuItem();
//      //  editRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
//        viewClearZoom.setIcon(new ImageIcon(getClass().getResource(ICON_VIEW_CLEAR_ZOOM_PATH)));
//        viewClearZoom.setText(jgaf.l18n.Resource.getValue("menu.view.clearZoom"));
//        viewClearZoom.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                editorHandler.getCurrentEditor().clearZoom();
//            }
//        });
//        view.add(viewClearZoom);
//
//
////        //////////////////  VIEW -> BEST FIT ///////////////////////////
////        viewBestFit = new JMenuItem();
////      //  editRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
////        viewBestFit.setIcon(new ImageIcon(getClass().getResource(ICON_VIEW_BEST_FIT_PATH)));
////        viewBestFit.setText("Best fit");
////        viewBestFit.addActionListener(new java.awt.event.ActionListener() {
////            public void actionPerformed(java.awt.event.ActionEvent evt) {
////               // ((StateDiagramEditor) editorsHandler.getCurrentEditor()).centerAndScaleGraphics(true);
////            }
////        });
////        view.add(viewBestFit);
//
////        //////////////////  VIEW -> LAYOUTS ///////////////////////////
////        viewLayouts = new JMenu("Layouts");
////
////        //////////////////  VIEW -> LAYOUTS -> GEM ///////////////////////////
////        viewLayoutsGEM = new JMenuItem();
////        viewLayoutsGEM.setIcon(new ImageIcon(getClass().getResource(ICON_VIEW_LAYOUTS_GEM_PATH)));
////        viewLayoutsGEM.setText("GEM");
////        viewLayoutsGEM.addActionListener(new java.awt.event.ActionListener() {
////            public void actionPerformed(java.awt.event.ActionEvent evt) {
////         //       ((StateDiagramEditor) editorsHandler.getCurrentEditor()).applyGEMLayout();
////            }
////        });
////        viewLayouts.add(viewLayoutsGEM);
////
////        view.add(viewLayouts);
//
//
//
//
//        //////////////////  VIEW -> SHOW LABELS ///////////////////////////
////        viewShowLabels = new JCheckBoxMenuItem();
////      //  editRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
////        //iewShowLabels.setIcon(new ImageIcon(getClass().getResource(ICON_VIEW_BEST_FIT_PATH)));
////        viewShowLabels.setText("Show labels");
////        viewShowLabels.setSelected(true);
////        //boolean show = true;
//////        if(editorsHandler.getCurrentEditor() != null) {
//////            show = editorsHandler.getCurrentEditor().areLabelsVisible();
//////            viewShowLabels.setSelected(true);
//////        } else {
////        //    viewShowLabels.setEnabled(false);
//////        }
////        viewShowLabels.addActionListener(new java.awt.event.ActionListener() {
////            public void actionPerformed(java.awt.event.ActionEvent evt) {
////                ((StateDiagramEditor) editorsHandler.getCurrentEditor()).showLabels(!((StateDiagramEditor) editorsHandler.getCurrentEditor()).areLabelsVisible());
////            }
////        });
////        view.add(viewShowLabels);
//
//
//
//        add(view);
//



        /////////////////////////////////////////////////////
        //////////////////  TRANSFORMATION  ///////////////////////////
        /////////////////////////////////////////////////////
        transformation = new JMenu(jgaf.l18n.Resource.getValue("menu.transformation"));
        add(transformation);
        createTransformationSubmenus();



        /////////////////////////////////////////////////////
        //////////////////  ANALYSIS  ///////////////////////////
        /////////////////////////////////////////////////////
        analysis = new JMenu(jgaf.l18n.Resource.getValue("menu.analysis"));
        add(analysis);
        createAnalysisSubmenus();


        /////////////////////////////////////////////////////
        //////////////////  SIMULATION  ///////////////////////////
        /////////////////////////////////////////////////////
        simulation = new JMenu(jgaf.l18n.Resource.getValue("menu.simulation"));
        add(simulation);
        createSimulationSubmenus();




        /////////////////////////////////////////////////////
        //////////////////  WINDOW  ///////////////////////////
        /////////////////////////////////////////////////////
        window = new JMenu(jgaf.l18n.Resource.getValue("menu.window"));
        add(window);
        window.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                generateWindowSubmenu();
            }
        });




        /////////////////////////////////////////////////////
        //////////////////  HELP  ///////////////////////////
        /////////////////////////////////////////////////////
        help = new JMenu(jgaf.l18n.Resource.getValue("menu.help"));
        add(help);

        helpAbout = new JMenuItem();
        helpAbout.setText(jgaf.l18n.Resource.getValue("menu.help.about"));
        helpAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AboutBoxDialog aboutBox = new AboutBoxDialog(mainFrame, true);
            }
        });
        help.add(helpAbout);





    }

    private void generateRecentFilesSubMenu() {
        fileOpenRecent.removeAll();
        Stack<File> stack = PropertiesHandler.getInstance().getRecentFiles();
        if (!stack.isEmpty()) {
            ListIterator<File> it = stack.listIterator(stack.size());
            while (it.hasPrevious()) {
                final File recentFile = it.previous();
                JMenuItem item = new JMenuItem(recentFile.getName());
                item.addActionListener(new java.awt.event.ActionListener() {

                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        try {
                            editorHandler.open(recentFile);

                        } catch (NoSuchEditorException ex) {
                            showWarningDialog(jgaf.l18n.Resource.getValue("exception.file.not-supported"),
                                              jgaf.l18n.Resource.getValue("exception.title.warning"));
                        } catch (DocumentException ex) {
                            showWarningDialog(jgaf.l18n.Resource.getValue("exception.file.not-supported"),
                                              jgaf.l18n.Resource.getValue("exception.title.warning"));
                        } catch (JgafFileException ex) {
                            showWarningDialog(jgaf.l18n.Resource.getValue("xception.file.cannot-open") + " " + ex,
                                              jgaf.l18n.Resource.getValue("exception.title.warning"));
                        }
                    }
                });
                fileOpenRecent.add(item);
            }
        }
    }

    
    
    private void generateWindowSubmenu() {
        final EditorRegister register = editorHandler.getEditorRegister();
        
        window.removeAll();        
        for (final Editor editor : editorHandler.getEditors()) {           
            EditorDescriptor descriptor = null;
            try {
                descriptor = register.getDescriptorById(editor.getId());
            } catch (NoSuchEditorException ex) {
                showWarningDialog("Incorrect editor register", "error");
            }
            
            JCheckBoxMenuItem item = new JCheckBoxMenuItem();
            item.setSelected(false);
            String name = descriptor.getName() + ": " + editor.getName();
            item.setText(name);
            if(editorHandler.isCurrentEditor(editor)) {
                item.setSelected(true);
            }
            item.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    //editorHandler.showEditorWindow(editor);
                    editorHandler.putEditorToFront(editor);
                }
            });
            window.add(item);
        }
        window.addSeparator();

        final ProcedureHandler procedureHandler = Environment.getInstance().getProcedureHandler();
        for (final Procedure procedure  : procedureHandler.getProcedures()) {
            JCheckBoxMenuItem item = new JCheckBoxMenuItem();
            item.setSelected(false);
            String name = procedure.getNameID();
            item.setText(name);
            if(procedureHandler.isCurrentProcedure(procedure)) {
                item.setSelected(true);
            }
            item.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    procedureHandler.putProcedureToFront(procedure);
                }
            });
            window.add(item);
        }
        window.addSeparator();

        JMenu mShow = new JMenu("Show");
        window.add(mShow);

        JMenuItem showAll = new JMenuItem();
        showAll.setText("All");
        showAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FrameTool.organizeFrames(FrameTool.frameUnion(
                        editorHandler.getEditorFrames(editorHandler.getEditors()),
                        procedureHandler.getProcedureFrames(procedureHandler.getProcedures())),
                        mainFrame.getDesktop());
            }
        });
        mShow.add(showAll);

        JMenuItem showAllEditors = new JMenuItem();
        showAllEditors.setText("Editors");
        showAllEditors.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editorHandler.showAllEditors();
                procedureHandler.minimizeAllProcedures();
            }
        });
        mShow.add(showAllEditors);

        JMenuItem showAllProcedures = new JMenuItem();
        showAllProcedures.setText("Procedures");
        showAllProcedures.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                procedureHandler.showAllProcedures();
                editorHandler.minimizeAllEditors();
            }
        });
        mShow.add(showAllProcedures);









        JMenu mClose = new JMenu("Close");
        window.add(mClose);

        JMenuItem closeAll = new JMenuItem();
        closeAll.setText("All");
        closeAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editorHandler.closeAllEditors();
                procedureHandler.closeAllProcedures();
            }
        });
        mClose.add(closeAll);

        JMenuItem closeAllEditors = new JMenuItem();
        closeAllEditors.setText("Editors");
        closeAllEditors.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editorHandler.closeAllEditors();
            }
        });
        mClose.add(closeAllEditors);

        JMenuItem closeAllProcedures = new JMenuItem();
        closeAllProcedures.setText("Procedures");
        closeAllProcedures.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                procedureHandler.closeAllProcedures();
            }
        });
        mClose.add(closeAllProcedures);













        JMenuItem hideAll = new JMenuItem();
        hideAll.setText("Minimize all");

        hideAll.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                        FrameTool.minimizeFrames(FrameTool.frameUnion(
                        editorHandler.getEditorFrames(editorHandler.getEditors()),
                        procedureHandler.getProcedureFrames(procedureHandler.getProcedures())),
                        mainFrame.getDesktop());

                editorHandler.nullCurrentEditor();
                procedureHandler.setCurrentProcedure(null);
            }
        });
        window.add(hideAll);
    }





        private void generateNewFileSubMenu() {
        fileNew.removeAll();
        final EditorRegister register = editorHandler.getEditorRegister();
        for (final EditorDescriptor descriptor: register.getDescriptorList()) {
            JCheckBoxMenuItem item = new JCheckBoxMenuItem();
            item.setSelected(false);
            String name = descriptor.getName();//register.getEditorName(id);
            item.setText(name);
            item.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    editorHandler.newEditor(descriptor);
                }
            });
            fileNew.add(item);
        }
    }

    private void showWarningDialog(String message,
                                   String title) {
        JOptionPane.showMessageDialog(mainFrame, message, title, JOptionPane.WARNING_MESSAGE);
    }

    private void createTransformationSubmenus() {
        List<ProcedureDescriptor> precedures = Environment.getInstance().getProcedureHandler().getProcedureRegister().getTransformations();
        for (final ProcedureDescriptor procedureDescriptor : precedures) {
            JMenuItem item = new JMenuItem();
            item.setText(procedureDescriptor.getName());
            item.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        ProcedureDialog procedureDialog = new ProcedureDialog(mainFrame, procedureDescriptor, false);
                    } catch (NoSuchEditorException ex) {
                        showWarningDialog("Incorrect procedure register", "error");
                    }
                }
            });
            transformation.add(item);
        }
    }



    private void createAnalysisSubmenus() {
        List<ProcedureDescriptor> precedures = Environment.getInstance().getProcedureHandler().getProcedureRegister().getAnalyses();
        for (final ProcedureDescriptor procedureDescriptor : precedures) {
            JMenuItem item = new JMenuItem();
            item.setText(procedureDescriptor.getName());
            item.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        ProcedureDialog procedureDialog = new ProcedureDialog(mainFrame, procedureDescriptor, false);
                    } catch (NoSuchEditorException ex) {
                        showWarningDialog("Incorrect procedure register", "error");
                    }
                }
            });
            analysis.add(item);
        }
    }




    private void createSimulationSubmenus() {
        List<ProcedureDescriptor> precedures = Environment.getInstance().getProcedureHandler().getProcedureRegister().getSimulations();
        for (final ProcedureDescriptor procedureDescriptor : precedures) {
            JMenuItem item = new JMenuItem();
            item.setText(procedureDescriptor.getName());
            item.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        ProcedureDialog procedureDialog = new ProcedureDialog(mainFrame, procedureDescriptor, false);
                    } catch (NoSuchEditorException ex) {
                        showWarningDialog("Incorrect procedure register", "error");
                    }
                }
            });
            simulation.add(item);
        }
    }















}
