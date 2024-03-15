package Logica.Listas;
public class MP3Playlist {
    private SongNode head;
    private SongNode tail;
    private int size;
    public MP3Playlist() {
        head = null;
        tail = null;
        size = 0;
    }
    public void addSong(String songName) {
        SongNode newNode = new SongNode(songName);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }
    public void removeSong(String songName) {
        SongNode current = head;
        while (current != null) {
            if (current.songName.equals(songName)) {
                if (current == head && current == tail) {
                    head = null;
                    tail = null;
                } else if (current == head) {
                    head = head.next;
                    head.prev = null;
                } else if (current == tail) {
                    tail = tail.prev;
                    tail.next = null;
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
                size--;
                return;
            }
            current = current.next;
        }
        System.out.println("Song not found in the playlist.");
    }