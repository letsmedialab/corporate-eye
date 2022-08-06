using NativeSupportServiceApplication.Models;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NativeSupportServiceApplication.Utils
{
    public class AlertHandler
    {
        private static List<String> waitingProcessDialogs = new List<string>();
        private static List<String> waitingRFileDialogs = new List<string>();
        public static void handle( RestrictedProcess restrictedProcess)
        {
            if(!waitingProcessDialogs.Contains("rprocess_" + restrictedProcess.ProcessName))
            new Task(() => {
                DialogResult d;
                waitingProcessDialogs.Add("rprocess_" + restrictedProcess.ProcessName);

                d = MessageBox.Show("Running process " + restrictedProcess.ProcessName + " violates company policy " + restrictedProcess.PolicyName + " Do you want to terminate?", "Restricted Process Detected", MessageBoxButtons.YesNo, MessageBoxIcon.Information);
                
                if (d == DialogResult.Yes)
                {
                    try
                    {
                        Process[] workers = Process.GetProcessesByName(restrictedProcess.ProcessName);
                        foreach (Process worker in workers)
                        {
                            worker.Kill();
                            worker.WaitForExit();
                            worker.Dispose();
                        }
                    }
                    catch (Exception e)
                    {
                        Debug.Write(e);
                    }
                }
                waitingProcessDialogs.Remove("rprocess_" + restrictedProcess.ProcessName);
            }).Start();
            
        }

        public static void handle(RestrictedFile restrictedFile)
        {
            if (!waitingRFileDialogs.Contains("rfile_" + restrictedFile.FileSHA))
                new Task(() => {
                    DialogResult d;
                    waitingRFileDialogs.Add("rfile_" + restrictedFile.FileSHA);

                    d = MessageBox.Show("Posession of file " + restrictedFile.FileName + " violates company policy " + restrictedFile.PolicyName + " Do you want to delete the file?", "Restricted File Detected", MessageBoxButtons.YesNo, MessageBoxIcon.Information);

                    if (d == DialogResult.Yes)
                    {
                        try
                        {
                            File.Delete(restrictedFile.FileName);
                        }
                        catch (Exception e)
                        {
                            Debug.Write(e);
                        }
                    }
                    waitingRFileDialogs.Remove("rfile_" + restrictedFile.FileSHA);
                }).Start();

        }


    }
}
