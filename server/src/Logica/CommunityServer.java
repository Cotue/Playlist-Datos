package Logica;


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

    private String getResponse(String dataFromClient) {
        System.out.println(dataFromClient);
        return dataFromClient.toUpperCase();
    }

}