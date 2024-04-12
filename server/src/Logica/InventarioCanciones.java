package Logica;

import java.io.File;
import java.io.IOException;

public class InventarioCanciones {
    public static ListaDouble listaCanciones;
    public static ListaCircular listaArtistas = new ListaCircular();
    public static ListaDouble obtenerListaCanciones() throws IOException {
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
        listaCanciones = new ListaDouble(archivos);
        listaCanciones.sortDescending(); // Ordenar la lista en orden descendente
        return listaCanciones;
    }

    public static void main(String[] args) throws IOException {
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

        listaCanciones.sortDescending(); // Ordenar la lista en orden descendente
        listaCanciones.display(); // Mostrar la lista ordenada
        //return lista;
    }


    //C:/Users/Usuario/Documents/GitHub/Playlist-Datos/server/src/
}
