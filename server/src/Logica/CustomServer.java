package Logica;

import java.io.IOException;

public class CustomServer {
    private int port = 1234;
    private CommunityServer newServer;
    private Thread serverThead;
    public void OpenServer() {
        newServer = new CommunityServer(port, 10);


        Thread serverThread = new Thread(newServer::startServer);

        serverThread.start();

    }
    public void closeServerSocket() throws IOException {
        newServer.serverSocket.close();
    }
}


