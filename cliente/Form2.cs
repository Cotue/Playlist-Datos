using Sockets;
using System;
using System.Text.Json;
using System.Windows.Forms;

namespace cliente
{
    public partial class Form2 : Form
    {
        private ClientSocket clientSocket;
        public Form2()
        {
            InitializeComponent();
            clientSocket = new ClientSocket(1234, 3000, 3000);
            
        }

        private void button1_Click(object sender, EventArgs e)
        {
            var getPlaylistCommand = new GetPlaylistCommand 
            { 
                command = "Get-Playlist" 
            };
            string json = JsonSerializer.Serialize(getPlaylistCommand);
            var downVote = new Vote 
            { 
                command = "Vote-down",
                id = "f97afe84-54d4-43d0-a0c0-b37c91c1349c"
            };
            string vote = JsonSerializer.Serialize(downVote);
            //string mensaje = Mensaje.Text;
            //clientSocket.processData(json);
            clientSocket.processData(vote);
            
        }

        public class GetPlaylistCommand
        {
            public string command {  get; set; }

        }

        public class Vote
        {
            public string command { get; set; }
            public string id { get; set; }
        }

        private void Mensaje_TextChanged(object sender, EventArgs e)
        {

        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private void label2_Click(object sender, EventArgs e)
        {

        }

        private void label11_Click(object sender, EventArgs e)
        {

        }
    }
}
