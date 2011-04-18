/*
 * SortingDemoView.java
 */
package com.serb.sortingdemo;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.Task;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import org.apache.log4j.Logger;

/**
 * The application's main frame.
 */
public class SortingDemoView extends FrameView {

    private final static String SEPARATOR_IN_FILE_PROPERTY = "fileToLoadSeparator";
    private final static String IS_STRING_NUMBER_PATTERN = "((-|\\+)?[0-9]+(\\.[0-9]+)?)+";
    private static Logger log = Logger.getLogger(SortingDemoView.class);

    public SortingDemoView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = SortingDemoApp.getApplication().getMainFrame();
            aboutBox = new SortingDemoAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        SortingDemoApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        mainSortTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        buttonSort = new javax.swing.JButton();
        fileNameLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        SortMenu = new javax.swing.JMenu();
        sortMenuItem = new javax.swing.JMenuItem();
        loadFromFileMenuItem = new javax.swing.JMenuItem();
        fillTableRandomMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        mainSortTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {new Integer(0), new Integer(0), new Integer(0), new Integer(0)},
                {new Integer(0), new Integer(0), new Integer(0), new Integer(0)},
                {new Integer(0), new Integer(0), new Integer(0), new Integer(0)},
                {new Integer(0), new Integer(0), new Integer(0), new Integer(0)}
            },
            new String [] {
                "", "", "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        mainSortTable.setCellSelectionEnabled(true);
        mainSortTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mainSortTable.setDragEnabled(true);
        mainSortTable.setName("mainSortTable"); // NOI18N
        mainSortTable.getTableHeader().setResizingAllowed(false);
        mainSortTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(mainSortTable);

        jPanel1.setName("jPanel1"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.serb.sortingdemo.SortingDemoApp.class).getContext().getActionMap(SortingDemoView.class, this);
        buttonSort.setAction(actionMap.get("doSort")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.serb.sortingdemo.SortingDemoApp.class).getContext().getResourceMap(SortingDemoView.class);
        buttonSort.setText(resourceMap.getString("buttonSort.text")); // NOI18N
        buttonSort.setToolTipText(resourceMap.getString("buttonSort.toolTipText")); // NOI18N
        buttonSort.setName("buttonSort"); // NOI18N
        buttonSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doSortAction(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(buttonSort, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(303, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(buttonSort, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(27, 27, 27))
        );

        fileNameLabel.setText(resourceMap.getString("fileNameLabel.text")); // NOI18N
        fileNameLabel.setName("fileNameLabel"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(33, 33, 33))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(60, Short.MAX_VALUE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(fileNameLabel)
                        .addContainerGap(435, Short.MAX_VALUE))))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(fileNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        menuBar.setName("mainMenuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        SortMenu.setText(resourceMap.getString("SortMenu.text")); // NOI18N
        SortMenu.setName("SortMenu"); // NOI18N

        sortMenuItem.setAction(actionMap.get("doSort")); // NOI18N
        sortMenuItem.setText(resourceMap.getString("sortMenuItem.text")); // NOI18N
        sortMenuItem.setName("sortMenuItem"); // NOI18N
        SortMenu.add(sortMenuItem);

        loadFromFileMenuItem.setAction(actionMap.get("loadFromFile")); // NOI18N
        loadFromFileMenuItem.setText(resourceMap.getString("loadFromFileMenuItem.text")); // NOI18N
        loadFromFileMenuItem.setName("loadFromFileMenuItem"); // NOI18N
        SortMenu.add(loadFromFileMenuItem);

        fillTableRandomMenuItem.setAction(actionMap.get("fillTableRandom")); // NOI18N
        fillTableRandomMenuItem.setText(resourceMap.getString("fillTableRandomMenuItem.text")); // NOI18N
        fillTableRandomMenuItem.setName("fillTableRandomMenuItem"); // NOI18N
        SortMenu.add(fillTableRandomMenuItem);

        menuBar.add(SortMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addGap(141, 141, 141)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(statusAnimationLabel)
                        .addContainerGap())))
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void doSortAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doSortAction
    }//GEN-LAST:event_doSortAction

    @Action
    public void doSort() {
        System.out.println("doSort() method");
        bubbleSort();
    }

    /**
     * Bubble sort for main table O(n*n). 
     * Used only for academic goals
     */
    private void bubbleSort() {
        System.out.println("SortingDemoView bubbleSort new version");
        /*int[][] arrToSort = new int[][]{
            {16, 15, 14, 13},
            {6, 10, 11, 12, 15, 17},
            {4, 1, 2, 3},
            {9, 8, 7, 5},                        
            {17, 19, 20, 18},};
        ArrayUtil.printValues(arrToSort);
        ArrayUtil.bubbleSort(arrToSort);        
        ArrayUtil.printValues(arrToSort);*/

        int rowCount = mainSortTable.getRowCount();
        int columnCount = mainSortTable.getColumnCount();
           
        //сортируем пузырьком
        for (int n = 0; n < rowCount * columnCount; n++) {
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    if ((j != columnCount-1) && ((Integer) mainSortTable.getValueAt(i, j + 1) <
                            (Integer) mainSortTable.getValueAt(i, j))) {
                        swapTable(i, j+1, i, j);                        
                    }
                }
                //сравниваем последний элемент текущей строки
                //с перым элементом следующей
                if ((i != rowCount-1) && (Integer) mainSortTable.getValueAt(i + 1, 0) <
                        (Integer) mainSortTable.getValueAt(i, rowCount - 1)) {
                    swapTable(i+1, 0, i, rowCount-1);                    
                }
            }
        }        
    }

    private void swapTable(int oldRowIndex, int oldColumnIndex, int newRowIndex, int newColumnIndex) {
        System.out.println("SortingDemoView swapTable() oldRowIndex: " +
                oldRowIndex + " " + "oldColumnIndex: " + oldColumnIndex + " " + "+newColumnIndex: " + newColumnIndex);
        Integer tmp = (Integer) mainSortTable.getValueAt(oldRowIndex, oldColumnIndex);
        mainSortTable.setValueAt(mainSortTable.getValueAt(newRowIndex, newColumnIndex), oldRowIndex, oldColumnIndex);
        mainSortTable.setValueAt(tmp, newRowIndex, newColumnIndex);
    }

    //TODO: implement quick sort for table
    private void quickSort() {
    }

    @Action
    public Task loadFromFile() {
        JFileChooser fc = createFileChooser("openFileChooser");
        int option = fc.showOpenDialog(getFrame());
        Task task = null;
        if (JFileChooser.APPROVE_OPTION == option) {
            loadFromFile(fc.getSelectedFile());
        }
        return new LoadFromFileTask(getApplication());
    }

    /**
     * Load values from file to main table of app.
     * @param fileToLoad
     */
    private void loadFromFile(File fileToLoad) {
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader(fileToLoad));
            String line;
            int columnIndex = 0;
            int rowIndex = 0;
            int columnCount = mainSortTable.getColumnCount();
            int rowCount = mainSortTable.getRowCount();
            while ((line = input.readLine()) != null) {
                if (rowIndex >= rowCount) {
                    System.err.print("File has more lines than needed. Line no "
                            + rowIndex + " " + line + "is explicit.");
                    return;
                }
                columnIndex = 0;
                String[] values = line.trim().split(getResourceMap().getString(SEPARATOR_IN_FILE_PROPERTY));
                if (columnCount != values.length) {
                    System.err.print("Line " + line + "does not match");
                    return;
                }
                for (; columnIndex < columnCount; columnIndex++) {
                    if (isStringNumber(values[columnIndex].trim())) {
                        mainSortTable.setValueAt(Integer.parseInt(values[columnIndex].trim()), rowIndex, columnIndex);
                    } else {
                        System.err.print("Line " + line + "contains wrong number: " + values[columnIndex]);
                        return;
                    }
                }

                rowIndex++;
            }
            fileNameLabel.setText(fileToLoad.getAbsolutePath());
        } catch (IOException ex) {
            //Logger.getLogger(SortingDemoView.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    /**
     * Check using String that it contains only number. Regular
     * expression used
     *
     * @param strToCheck
     * @return
     */
    private boolean isStringNumber(String strToCheck) {
        return strToCheck.matches(IS_STRING_NUMBER_PATTERN);
    }

    /**
     * Create swing JFileChooser. Set current directory for chooser.
     * @param name will set in dialog title
     * @return created JFileChooser
     */
    private JFileChooser createFileChooser(String name) {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle(getResourceMap().getString(name + ".dialogTitle"));
        String textFilesDesc = getResourceMap().
                getString("txtFileExtensionDescription");
        fc.setFileFilter(new TextFileFilter(textFilesDesc));
        fc.setCurrentDirectory(new File(".").getAbsoluteFile());
        return fc;
    }

    /** This is a substitute for FileNameExtensionFilter, which is
     * only available on Java SE 6.
     */
    private static class TextFileFilter extends FileFilter {

        private final String description;

        TextFileFilter(String description) {
            this.description = description;
        }

        @Override
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            String fileName = f.getName();
            int i = fileName.lastIndexOf('.');
            if ((i > 0) && (i < (fileName.length() - 1))) {
                String fileExt = fileName.substring(i + 1);
                if ("txt".equalsIgnoreCase(fileExt)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }

    private class LoadFromFileTask extends org.jdesktop.application.Task<Object, Void> {

        LoadFromFileTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to LoadFromFileTask fields, here.
            super(app);
        }

        @Override
        protected Object doInBackground() {
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
            return null;  // return your result
        }

        @Override
        protected void succeeded(Object result) {
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
        }
    }

    private void toDelete(Integer value) {
        System.out.print(value);
    }

    @Action
    public void fillTableRandom() {
        //System.out.println("fillTableRandom()");
        log.debug("Start");
        int sizeToGenerate = mainSortTable.getColumnCount() * mainSortTable.getRowCount();
        Random rand = new Random();
        Set<Integer> setToGenerate = new LinkedHashSet<Integer>();
        while (setToGenerate.size() < sizeToGenerate) {        
            setToGenerate.add(rand.nextInt(sizeToGenerate));
        }
        //System.out.println("fillTableRandom() setToGenerate.size:"+setToGenerate.size());
        Iterator<Integer> iterSetToGenerate = setToGenerate.iterator();
        for (int rowCount = 0; rowCount <  mainSortTable.getRowCount();rowCount++) {
            for (int columnCount = 0; columnCount <  mainSortTable.getColumnCount();columnCount++) {
                if (iterSetToGenerate.hasNext()) {
                    mainSortTable.setValueAt(iterSetToGenerate.next(), rowCount, columnCount);
                }

            }
        }               
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu SortMenu;
    private javax.swing.JButton buttonSort;
    private javax.swing.JLabel fileNameLabel;
    private javax.swing.JMenuItem fillTableRandomMenuItem;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem loadFromFileMenuItem;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTable mainSortTable;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JMenuItem sortMenuItem;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
}
