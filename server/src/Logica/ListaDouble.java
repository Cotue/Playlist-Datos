package Logica;

import Interfaz.MainWindow;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


public class ListaDouble {
    class MP3File {
        private File file;
        private int value;

        public MP3File(File file, int value) {
            this.file = file;
            this.value = value;
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    class Node {
        MP3File data;
        Node previous;
        Node next;

        public Node(MP3File data) {
            this.data = data;
            this.previous = null;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;


    public ListaDouble() {
        this.head = null;
        this.tail = null;
    }

    public ListaDouble(File[] files) throws IOException {
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".mp3")) {
                insertAtEnd(new MP3File(file, 0)); // Inicializando el valor en 0
            }
        }
        //listaCircular.print();
        //InventarioCanciones.setListaArtistas(listaCircular);
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void insertAtEnd(MP3File data) throws IOException {
        Node newNode = new Node(data);
        String metaData = getMetadata(newNode).getFirst(FieldKey.ARTIST);
        InventarioCanciones.listaArtistas.insertInCircular(metaData, newNode);
        //add(newNode);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }

    }

    public void add(Node cancion) throws IOException {
        Node newNode = cancion;
        String metaData = getMetadata(newNode).getFirst(FieldKey.ARTIST);
        //System.out.println(metaData);
        //InventarioCanciones.listaArtistas.insertInCircular(metaData, newNode);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
    }

    public void sortDescending() {
        if (isEmpty() || head.next == null) {
            return; // La lista está vacía o solo tiene un elemento, ya está ordenada
        }

        boolean swapped;
        Node current;
        Node last = null;

        do {
            swapped = false;
            current = head;

            while (current.next != last) {
                if (current.data.getValue() < current.next.data.getValue()) {
                    // Intercambiar los datos de los nodos
                    MP3File temp = current.data;
                    current.data = current.next.data;
                    current.next.data = temp;
                    swapped = true;
                }
                current = current.next;
            }
            last = current;
        } while (swapped);
    }

    public boolean display() {
        if (isEmpty()) {
            System.out.println("La lista está vacía.");
            return false;
        }

        Node current = head;
        while (current != null) {
            System.out.println(current.data.getFile().getName() + " - Valor: " + current.data.getValue()); // Mostrar el nombre del archivo y su valor
            current = current.next;
        }
        return true;
    }

    public void songRows(JPanel songPanel) throws IOException {
        if (this.tail == null) {
            System.out.println("vacia");
        } else {
            ReproductorMusica reproductorMusica = new ReproductorMusica(InventarioCanciones.listaCanciones);
            Node current = head;
            System.out.println(current);
            int i = 1;
            while (current != tail){
                GridBagConstraints parentGBC = new GridBagConstraints();

                //parentGBC.fill = GridBagConstraints.VERTICAL;
                parentGBC.gridx = 0;
                parentGBC.gridy = i;

                singleSongRow(current, songPanel, i-1, parentGBC, reproductorMusica);
                //JLabel btn = new JLabel(String.valueOf(metaData));
//                btn.setFont(new Font("Arial", Font.PLAIN, 24));
//                btn.setForeground(Color.decode("#a2a2a2"));
//                btn.setPreferredSize(new Dimension(800,100));
//                btn.setBorder(BorderFactory.createMatteBorder(5, 0, 0, 0, Color.decode("#1B222E")));
//                songPanel.add(btn, gbc);
                current = current.next;
                i++;
            }

        }
    }

    private void singleSongRow(Node current, JPanel songPanel, int i, GridBagConstraints parentGBC, ReproductorMusica reproductorMusica) throws IOException {
        JPanel row = new JPanel();
        row.setLayout(new GridBagLayout());
        row.setBorder(BorderFactory.createMatteBorder(20, 2, 0, 0, Color.decode("#0B121E")));
        GridBagConstraints gbc = new GridBagConstraints();

        row.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // when the user is holding the tick we want to the pause the song
                //musicPlayer.pauseSong();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                String cancion = String.valueOf(current.data.file.getName());
                System.out.println(cancion);
                MainWindow.playButton.doClick();
                reproductorMusica.reproducirCancion(cancion);
            }
        });

        //gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = i;
        String cancion = getMetadata(current).getFirst(FieldKey.TITLE);

        JLabel JLCancion = new JLabel(String.valueOf(cancion));
        JLCancion.setBackground(Color.decode("#1B222E"));
        JLCancion.setOpaque(true);
        JLCancion.setFont(new Font("Arial", Font.PLAIN, 20));
        JLCancion.setForeground(Color.decode("#a2a2a2"));
        JLCancion.setPreferredSize(new Dimension(174,50));
        JLCancion.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.decode("#a2a2a2")));
        row.add(JLCancion, gbc);

        gbc.gridx = 1;
        gbc.gridy = i;
        String artista = getMetadata(current).getFirst(FieldKey.ARTIST);

        JLabel JLArtista = new JLabel(String.valueOf(artista));
        JLArtista.setBackground(Color.decode("#1B222E"));
        JLArtista.setOpaque(true);
        JLArtista.setFont(new Font("Arial", Font.PLAIN, 20));
        JLArtista.setForeground(Color.decode("#a2a2a2"));
        JLArtista.setPreferredSize(new Dimension(174,50));
        JLArtista.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.decode("#a2a2a2")));
        row.add(JLArtista, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = i;
        String album = getMetadata(current).getFirst(FieldKey.ALBUM);

        JLabel JLAlbum = new JLabel(String.valueOf(album));
        JLAlbum.setBackground(Color.decode("#1B222E"));
        JLAlbum.setOpaque(true);
        JLAlbum.setFont(new Font("Arial", Font.PLAIN, 20));
        JLAlbum.setForeground(Color.decode("#a2a2a2"));
        JLAlbum.setPreferredSize(new Dimension(174,50));
        JLAlbum.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.decode("#a2a2a2")));
        row.add(JLAlbum, gbc);

        gbc.gridx = 3;
        gbc.gridy = i;
        String genero = getMetadata(current).getFirst(FieldKey.GENRE);

        JLabel JLGenero = new JLabel(String.valueOf(genero));
        JLGenero.setBackground(Color.decode("#1B222E"));
        JLGenero.setOpaque(true);
        JLGenero.setFont(new Font("Arial", Font.PLAIN, 20));
        JLGenero.setForeground(Color.decode("#a2a2a2"));
        JLGenero.setPreferredSize(new Dimension(174,50));
        JLGenero.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.decode("#a2a2a2")));
        row.add(JLGenero, gbc);

        gbc.gridx = 4;
        gbc.gridy = i;
        gbc.gridheight = 2;
//        String generos = getMetadata(current).getFirst(FieldKey.GENRE);
//        System.out.println(genero);
//        JLabel JLGeneros = new JLabel(String.valueOf(genero));
//        JLGeneros.setFont(new Font("Arial", Font.PLAIN, 20));
//        JLGeneros.setForeground(Color.decode("#a2a2a2"));
//        JLGeneros.setPreferredSize(new Dimension(170,100));
//        JLGeneros.setBorder(BorderFactory.createMatteBorder(3, 2, 0, 0, Color.decode("#a2a2a2")));
//        row.add(JLGeneros, gbc);

        JPanel votosPanel = new JPanel();
        votosPanel.setBackground(Color.decode("#1B222E"));
        votosPanel.setOpaque(true);
        //votosPanel.setFont(new Font("Arial", Font.PLAIN, 20));
        //votosPanel.setForeground(Color.decode("#a2a2a2"));
        votosPanel.setPreferredSize(new Dimension(170,100));
        votosPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.decode("#a2a2a2")));
        row.add(votosPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = i+1;
        gbc.gridwidth = 4;
        String geneross = getMetadata(current).getFirst(FieldKey.GENRE);

        JLabel JLGeneross = new JLabel(String.valueOf(current.data.getFile()));
        JLGeneross.setBackground(Color.decode("#1B222E"));
        JLGeneross.setOpaque(true);
        JLGeneross.setFont(new Font("Arial", Font.PLAIN, 20));
        JLGeneross.setForeground(Color.decode("#a2a2a2"));
        JLGeneross.setPreferredSize(new Dimension(700,50));
        JLGeneross.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.decode("#a2a2a2")));
        row.add(JLGeneross, gbc);




        songPanel.add(row, parentGBC);

    }

    public Tag getMetadata(Node nodo) throws IOException {


        File path = nodo.data.getFile();
        try{
            // use the jaudiotagger library to create an audiofile obj to read mp3 file's information
            AudioFile audioFile = AudioFileIO.read(path);

            // read through the meta data of the audio file
            Tag tag =  audioFile.getTag();
            if(tag != null){
//                    System.out.println(tag.getFirst(FieldKey.TITLE));
//                    System.out.println(tag.getFirst(FieldKey.ARTIST));
//                    System.out.println(tag.getFirst(FieldKey.ALBUM));
//                    System.out.println(tag.getFirst(FieldKey.GENRE));
//
//                    System.out.println(" ");

                //listaArtistas.insertSongByArtist(tag.getFirst(FieldKey.ARTIST), tag.getFirst(FieldKey.TITLE));
                //listaCircular.add(tag.getFirst(FieldKey.ARTIST), );
                //System.out.println(tag);
                return tag;
            }else{
                // could not read through mp3 file's meta data
                System.out.println("N/A");
//                    songTitle = "N/A";
//                    songArtist = "N/A";
            }
        }catch(Exception e){
            e.printStackTrace();
        }



        System.out.println("l artistas");
        //listaArtistas.showList();
        //listaArtistas.showSongsByArtist();
        //listaCircular.add("sdfs");
        return null;
    }

    public int contarElementos() {
        int numeroElementos = 0;
        if (isEmpty()) {
            System.out.println("La lista está vacía.");
            return numeroElementos;
        }

        Node current = head;
        while (current != null) {
            current = current.next;
            numeroElementos++;
        }
        return numeroElementos;
    }

    public String toStringList() throws IOException {
        String lista = new String();
        if (isEmpty()) {
            System.out.println("La lista está vacía.");
            return "";
        }

        Node current = head;
        while (current != null) {
            UUID uuid = UUID.randomUUID();
            String id = uuid.toString();
            String song = getMetadata(current).getFirst(FieldKey.TITLE);
            String artist = getMetadata(current).getFirst(FieldKey.ARTIST);
            current = current.next;
            lista = lista.concat("{\"id\":\"" + id + "\",\"song\":\"" + song + "\",\"artist\":\"" + artist + "\"},");
        }
        String removeLastComma = lista.substring(0, lista.length() - 1);
        return removeLastComma;
    }

    public Node getHead() {
        return head;
    }
    public Node getTail() {
        return tail;
    }
}

