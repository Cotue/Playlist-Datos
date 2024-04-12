package Interfaz;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import Logica.CustomServer;
import Logica.InventarioCanciones;
import Logica.ReproductorMusica;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class MainWindow{
    private static JFrame mainFrame;
    public static ReproductorMusica reproductor;

    public static JButton playButton;



    public MainWindow() {
        getInitialLists();
        prepareGUI();
        reproductor = new ReproductorMusica(InventarioCanciones.listaCanciones);
    }
    public static void main(String[] args) throws IOException {

        MainWindow mainWindow = new MainWindow();
        //mainWindow.CreateStartCommunityPanel();
        mainWindow.CreateSidePanel();
        mainWindow.CreateSongPanel();
        mainWindow.CreateControlPanel();
        mainFrame.setVisible(true);

    }

    private void getInitialLists() {
        try {
            InventarioCanciones.obtenerListaCanciones();

            System.out.println(InventarioCanciones.listaCanciones);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println( InventarioCanciones.));
        //InventarioCanciones.
    }

    private void prepareGUI() {

        mainFrame = new JFrame("Reproductor");
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setSize(1200,800);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setLocationRelativeTo(null);
        //mainFrame.setResizable(false);
        System.out.println("2");
        System.out.println(InventarioCanciones.listaCanciones);



    }
    private void CreateSidePanel() throws IOException {
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(Color.blue);
        //artistPanel.setBounds(0,100,300,700);

        sidePanel.setLayout(new BorderLayout());
        sidePanel.setPreferredSize(new Dimension(300,800));

        CreateStartCommunityPanel(sidePanel);
        CreateArtistsPanel(sidePanel);

        mainFrame.add(sidePanel, BorderLayout.WEST);


    }
    private void CreateStartCommunityPanel(JPanel sidePanel) {
        JPanel startCommunityPanel = new JPanel();
        startCommunityPanel.setBackground(Color.decode("#0B121E"));
        startCommunityPanel.setBorder(BorderFactory.createMatteBorder(3, 3, 0, 3, Color.decode("#1B222E")));

        //startCommunityPanel.setBounds(0,0,300,100);
        //startCommunityPanel.setLayout(new BorderLayout());


        JToggleButton toggleCommunityServer = new JToggleButton("Iniciar servidor");

        CustomServer newServer = new CustomServer();
        ItemListener itemListener = new ItemListener() {

            // itemStateChanged() method is invoked automatically
            // whenever you click or unclick on the Button.
            public void itemStateChanged(ItemEvent itemEvent)
            {

                // event is generated in button
                int state = itemEvent.getStateChange();

                // if selected print selected in console
                if (state == ItemEvent.SELECTED) {
                    toggleCommunityServer.setText("Detener servidor");
                    newServer.OpenServer();

                }
                else {
                    toggleCommunityServer.setText("Iniciar servidor");
                    // else print deselected in console
                    try {
                        newServer.closeServerSocket();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        toggleCommunityServer.addItemListener(itemListener);

        toggleCommunityServer.setSize(150,50);
        toggleCommunityServer.setLocation(75,25);
        startCommunityPanel.add(toggleCommunityServer);

        toggleCommunityServer.addActionListener(e -> {
            //System.out.println( InventarioCanciones.obtenerListaCanciones());

        });


        sidePanel.add(startCommunityPanel, BorderLayout.NORTH);
    }
    private void CreateArtistsPanel(JPanel sidePanel) throws IOException {
        JPanel artistsPanel = new JPanel();
        artistsPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#1B222E"), 3));//.setBackground(Color.pink);
        artistsPanel.setBackground(Color.decode("#0B121E"));
        artistsPanel.setLayout(new GridBagLayout());

        JLabel titleArtistsSection = new JLabel("Artistas", JLabel.CENTER);
        titleArtistsSection.setFont(new Font("Arial", Font.PLAIN, 40));
        titleArtistsSection.setForeground(Color.decode("#cacaca"));
        //titleArtistsSection.setLocation(50,400);

        artistsPanel.add(titleArtistsSection);



        InventarioCanciones.listaArtistas.artistsRows(artistsPanel);

        JScrollPane scrollPane = new JScrollPane(artistsPanel);
        scrollPane.setBackground(Color.decode("#0B121E"));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setBackground(Color.decode("#1a1a1a"));
        sidePanel.add(scrollPane, BorderLayout.CENTER);
    }
    private void CreateSongPanel() throws IOException {
        JPanel songPanel = new JPanel();
        //songPanel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));//.setBackground(Color.yellow);
        songPanel.setBackground(Color.decode("#0B121E"));
        songPanel.setLayout(new GridBagLayout());
        //songPanel.setBounds(300,0,900,600);
        //songPanel.setPreferredSize(new Dimension(900,400));

        InventarioCanciones.listaCanciones.songRows(songPanel);

        JScrollPane scrollPane = new JScrollPane(songPanel);
        scrollPane.setBackground(Color.decode("#0B121E"));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setBackground(Color.decode("#1a1a1a"));
        //sidePanel.add(scrollPane, BorderLayout.CENTER);

        mainFrame.add(scrollPane, BorderLayout.CENTER);

    }
    private void CreateControlPanel() {
        JPanel controlPanel = new JPanel();
        //controlPanel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));//.setBackground(Color.green);
        controlPanel.setBackground(Color.decode("#9B77D7"));
        controlPanel.setLayout(new BorderLayout());
        //controlPanel.setBounds(300,600,900,200);
        controlPanel.setPreferredSize(new Dimension(100,200));



        controls(controlPanel);
        slider(controlPanel);


        mainFrame.add(controlPanel, BorderLayout.SOUTH);

    }

    private void controls (JPanel controlPanel) {
        JPanel controlsJP = new JPanel();
        controlsJP.setPreferredSize(new Dimension(300,400));

        controlsJP = new JPanel();
        controlsJP.setBounds(0, 435, 100, 80);
        controlsJP.setLayout(new BoxLayout(controlsJP, BoxLayout.LINE_AXIS));
        controlsJP.setBackground(null);

        // previous button
        JButton prevButton = new JButton(loadImage("./src/Interfaz/assets/previous.png"));
        prevButton.setBorderPainted(false);
        prevButton.setBackground(null);
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // go to the previous song
                reproductor.anteriorCancion();
                System.out.println(InventarioCanciones.listaCanciones.display());
            }
        });
        controlsJP.add(prevButton);

        // play button
        playButton = new JButton(loadImage("./src/Interfaz/assets/play.png"));
        playButton.setBorderPainted(false);
        playButton.setBackground(null);
        JPanel finalControlsJP = controlsJP;
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // toggle off play button and toggle on pause button
                enablePauseButtonDisablePlayButton(finalControlsJP);

                // play or resume song
                try {
                    reproductor.resumirCancion();
                } catch(Exception exception){
                    reproductor.reproducirPrimeraCancion();
                }

            }
        });
        controlsJP.add(playButton);

        // pause button
        JButton pauseButton = new JButton(loadImage("./src/Interfaz/assets/pause.png"));
        pauseButton.setBorderPainted(false);
        pauseButton.setBackground(null);
        pauseButton.setVisible(false);
        JPanel finalControlsJP1 = controlsJP;
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // toggle off pause button and toggle on play button
                enablePlayButtonDisablePauseButton(finalControlsJP1);

                // pause the song
                reproductor.pausarCancion();
            }
        });
        controlsJP.add(pauseButton);

        // next button
        JButton nextButton = new JButton(loadImage("./src/Interfaz/assets/next.png"));
        nextButton.setBorderPainted(false);
        nextButton.setBackground(null);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // go to the next song
                //musicPlayer.nextSong();
                reproductor.siguienteCancion();
            }
        });
        controlsJP.add(nextButton);

        //add(controlsJP);

        controlPanel.add(controlsJP, BorderLayout.WEST);
    }

    private void enablePlayButtonDisablePauseButton(JPanel controlsJP) {
        // retrieve reference to play button from playbackBtns panel
        JButton playButton = (JButton) controlsJP.getComponent(1);
        JButton pauseButton = (JButton) controlsJP.getComponent(2);

        // turn on play button
        playButton.setVisible(true);
        playButton.setEnabled(true);

        // turn off pause button
        pauseButton.setVisible(false);
        pauseButton.setEnabled(false);
    }

    public void enablePauseButtonDisablePlayButton(JPanel controlsJP){
        // retrieve reference to play button from playbackBtns panel
        JButton playButton = (JButton) controlsJP.getComponent(1);
        JButton pauseButton = (JButton) controlsJP.getComponent(2);

        // turn off play button
        playButton.setVisible(false);
        playButton.setEnabled(false);

        // turn on pause button
        pauseButton.setVisible(true);
        pauseButton.setEnabled(true);
    }

    private ImageIcon loadImage(String imagePath){
        try{
            // read the image file from the given path
            BufferedImage image = ImageIO.read(new File(imagePath));

            // returns an image icon so that our component can render the image
            return new ImageIcon(image);
        }catch(Exception e){
            e.printStackTrace();
        }

        // could not find resource
        return null;
    }


    private void slider (JPanel controlPanel){
        // playback slider
        JPanel sliderJP = new JPanel();
        sliderJP.setLayout(new BorderLayout());
        //JLabel
        sliderJP.setBackground(Color.decode("#9B77D7"));
        JSlider playbackSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        playbackSlider.setBounds(600, 365, 100, 40);
        playbackSlider.setBackground(null);
        playbackSlider.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // when the user is holding the tick we want to the pause the song
                //musicPlayer.pauseSong();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // when the user drops the tick
                JSlider source = (JSlider) e.getSource();

                // get the frame value from where the user wants to playback to
                int frame = source.getValue();

                // update the current frame in the music player to this frame
                //musicPlayer.setCurrentFrame(frame);

                // update current time in milli as well
                //musicPlayer.setCurrentTimeInMilli((int) (frame / (2.08 * musicPlayer.getCurrentSong().getFrameRatePerMilliseconds())));

                // resume the song
                //musicPlayer.playCurrentSong();

                // toggle on pause button and toggle off play button
                //enablePauseButtonDisablePlayButton();
            }
        });


        sliderJP.add(playbackSlider, BorderLayout.CENTER);
        controlPanel.add(sliderJP, BorderLayout.CENTER);
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
