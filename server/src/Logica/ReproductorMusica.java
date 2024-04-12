package Logica;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javazoom.jl.player.Player;
import java.io.FileInputStream;

public class ReproductorMusica {
    private ListaDouble listaCanciones;
    private ListaDouble.Node currentSong;
    private Player player;

    public ReproductorMusica(ListaDouble listaCanciones) {
        this.listaCanciones = listaCanciones;
        this.currentSong = null;
        this.player = null;
    }

    public void reproducirCancion(String nombreCancion) {
        ListaDouble.Node current = listaCanciones.getHead();
        while (current != null) {
            if (current.data.getFile().getName().equals(nombreCancion)) {
                detenerReproduccion(); // Detener la canción actual antes de reproducir la nueva
                currentSong = current;
                System.out.println("Reproduciendo: " + nombreCancion);
                reproducirAudio(current.data.getFile());
                return;
            }
            current = current.next;
        }
        System.out.println("La canción no se encuentra en la lista.");
    }

    private void reproducirAudio(File audioFile) {
        try {
            player = new Player(new FileInputStream(audioFile));
            Thread playerThread = new Thread(() -> {
                try {
                    player.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            playerThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void siguienteCancion() {
        if (currentSong == null || currentSong.next == null) {
            System.out.println("No hay más canciones en la lista.");
            return;
        }
        detenerReproduccion(); // Detener la canción actual antes de pasar a la siguiente
        currentSong = currentSong.next;
        System.out.println("Reproduciendo siguiente canción: " + currentSong.data.getFile().getName());
        reproducirCancion(currentSong.data.getFile().getName());
    }

    public void anteriorCancion() {
        if (currentSong == null || currentSong.previous == null) {
            System.out.println("No hay canciones anteriores en la lista.");
            return;
        }
        detenerReproduccion(); // Detener la canción actual antes de reproducir la anterior
        currentSong = currentSong.previous;
        System.out.println("Reproduciendo canción anterior: " + currentSong.data.getFile().getName());
        reproducirCancion(currentSong.data.getFile().getName());
    }

    private void detenerReproduccion() {
        if (player != null) {
            player.close();
        }
    }

    public static void main(String[] args) throws IOException {
        InventarioCanciones inventario = new InventarioCanciones();
        inventario.createInitialSongList();
        ListaDouble listaCanciones = inventario.listaCanciones;

        ReproductorMusica reproductor = new ReproductorMusica(listaCanciones);
        Scanner scanner = new Scanner(System.in);

        Thread commandThread = new Thread(() -> {
            while (true) {
                System.out.println("Comandos: reproducir [nombre], siguiente, anterior, salir");
                System.out.print("Ingrese un comando: ");
                String comando = scanner.nextLine();

                if (comando.equalsIgnoreCase("salir")) {
                    System.out.println("Saliendo del reproductor...");
                    System.exit(0);
                } else if (comando.startsWith("reproducir")) {
                    String nombreCancion = comando.substring(11);
                    reproductor.reproducirCancion(nombreCancion);
                } else if (comando.equalsIgnoreCase("siguiente")) {
                    reproductor.siguienteCancion();
                } else if (comando.equalsIgnoreCase("anterior")) {
                    reproductor.anteriorCancion();
                } else {
                    System.out.println("Comando no reconocido.");
                }
            }
        });

        commandThread.start();

        // Espera hasta que el hilo del comando termine
        try {
            commandThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        scanner.close();
    }
}