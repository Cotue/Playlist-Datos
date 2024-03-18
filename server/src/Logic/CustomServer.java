package Logic;


public class CustomServer {

    public void OpenServer() {
        CommunityServer newServer = new CommunityServer(1234, 10);

        newServer.startServer();

    }
}


