
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
using System.Xml.Linq;

namespace NativeSupportServiceApplication
{
    public partial class MainApplicationForm : Form
    {
        public MainApplicationForm()
        {
            InitializeComponent();

            if (GlobalConstants.getCurrentUser().UserName.Equals("admin"))
            {
                toolExit.Enabled = true;
            }
        }

        private void notifyIcon1_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            Show();
            this.WindowState = FormWindowState.Normal;
            
        }

        private void MainApplicationForm_Load(object sender, EventArgs e)
        {
           
            //this.WindowState = FormWindowState.Minimized;
           // this.Close();
            
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


            GeneralUtil.logout();
           // Debug.WriteLine(response.RawResponse);

        }

        private void contextMenuStrip1_Opening(object sender, CancelEventArgs e)
        {

        }

        private void logOut_Click(object sender, EventArgs e)
        {


            // Prepare the process to run
            ProcessStartInfo start = new ProcessStartInfo();
            
            // Enter the executable to run, including the complete path
            start.FileName = System.Diagnostics.Process.GetCurrentProcess().MainModule.FileName;
            // Do you want to show a console window?
            start.WindowStyle = ProcessWindowStyle.Hidden;
            start.CreateNoWindow = true;
            int exitCode;


            // Run the external process & wait for it to finish
            Process proc = Process.Start(start);
         

            GeneralUtil.logout();
        }

        private void toolExit_Click(object sender, EventArgs e)
        {
            GeneralUtil.logout();
        }
    }
}
