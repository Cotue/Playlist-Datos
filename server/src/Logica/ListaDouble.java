package Logica;

import java.io.File;

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

    public ListaDouble(File[] files) {
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".mp3")) {
                insertAtEnd(new MP3File(file, 0)); // Inicializando el valor en 0
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

    public void display() {
        if (isEmpty()) {
            System.out.println("La lista está vacía.");
            return;
        }

        Node current = head;
        while (current != null) {
            System.out.println(current.data.getFile().getName() + " - Valor: " + current.data.getValue()); // Mostrar el nombre del archivo y su valor
            current = current.next;
        }
    }

    public Node getHead() {
        return head;
    }
}

