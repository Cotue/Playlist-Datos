using System;
using System.Text;
using System.Net.Sockets;
using System.Threading;
using cliente;
using System.Text.Json;
using System.Collections.Generic;

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

        public class Song
        {
            public string Id { get; set; }
            public string Title { get; set; }
            public string Artist { get; set; }
        }

        public class PlaylistResponse
        {
            public string command { get; set; }
            public string status { get; set; }
            public List<Song> list { get; set; }
        }


        public PlaylistResponse jsonFormater(string jsonString)
        {

            //string jsonString = "{\"command\":\"Get-Playlist\",\"status\":\"OK\",\"list\":[{\"id\":\"080da861-1a0c-4d87-8ef5-85b9685ab9ff\",\"song\":\"50 Palos\",\"artist\":\"Feid\"},{\"id\":\"913e0f6a-95e0-4683-9b16-e986cfded37f\",\"song\":\"Alakran\",\"artist\":\"Feid\"},{\"id\":\"ff2bf430-dfc7-4de0-beae-eafa5f28bdef\",\"song\":\"Desquite\",\"artist\":\"Feid\"},{\"id\":\"433063d8-f796-4a73-9cd9-7bb7307ce3f1\",\"song\":\"Esquirla\",\"artist\":\"Feid\"},{\"id\":\"08d50a27-7fd9-4258-951f-b7030d7454c3\",\"song\":\"Interlude\",\"artist\":\"Feid\"},{\"id\":\"b7d81fb4-d159-455c-8082-67e21123f48d\",\"song\":\"Yo AK\",\"artist\":\"Feid\"},{\"id\":\"fd4c1314-1316-4e96-9d14-616b4be80e9a\",\"song\":\"Luna\",\"artist\":\"Feid, ATL Jacob\"},{\"id\":\"52d70fb7-1003-47c9-a753-15cdf1a7e4c9\",\"song\":\"La Vuelta\",\"artist\":\"Feid, Mañas Ru-Fino\"},{\"id\":\"20607235-f5a6-4290-b2e1-1bbb885beca2\",\"song\":\"Cual es Esa\",\"artist\":\"Feid, Pirlo\"},{\"id\":\"30720715-e1b7-4a9e-acde-2dca5a444c8b\",\"song\":\"Classy 101\",\"artist\":\"Feid, Young Miko\"},{\"id\":\"632bc526-eb65-4183-aaa2-4e41bf2ae1f3\",\"song\":\"Aquel Nap ZzZz\",\"artist\":\"Rauw Alejandro\"},{\"id\":\"560abea0-0a40-4ed3-9f6a-99f8870ad5a9\",\"song\":\"Cuando Fue\",\"artist\":\"Rauw Alejandro\"},{\"id\":\"5c699a45-f266-471f-b8ce-48b99b58a58b\",\"song\":\"Cúrame\",\"artist\":\"Rauw Alejandro\"},{\"id\":\"2d798922-c7f8-4e24-bf92-83f50f95fec7\",\"song\":\"Desenfocau\",\"artist\":\"Rauw Alejandro\"},{\"id\":\"cfad391d-f7aa-4886-b363-ca38758c1f94\",\"song\":\"La Old Skul\",\"artist\":\"Rauw Alejandro\"},{\"id\":\"6f535aa9-c3e7-4531-94d9-35effbe36da6\",\"song\":\"Loading V.V.\",\"artist\":\"Rauw Alejandro\"},{\"id\":\"ade08e0c-3701-48d6-8b9d-86a5bd49a064\",\"song\":\"Nubes\",\"artist\":\"Rauw Alejandro\"},{\"id\":\"7aa56aee-aa6d-42dc-b1a4-07bdd5a9c5dc\",\"song\":\"Sexo Virtual\",\"artist\":\"Rauw Alejandro\"},{\"id\":\"8f050040-4c4f-413b-afd8-39506dd7b5d4\",\"song\":\"Todo De Ti\",\"artist\":\"Rauw Alejandro\"},{\"id\":\"378f2dc9-c66e-4024-9bfd-b5434816ef07\",\"song\":\"Brazilera (Ft. Anitta)\",\"artist\":\"Rauw Alejandro\"},{\"id\":\"aaaba6b5-6bae-49e3-8d09-aec2afab0b56\",\"song\":\"Tengo un Pal (Ft. Lyanno)\",\"artist\":\"Rauw Alejandro\"},{\"id\":\"b04bcd4a-3f32-47dd-ad23-94ef35faaea3\",\"song\":\"2-Catorce (Ft. Mr Naisgai)\",\"artist\":\"Rauw Alejandro\"},{\"id\":\"5ff28ca9-01ec-4214-a84a-8ddcb7e62d6e\",\"song\":\"Y Eso\",\"artist\":\"Rauw Alejandro\"}]}";

            // Deserialize JSON string into a PlaylistResponse object
            PlaylistResponse playlist = JsonSerializer.Deserialize<PlaylistResponse>(jsonString);

            // Access deserialized object properties
            Console.WriteLine("command: " + playlist.command);
            Console.WriteLine("status: " + playlist.status);

            Console.WriteLine("\nSongs:");
            foreach (var song in playlist.list)
            {
                Console.WriteLine("ID: " + song.Id);
                Console.WriteLine("Title: " + song.Title);
                Console.WriteLine("Artist: " + song.Artist);
                Console.WriteLine();
            }
            return playlist;
        }



        public void processData(String data)
        {
            byte[] buf;

            /* append newline as server expects a line to be read */
            buf = Encoding.UTF8.GetBytes(data + "\n");

            Console.WriteLine("Sending data \'{0}\' to server", data);

            stream.Write(buf, 0, data.Length + 1);

            buf = new byte[4096];
            int bytesRead = stream.Read(buf, 0, buf.Length);

            byte[] finalData = new byte[bytesRead];

            for (int i = 0; i < bytesRead; i++)
            {
                finalData[i] = buf[i];
            }

            string response = Encoding.UTF8.GetString(finalData);

            response = response.TrimEnd();

            try
            {
                Console.WriteLine(jsonFormater(response).list[0]);

            } catch (Exception e)
            {
                Console.WriteLine($"Error: {e.Message}");
            }


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

                    clientSock.processData("{\"command\":\"Connect\",\"status\":\"OK\",\"list\":[]}");



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

