using NativeSupportServiceApplication.Models;
using NativeSupportServiceApplication.Utils;
using System.Collections.Concurrent;
using System.Diagnostics;
using System.Runtime.InteropServices;
using System.Text;

namespace NativeSupportServiceApplication.Modules
{

    class UpdateChecker
    {
        

   
        public static void startMonitor()
        {

            while (true)
            {
                Debug.WriteLine("Updating RuleSet");

                Cache.rebuildRuleCache();

                Thread.Sleep(60000);

                Debug.WriteLine("Updated RuleSet");
            }

        }




    }
}