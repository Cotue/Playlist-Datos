package Interfaz;
import javax.swing.*;
import java.awt.*;
import Logica.CustomServer;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class MainWindow{
    private JFrame mainFrame;
    private JPanel startCommunityPanel;
    private JPanel artistPanel;
    private JPanel songPanel;
    private JPanel controlPanel;


    public MainWindow() {
        prepareGUI();
    }
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        //mainWindow.CreateStartCommunityPanel();
        mainWindow.CreateArtistPanel();
        mainWindow.CreateSongPanel();
        mainWindow.CreateControlPanel();

    }

    private void prepareGUI() {

        mainFrame = new JFrame("Reproductor");
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setSize(1200,800);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);

        mainFrame.setVisible(true);


        artistPanel = new JPanel();
        songPanel = new JPanel();
        controlPanel = new JPanel();


        artistPanel.setBackground(Color.blue);
        songPanel.setBackground(Color.green);
        controlPanel.setBackground(Color.yellow);



    }
    private void CreateStartCommunityPanel() {
        startCommunityPanel = new JPanel();
        startCommunityPanel.setBackground(Color.red);
        //startCommunityPanel.setBounds(0,0,300,100);
        //startCommunityPanel.setLayout(new BorderLayout());


        JButton btnOpenCommunity = new JButton("Open Community");
        btnOpenCommunity.setSize(150,50);
        btnOpenCommunity.setLocation(75,25);
        startCommunityPanel.add(btnOpenCommunity);

        btnOpenCommunity.addActionListener(e -> {
            CustomServer newServer = new CustomServer();
            newServer.OpenServer();
        });


        mainFrame.add(startCommunityPanel);
    }
    private void CreateArtistPanel() {
        artistPanel = new JPanel();
        artistPanel.setBackground(Color.blue);
        //artistPanel.setBounds(0,100,300,700);
        artistPanel.setPreferredSize(new Dimension(300,800));

        // Label for the artists section
        JLabel titleArtistsSection = new JLabel("siuuuuuuuu");
        titleArtistsSection.setLocation(50,400);

        artistPanel.add(titleArtistsSection);

        mainFrame.add(artistPanel, BorderLayout.WEST);

    }
    private void CreateSongPanel() {
        songPanel = new JPanel();
        songPanel.setBackground(Color.yellow);
        //songPanel.setBounds(300,0,900,600);
        songPanel.setPreferredSize(new Dimension(900,600));

        mainFrame.add(songPanel, BorderLayout.CENTER);

    }
    private void CreateControlPanel() {
        controlPanel = new JPanel();
        controlPanel.setBackground(Color.green);
        //controlPanel.setBounds(300,600,900,200);
        controlPanel.setPreferredSize(new Dimension(100,200));

        mainFrame.add(controlPanel, BorderLayout.SOUTH);

    }














//    private void addGuiComponents() {
//        addToolBar();
//    }
//
//    private void addToolBar() {
//        JToolBar toolBar = new JToolBar();
//        toolBar.setBounds(0, 0, getWidth(), 20);
//
//
//    }
}
