package Logica;
import javax.sound.sampled.*;
import java.io.File;
import java.util.Scanner;

public class ReproductorMusica {
    private ListaDouble listaCanciones;
    private ListaDouble.Node currentSong;

    public ReproductorMusica(ListaDouble listaCanciones) {
        this.listaCanciones = listaCanciones;
        this.currentSong = null;
    }

    public void reproducirCancion(String nombreCancion) {
        ListaDouble.Node current = listaCanciones.getHead();
        while (current != null) {
            if (current.data.getFile().getName().equals(nombreCancion)) {
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
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            AudioFormat format = audioStream.getFormat();

            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);
            audioLine.open(format);
            audioLine.start();

            byte[] buffer = new byte[4096];
            int bytesRead = 0;
            while ((bytesRead = audioStream.read(buffer)) != -1) {
                audioLine.write(buffer, 0, bytesRead);
            }

            audioStream.close();
            audioLine.drain();
            audioLine.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void siguienteCancion() {
        if (currentSong == null || currentSong.next == null) {
            System.out.println("No hay más canciones en la lista.");
            return;
        }
        currentSong = currentSong.next;
        System.out.println("Reproduciendo siguiente canción: " + currentSong.data.getFile().getName());
    }

    public void anteriorCancion() {
        if (currentSong == null || currentSong.previous == null) {
            System.out.println("No hay canciones anteriores en la lista.");
            return;
        }
        currentSong = currentSong.previous;
        System.out.println("Reproduciendo canción anterior: " + currentSong.data.getFile().getName());
    }

    public static void main(String[] args) {
        InventarioCanciones inventario = new InventarioCanciones();
        ListaDouble listaCanciones = InventarioCanciones.obtenerListaCanciones();

        ReproductorMusica reproductor = new ReproductorMusica(listaCanciones);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Comandos: reproducir [nombre], siguiente, anterior, salir");
            System.out.print("Ingrese un comando: ");
            String comando = scanner.nextLine();

            if (comando.equalsIgnoreCase("salir")) {
                System.out.println("Saliendo del reproductor...");
                break;
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

        scanner.close();
    }
}


