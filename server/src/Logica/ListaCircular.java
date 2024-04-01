package Logica;

import java.io.IOException;
import java.util.Objects;

import Logica.ListaDouble.Node;
public class ListaCircular<T> {
    private NodeCircular<T> tail;

    class NodeCircular<T> {
        T value;
        NodeCircular<T> next;

        public NodeCircular(T value) {
            this.value = value;
            this.next = null;
        }
    }

    public void add(T cancion) throws IOException {
//        ListaDouble listaCanciones =  InventarioCanciones.obtenerListaCanciones();
//
//        listaCanciones.display();
        NodeCircular<T> newSong = new NodeCircular<>(cancion);
        if (this.tail == null) {
            newSong.next = newSong;
            this.tail = newSong;
        } else {
            if (!inList(cancion)) {
                newSong.next = this.tail.next;
                this.tail.next = newSong;
                this.tail = newSong;

            }
        }

    }

    public void insertInCircular(T artist, Node song) throws IOException {
        Node newSong = song;
        System.out.println(newSong.getClass().getName());
        NodeCircular<T> newArtist = new NodeCircular<>(artist);
        if (this.tail == null) {
            newArtist.next = newArtist;
            this.tail = newArtist;
        } else {
            if (!inList(artist)) {
//                ListaCircular<Node> canciones = new ListaCircular<>(song);
//                canciones.tail.next = newArtist;
//                canciones.add(newSong);
                newArtist.next = this.tail.next;
                this.tail.next = newArtist;
                this.tail = newArtist;

            }
        }
    }

    public void print(){
        if (this.tail == null) {
            System.out.println("vacia");
        } else {
            NodeCircular current = tail.next;
            while (current != tail){
                System.out.println(current.value);
                current = current.next;
            }
            System.out.println(current.value);
        }
    }

    public boolean inList(T value) {
        if (this.tail == null) {
            return false;
        }
        NodeCircular current = this.tail.next;
        while (current != this.tail) {
            if (Objects.equals(current.value, value)) {
                return true;
            }
            current = current.next;
        }
        return Objects.equals(current.value, value);
    }





}
