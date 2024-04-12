package Logica;




import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class CommunityServer {

    private final int port;
    private final int backlog;
    public ServerSocket serverSocket;

    public CommunityServer(int port, int backlog) {
        this.port = port;
        this.backlog = backlog;
    }

    public void startServer() {

        if (isPortInUse(port)) {
            System.out.println("Port " + port + " is already in use. Cannot start server.");
            return;
        }


        try (ServerSocket serverSocket = new ServerSocket(port, backlog)) {
            this.serverSocket = serverSocket;

            System.out.println("Started Listening for clients");
            boolean running = true;
            while (running) {


                // take input and output streams
                try (Socket client = serverSocket.accept();
                     Scanner scanner = new Scanner(client.getInputStream());
                     PrintWriter pw = new PrintWriter(client.getOutputStream(), true)) {
                    String dataFromClient = scanner.nextLine();

                    String response = getResponse(dataFromClient);
                    pw.write(response);

                }catch(Exception e){
                    running = false;
                }

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private boolean isPortInUse(int port) {
        try (ServerSocket ignored = new ServerSocket(port)) {
            return false;
        } catch (IOException e) {
            return true;
        }
    }

    private String getResponse(String dataFromClient) throws IOException {

        String lista = new String(InventarioCanciones.listaCanciones.toStringList());
        String respuesta = "";
        //JsonObject command = new JsonObject();
        if (dataFromClient.contains("Get-Playlist")){
            respuesta = new String("{\"command\":\"Get-Playlist\",\"status\":\"OK\",\"list\":[" + lista + "]}");
            System.out.println(respuesta);
        } else if (dataFromClient.contains("Vote-down")) {
            
        } else if (dataFromClient.contains("Vote-up")) {
            
        }

        return respuesta;
    }

}