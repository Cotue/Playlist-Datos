package Logica;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import java.io.File;
import java.io.IOException;



public class ListaDouble {
    class MP3File {
        private File file;

        public MP3File(File file) {
            this.file = file;
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
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
                insertAtEnd(new MP3File(file));
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



////        ListaDouble listaCanciones =  InventarioCanciones.obtenerListaCanciones();
////
////        listaCanciones.display();
//        Logica.ListaCircular.NodeCircular<T> newSong = new Logica.ListaCircular.NodeCircular<>(cancion);
//        if (this.tail == null) {
//            newSong.next = newSong;
//            this.tail = newSong;
//        } else {
//            if (!inList(cancion)) {
//                newSong.next = this.tail.next;
//                this.tail.next = newSong;
//                this.tail = newSong;
//
//            }
//        }

    }

    public boolean display() {
        if (isEmpty()) {
            System.out.println("La lista está vacía.");
            return false;
        }

        Node current = head;
        while (current != null) {
            System.out.println(current.data.getFile().getName()); // Aquí podrías personalizar la visualización según tus necesidades
            current = current.next;
        }
        return true;
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

    public Node getHead() {
        return head;
    }
    public Node getTail() {
        return tail;
    }
}
