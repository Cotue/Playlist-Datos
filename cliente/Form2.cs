using System;
using System.Windows.Forms;
using Sockets;

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
            string mensaje = Mensaje.Text;
            clientSocket.processData(mensaje);
        }

        private void Mensaje_TextChanged(object sender, EventArgs e)
        {

        }
    }
}
