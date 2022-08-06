using MySqlConnector;
using System.Diagnostics;
using NativeSupportServiceApplication.Modules;
using NativeSupportServiceApplication.Models;
using NativeSupportServiceApplication.Utils;

namespace NativeSupportServiceApplication
{
    public partial class LoadingSplash : Form
    {
        public LoadingSplash()
        {
            InitializeComponent();

            buildCache();

            startServices();

        }

        private void startServices()
        {
            var taskKeyMonitor = Task.Run(() => InterceptKeys.startMonitor());
            var taskClipboardMonitor = Task.Run(() => ClipboardMonitor.startMonitor());
            var taskProcessMonitor = Task.Run(() => ProcessMonitor.startMonitor()); 
            var taskFileMonitor = Task.Run(() => FileMonitor.startMonitor());
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


            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.ToString());
            }

            conn.Close();
            Debug.WriteLine("Done.");
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Console.WriteLine("sfas");
        }

        private void LoadingSplash_Load(object sender, EventArgs e)
        {

        }
    }
}