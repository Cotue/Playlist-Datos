using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using Sockets;


namespace cliente
{
    public partial class Form1 : Form
    {
        
        public bool connectServer = false;
        public Form1()
        {

            
            InitializeComponent();

            labelConnecting.Parent = pictureBox1;
            btnIngresar.Parent = pictureBox1;

            
        }

        public async void loadingScreen()
        {
            ConnectToServer tryConnection = new ConnectToServer();
            await Task.Run(() => tryConnection.Start());

            if (tryConnection.connected)
            {
                // Update UI based on connection status
                btnIngresar.Invoke((MethodInvoker)(() => btnIngresar.Visible = true));
                labelConnecting.Invoke((MethodInvoker)(() => labelConnecting.Visible = false));
                connectServer = true;
                timer1.Stop();
            }

            
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            loadingScreen();
        }


        private void pictureBox1_Click(object sender, EventArgs e)
        {
            
        }

        private void labelConnecting_Click(object sender, EventArgs e)
        {

        }

        private void Form1_Shown(object sender, EventArgs e)
        {
            
        }

        private void pictureBox1_ControlRemoved(object sender, ControlEventArgs e)
        {
            
        }


        private void btnIngresar_Click(object sender, EventArgs e)
        {
            if (connectServer)
            {
                pictureBox1.Visible = false;
                Hide();
                Form2 form2 = new Form2();
                form2.ShowDialog();
                Close();
            }else
            {

                labelConnecting.Visible = true;
                btnIngresar.Visible = false;
            }
        }
    }
}
