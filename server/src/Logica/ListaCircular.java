package Logica;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import Logica.ListaDouble.Node;

import javax.swing.*;

public class ListaCircular<T> {
    public NodeCircular<T> tail;



    class NodeCircular<T> {
        T value;
        NodeCircular<T> next;

        ListaDouble canciones;


        public NodeCircular(T value) {
            this.value = value;
            this.next = null;
            this.canciones = new ListaDouble();
        }
    }



    public void insertInCircular(T artist, Node song) throws IOException {
        Node newSong = song;
        //System.out.println(newSong.getClass().getName());
        NodeCircular<T> newArtist = new NodeCircular<>(artist);
        if (this.tail == null) {
            newArtist.next = newArtist;
            this.tail = newArtist;
            //System.out.println(tail.value);
        } else {
            if (inList(artist) == null) {
                //ListaDouble canciones = new ListaDouble();
                //System.out.println(newArtist.canciones);
                newArtist.next = this.tail.next;
                this.tail.next = newArtist;
                this.tail = newArtist;
//                newArtist.tail.next = newArtist;
                newArtist.canciones.add(newSong);
//                tail.next = newArtist;
//                //newArtist.next = this.tail;
//                tail = newArtist;

            } else  {
                inList(artist).canciones.add(newSong);//.canciones.add(newSong);
                System.out.println(artist);
                System.out.println(inList(artist).canciones);
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

    public void artistsRows(JPanel artistsPanel){
        if (this.tail == null) {
            System.out.println("vacia");
        } else {
            NodeCircular current = tail.next;

            int i = 1;
            while (current != tail){
                GridBagConstraints gbc = new GridBagConstraints();

                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = i;
                JLabel btn = new JLabel(String.valueOf(current.value));
                btn.setFont(new Font("Arial", Font.PLAIN, 24));
                btn.setForeground(Color.decode("#a2a2a2"));
                btn.setPreferredSize(new Dimension(250,100));
                btn.setBorder(BorderFactory.createMatteBorder(5, 0, 0, 0, Color.decode("#1B222E")));
                artistsPanel.add(btn, gbc);
                current = current.next;
                i++;
            }

        }
    }

    public NodeCircular inList(T value) {
        if (this.tail == null) {
            return null;
        }
        NodeCircular current = this.tail.next;
        while (current != this.tail) {
            if (Objects.equals(current.value, value)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }


    public NodeCircular getTail() {
        return tail;
    }


}
