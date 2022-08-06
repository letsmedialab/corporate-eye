using System.Diagnostics;
using System.Runtime.InteropServices;

namespace NativeSupportServiceApplication.Modules
{
    using System;
    using System.Windows.Forms;
    using System.Runtime.InteropServices;
    using System.Text;
    using System.Threading;
    using NativeSupportServiceApplication.Utils;
    using NativeSupportServiceApplication.Models;

    class ProcessMonitor {

        public static void startMonitor()
        {

            while (true)
            {
             
                foreach (RestrictedProcess process in Cache.restrictedProcesses)
                {
                    if (listProcesses().Contains(process.ProcessName))
                    {
                        AlertHandler.handle(process);
                    }
                }
               

                Thread.Sleep(5000);
            }

        }

        private static List<String> listProcesses() => Process.GetProcesses().Select(process=>process.ProcessName).ToList();
    }
}
