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
        public LoadingSplash()
        {
            InitializeComponent();



        }

        private void startServices()
        {
            var taskKeyMonitor = Task.Run(() => InterceptKeys.startMonitor());
            var taskClipboardMonitor = Task.Run(() => ClipboardMonitor.startMonitor());
            var taskProcessMonitor = Task.Run(() => ProcessMonitor.startMonitor());
            var taskFileMonitor = Task.Run(() => FileMonitor.startMonitor());
            var iPC = Task.Run(() => Icom.startListner());
        }

        private void buildCache()
        {
            string connStr = "server=localhost;user=root;database=cumpws;port=3306;password=Login!@34";
            MySqlConnection conn = new MySqlConnection(connStr);
            try
            {
                Debug.WriteLine("Connecting to MySQL...");
                conn.Open();

                //loading process 
                Debug.WriteLine("loading restricted process ...");

                string sql = "SELECT * from restrictedProcess";
                MySqlCommand cmd = new MySqlCommand(sql, conn);
                MySqlDataReader rdr = cmd.ExecuteReader();

                while (rdr.Read())
                {
                    RestrictedProcess rp = new RestrictedProcess();
                    rp.Id = Int32.Parse(rdr[0].ToString());
                    rp.ProcessName = rdr[1].ToString();
                    try { }
                    catch (Exception ex)
                    {
                        rp.AllowedGroupId = rdr[2].ToString().Split(",").ToList();
                    }
                    try { }
                    catch (Exception ex)
                    {
                        rp.AllowedUserId = rdr[3].ToString().Split(",").ToList();
                    }


                    rp.PolicyName = rdr[4].ToString();

                    Cache.restrictedProcesses.Add(rp);

                }
                rdr.Close();
            
                //loading restricted files 
                Debug.WriteLine("loading restricted files ...");

                sql = "SELECT * from restrictedFiles";
                cmd = new MySqlCommand(sql, conn);
                rdr = cmd.ExecuteReader();

                while (rdr.Read())
                {
                    RestrictedFile rf = new RestrictedFile();
                    rf.Id = Int32.Parse(rdr[0].ToString());
                    rf.FileName = rdr[1].ToString();
                    rf.FileSHA = rdr[2].ToString().ToUpper();
                    try { }
                    catch (Exception ex)
                    {
                        rf.AllowedGroupId = rdr[3].ToString().Split(",").ToList();
                    }
                    try { }
                    catch (Exception ex)
                    {
                        rf.AllowedUserId = rdr[4].ToString().Split(",").ToList();
                    }


                    rf.PolicyName = rdr[5].ToString();

                    Cache.restrictedFiles.Add(rf);

                }
                rdr.Close();

                //loading restricted keywords 
                Debug.WriteLine("loading restricted keywords ...");

                sql = "SELECT * from restrictedkeywords";
                cmd = new MySqlCommand(sql, conn);
                rdr = cmd.ExecuteReader();

                while (rdr.Read())
                {
                    RestrictedKeyword rf = new RestrictedKeyword();
                    rf.Id = Int32.Parse(rdr[0].ToString());
                   
                    String keywordString = rdr[1].ToString().Replace("\\,", "!@27$");

                    rf.RKeyword = new List<string>(keywordString.Split(","));

                    List<String> newList = new List<string>();

                    rf.RKeyword.ForEach(e => newList.Add(e.Replace("!@27$", ",").Trim()));
                    rf.RKeyword = newList;

                    rf.RestrictedRegex = rdr[2].ToString().ToUpper();
                    try { }
                    catch (Exception ex)
                    {
                        rf.AllowedGroupId = rdr[3].ToString().Split(",").ToList();
                    }
                    try { }
                    catch (Exception ex)
                    {
                        rf.AllowedUserId = rdr[4].ToString().Split(",").ToList();
                    }


                    rf.PolicyName = rdr[5].ToString();

                    Cache.restrictedKeywords.Add(rf);

                }

                Debug.WriteLine(Cache.restrictedKeywords);
                rdr.Close();

            }
            catch (Exception ex)
            {
                string message = "Program will exit.\nError:\n" + ex.StackTrace;
                string title = "Critical Error.";
                MessageBoxButtons buttons = MessageBoxButtons.OK;
                DialogResult result = MessageBox.Show(message, title, buttons,MessageBoxIcon.Error);
                Debug.WriteLine(ex.ToString());
                Application.Exit();              

               
            }

            conn.Close();
            Debug.WriteLine("Done.");
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Console.WriteLine("sfas");
        }

        Form currentForm ;

        private void LoadingSplash_Load(object sender, EventArgs e)
        {
            buildCache();

            startServices();
            currentForm = this;
            Debug.WriteLine("staring work");
            
            backgroundWorker1.RunWorkerAsync();


        }
        private void backgroundWorker1_DoWork(object sender, DoWorkEventArgs e)
        {
            Debug.WriteLine("sleeping");
            Thread.Sleep(5000);
            Debug.WriteLine("awake");
            Debug.WriteLine("closing form");
         
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