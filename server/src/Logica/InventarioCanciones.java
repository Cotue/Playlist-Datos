package Logica;
import java.io.File;

public class InventarioCanciones {
    public static ListaDouble obtenerListaCanciones() {
        File carpeta = new File("C:\\Users\\X\\Documents\\Proyecto PL\\Playlist-Datos\\server\\src\\Logica\\Listas\\Canciones");

        // Verificar si el directorio "Canciones" existe
        if (!carpeta.exists() || !carpeta.isDirectory()) {
            System.out.println("La carpeta Canciones no existe.");
            return null;
        }

        // Obtener una lista de archivos dentro de la carpeta
        File[] archivos = carpeta.listFiles();
        if (archivos == null || archivos.length == 0) {
            System.out.println("La carpeta Canciones está vacía.");
            return null;
        }

        // Crear la lista de canciones
        ListaDouble lista = new ListaDouble(archivos);
        return lista;
    }
    public static void main(String[] args) {
        File carpeta = new File("C:\\Users\\X\\Documents\\Proyecto PL\\Playlist-Datos\\server\\src\\Logica\\Listas\\Canciones");

        // Verificar si el directorio "Canciones" existe
        if (!carpeta.exists() || !carpeta.isDirectory()) {
            System.out.println("La carpeta Canciones no existe.");
            return;
        }

        // Obtener una lista de archivos dentro de la carpeta
        File[] archivos = carpeta.listFiles();
        if (archivos == null || archivos.length == 0) {
            System.out.println("La carpeta Canciones está vacía.");
            return;
        }

        // Crear la lista de canciones
        ListaDouble lista = new ListaDouble(archivos);
        lista.display();
    }
}
