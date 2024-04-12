package Logica;

import java.io.*;

public class LikeDislikePersistence {
    private static final String FILE_PATH = "C:\\Users\\X\\Documents\\Proyecto PL\\Playlist-Datos\\server\\src\\Logica\\Likes\\Likes.txt";

    public static void guardarLikesDislikes(ListaDouble lista) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            ListaDouble.Node current = lista.getHead();
            while (current != null) {
                writer.println(current.data.getFile().getName() + "," + current.data.getValue());
                current = current.next;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cargarLikesDislikes(ListaDouble lista) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String nombreCancion = parts[0];
                int valor = Integer.parseInt(parts[1]);

                ListaDouble.Node current = lista.getHead();
                while (current != null) {
                    if (current.data.getFile().getName().equals(nombreCancion)) {
                        current.data.setValue(valor);
                        break;
                    }
                    current = current.next;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
