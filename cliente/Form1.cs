using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Sockets;

namespace cliente
{
    public partial class Form1 : Form
    {
        private ClientSocket clientSock;
        private String i = "jfgjhfjh";
        public Form1()
        {
            InitializeComponent();
            this.clientSock = new ClientSocket(1234, 3000, 3000);
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.clientSock.processData(this.i);
            this.clientSock.processData(this.i);
            this.clientSock.processData(this.i);
        }
    }
}
