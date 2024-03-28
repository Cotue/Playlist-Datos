using System;
using System.Text;
using System.Net.Sockets;
using System.Threading;

namespace Sockets
{
    class ClientSocket
    {
        private int port;
        private TcpClient client;
        private NetworkStream stream;



        public ClientSocket(int port, int sendTimeout, int receiveTimeout)
        {
            this.port = port;
            client = new TcpClient("localhost", port);

            client.ReceiveTimeout = sendTimeout;
            client.SendTimeout = receiveTimeout;
            stream = client.GetStream();
        }



        public void processData(String data)
        {
            byte[] buf;

            /* append newline as server expects a line to be read */
            buf = Encoding.UTF8.GetBytes(data + "\n");

            Console.WriteLine("Sending data \'{0}\' to server", data);

            stream.Write(buf, 0, data.Length + 1);

            buf = new byte[100];
            int bytesRead = stream.Read(buf, 0, 100);

            byte[] finalData = new byte[bytesRead];

            for (int i = 0; i < bytesRead; i++)
            {
                finalData[i] = buf[i];
            }

            string response = Encoding.UTF8.GetString(finalData);

            response = response.TrimEnd();

            Console.WriteLine("Received Response : \'{0}\', of length {1}", response, response.Length);

            client.Close();
        }

    }



    class ConnectToServer
    {
        public bool connected = false;
        public bool Start()
        {
            ClientSocket clientSock;
            while (!connected)
            {
                Console.WriteLine("Buscando conexión");
                try
                {
                    clientSock = new ClientSocket(1234, 3000, 3000);

                    clientSock.processData("Conectado");



                    connected = true;
                    Console.WriteLine(connected);


                }
                catch (Exception exception)
                {
                    Console.WriteLine("Falló al conectar. Reintentando en 3 segundos...");
                    Thread.Sleep(3000);
                }
            }

            return connected;
        }
    }
}

