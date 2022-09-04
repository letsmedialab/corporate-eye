
using NativeSupportServiceApplication.Api;
using NativeSupportServiceApplication.Dto;
using NativeSupportServiceApplication.Models;
using NativeSupportServiceApplication.Utils;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Xml.Linq;
using Windows.ApplicationModel.DataTransfer;
using static System.Net.Mime.MediaTypeNames;

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

            Windows.ApplicationModel.DataTransfer.Clipboard.ContentChanged += new EventHandler<object>(this.TrackClipboardChanges_EventHandler);
        }

        private void notifyIcon1_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            Show();
            this.WindowState = FormWindowState.Normal;
            
        }
        private async void TrackClipboardChanges_EventHandler(object sender, object e)
        {
           

            if (System.Windows.Forms.Clipboard.ContainsFileDropList())
            {
               
                    List<DetectedRestrictedFile> detectedFiles = new List<DetectedRestrictedFile>();

                    var files = System.Windows.Forms.Clipboard.GetFileDropList();

                    foreach (String path in files)
                    {
                        foreach (RestrictedFile restrictedFile in Cache.restrictedFiles)
                        {
                            if (restrictedFile.FileSHA.Equals(DigestUtil.GetChecksum(path).ToLower()))
                            {
                                detectedFiles.Add(new DetectedRestrictedFile(restrictedFile, path));
                            }
                        }
                    }

                    AlertHandler.handle(detectedFiles, EventSource.CLIPBOARD);


                    //System.Windows.Forms.Clipboard.Clear();

               
            }

            var result = GeneralUtil.checkKeywordMatch(System.Windows.Forms.Clipboard.GetText().ToLower());
            if (result != null)
            {

                AlertHandler.handle(result, EventSource.CLIPBOARD);

            }
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

            GeneralUtil.logout();
        }

        private void toolExit_Click(object sender, EventArgs e)
        {
            GeneralUtil.applicationExit();
        }
    }
}
