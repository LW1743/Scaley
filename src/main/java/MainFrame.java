import Actions.ExportFile;
import Actions.LoadFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MainFrame extends JFrame implements ActionListener, Runnable {
    private JPanel mainPanel;
    private JPanel settingsPanel;
    private JPanel previewPanel;
    private JButton startButton;


    Thread mainThread = new Thread(this);

    String selectedFilePath = "";

    PreviewRenderer previewRenderer;

    BufferedImage processedImage;

    JMenuBar jMenuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem loadFileButton = new JMenuItem("Load File");
    JMenuItem exportFileButton = new JMenuItem("Export File");

    public MainFrame() {
        setTitle("Scaley - the buggy image scaler");
        setSize(new Dimension(1280, 720));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setBackgroundColors();
        createMenuBar();
        setJMenuBar(jMenuBar);

        setVisible(true);
        setContentPane(mainPanel);

        mainThread.start();
        loadFileButton.addActionListener(this);
        startButton.addActionListener(this);
        exportFileButton.addActionListener(this);

    }

    private void createMenuBar() {
        fileMenu.add(loadFileButton);
        fileMenu.add(exportFileButton);
        jMenuBar.add(fileMenu);
    }

    private void setBackgroundColors() {
        mainPanel.setBackground(Color.decode("#45494e"));
        previewPanel.setBackground(Color.decode("#45494e"));
        settingsPanel.setBackground(Color.decode("#45494e"));
        jMenuBar.setBackground(Color.decode("#ffffff"));
    }

    @Override
    public void run() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == loadFileButton) {
            LoadFile loadFile = new LoadFile();
            selectedFilePath = loadFile.filePath;
            previewRenderer.repaint();
        }

        if(e.getSource() == startButton) {
            if(!selectedFilePath.isEmpty()) {
                ImageProcessor imageProcessor = new ImageProcessor(selectedFilePath);
                try {
                    processedImage = imageProcessor.imageScaler(33);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        if(e.getSource() == exportFileButton) {
            ExportFile exportFile = new ExportFile();
            try {
                exportFile.saveImage(processedImage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    private void createUIComponents() {
        previewRenderer = new PreviewRenderer(this);
        previewPanel = previewRenderer;

    }
}
