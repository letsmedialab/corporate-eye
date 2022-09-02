
using NativeSupportServiceApplication.Api;
using NativeSupportServiceApplication.Dto;
using NativeSupportServiceApplication.Utils;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace NativeSupportServiceApplication
{
    public partial class MainApplicationForm : Form
    {
        public MainApplicationForm()
        {
            InitializeComponent();
        }

        private void notifyIcon1_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            Show();
            this.WindowState = FormWindowState.Normal;
            
        }

        private void MainApplicationForm_Load(object sender, EventArgs e)
        {
            
            this.WindowState = FormWindowState.Minimized;
            this.Close();
           
        }

        private void MainApplicationForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            e.Cancel = true;
            this.Hide();

        }

        private void MainApplicationForm_FormClosed(object sender, FormClosedEventArgs e)
        {
            this.Hide();
            
        }

        private void button1_Click(object sender, EventArgs e)
        {
            //ApiResponse<FileUploadResponse> response =  ApiUtils.uploadFile("E:\\WorkOnedrive\\OneDrive\\Pictures\\les-brown-shoot-for-the-moon.jpg");

           

           // Debug.WriteLine(response.RawResponse);

        }
    }
}
