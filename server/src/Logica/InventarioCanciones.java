package Logica;
import java.io.File;
import java.io.IOException;

public class InventarioCanciones {
    public static ListaDouble listaCanciones;
    public static ListaCircular listaArtistas = new ListaCircular();

    public static void createInitialSongList() throws IOException {
        File carpeta = new File("./src/Logica/Listas/Canciones");

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
        listaCanciones = new ListaDouble(archivos);

        //
        // obtenerListaCanciones();
        //lista.getMetadata();
        //obtenerListaCanciones();
    }

    public static void setListaArtistas(ListaCircular lista) {
        listaArtistas = lista;
    }
    public static ListaDouble obtenerListas() throws IOException {
        if (listaCanciones == null) {
            createInitialSongList();
        }
        //System.out.println(listaCanciones.contarElementos());
        return listaCanciones;
    }

    public Object obtenerListaArtistas() throws IOException {
        File carpeta = new File("./src/Logica/Listas/Canciones");
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


    //C:/Users/Usuario/Documents/GitHub/Playlist-Datos/server/src/
}
