package Logica;

import java.io.File;


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

    public ListaDouble(File[] files) {
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".mp3")) {
                insertAtEnd(new MP3File(file));
            }
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void insertAtEnd(MP3File data) {
        Node newNode = new Node(data);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("La lista está vacía.");
            return;
        }

        Node current = head;
        while (current != null) {
            System.out.println(current.data.getFile().getName()); // Aquí podrías personalizar la visualización según tus necesidades
            current = current.next;
        }
    }

    public Node getHead() {
        return head;
    }
}
