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

                List<RestrictedProcess> alertProcesses =  new List<RestrictedProcess>();
                List<String> currentProcess = listProcesses();

                AlertHandlerHelpers.flushExpiredNotifications(REventType.R_PROCESS,currentProcess);

                Cache.restrictedProcesses.ForEach(e=> { if (currentProcess.Contains(e.ProcessName)) { alertProcesses.Add(e); } });

                alertProcesses.ForEach(e => AlertHandler.handle(e));               

                Thread.Sleep(10000);
            }

        }

        private static List<String> listProcesses() => Process.GetProcesses().Select(process=>process.ProcessName).ToList();
    }
}
