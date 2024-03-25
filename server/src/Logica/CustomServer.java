package Logica;

public class CustomServer {
    private int port = 1234;
    public void OpenServer() {
        CommunityServer newServer = new CommunityServer(port, 10);

        Thread serverThread = new Thread(newServer::startServer);

        serverThread.start();

    }
}


