package Logica;

import java.io.File;
import java.util.Scanner;
import javazoom.jl.player.Player;
import java.io.FileInputStream;

public class ReproductorMusica {
    private ListaDouble listaCanciones;
    private ListaDouble.Node currentSong;
    private Player player;
    private boolean isPaused;
    private long pausePosition;

    public ReproductorMusica(ListaDouble listaCanciones) {
        this.listaCanciones = listaCanciones;
        this.currentSong = null;
        this.player = null;
        this.isPaused = false;
        this.pausePosition = 0;
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

    public void pausarCancion() {
        if (player != null && !isPaused) {
            player.close();
            isPaused = true;
            System.out.println("Canción pausada.");
        } else {
            System.out.println("No hay ninguna canción reproduciéndose.");
        }
    }

    public void actualizarListaCanciones(ListaDouble nuevaLista) {
        this.listaCanciones = nuevaLista;
    }

    public void resumirCancion() {
        if (currentSong != null && isPaused) {
            reproducirAudio(currentSong.data.getFile());
            isPaused = false;
            System.out.println("Canción reanudada.");
        } else {
            System.out.println("No hay ninguna canción pausada.");
        }
    }

    public void reproducirPrimeraCancion() {
        if (listaCanciones.isEmpty()) {
            System.out.println("No hay canciones en la lista.");
            return;
        }
        reproducirCancion(listaCanciones.getHead().data.getFile().getName());
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

    public void darLike() {
        if (currentSong != null) {
            currentSong.data.setValue(currentSong.data.getValue() + 1);
            System.out.println("Has dado like a la canción: " + currentSong.data.getFile().getName());
            LikeDislikePersistence.guardarLikesDislikes(listaCanciones);
        } else {
            System.out.println("No hay ninguna canción reproduciéndose.");
        }
    }

    public void darDislike() {
        if (currentSong != null) {
            currentSong.data.setValue(currentSong.data.getValue() - 1);
            System.out.println("Has dado dislike a la canción: " + currentSong.data.getFile().getName());
            LikeDislikePersistence.guardarLikesDislikes(listaCanciones);
        } else {
            System.out.println("No hay ninguna canción reproduciéndose.");
        }
    }


    public static void main(String[] args) {
        InventarioCanciones inventario = new InventarioCanciones();
        ListaDouble listaCanciones = InventarioCanciones.obtenerListaCanciones();

        ReproductorMusica reproductor = new ReproductorMusica(listaCanciones);
        Scanner scanner = new Scanner(System.in);

        Thread commandThread = new Thread(() -> {
            while (true) {
                System.out.println("Comandos: reproducir [nombre], siguiente, anterior, stop, comunity, salir");
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
                } else if (comando.equalsIgnoreCase("stop")) {
                    reproductor.pausarCancion();
                } else if (comando.equalsIgnoreCase("anterior")) {
                    reproductor.anteriorCancion();
                } else if (comando.equalsIgnoreCase("resumir")) {
                    reproductor.resumirCancion();
                } else if (comando.equalsIgnoreCase("community")) {
                    ListaDouble nuevaListaCanciones = InventarioCanciones.obtenerListaCanciones();
                    LikeDislikePersistence.cargarLikesDislikes(nuevaListaCanciones);
                    nuevaListaCanciones.sortDescending(); // Ordenar la lista en orden descendente según los nuevos valores de likes y dislikes
                    reproductor.actualizarListaCanciones(nuevaListaCanciones);
                    reproductor.reproducirPrimeraCancion();
                } else if (comando.equalsIgnoreCase("like")) {
                    reproductor.darLike();
                } else if (comando.equalsIgnoreCase("dislike")) {
                    reproductor.darDislike();
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

