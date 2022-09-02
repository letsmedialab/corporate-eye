using NativeSupportServiceApplication.Models;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NativeSupportServiceApplication.Utils
{
    public class GeneralUtil

    {
        public static bool IsKeyAChar(Keys key)
        {
            return key >= Keys.A && key <= Keys.Z || key >= Keys.A && key <= Keys.Z;
        }

        public static bool IsKeyASpecialCharacter(Keys key)
        {
            string specialChar = "\"@\\|!#$%&/()=?»«@£§€{}.-;'<>_,";
            List<char> characters = specialChar.ToList();

            return characters.Contains((char)key);

        }

        public static bool IsKeyADigit(Keys key)
        {
            return (key >= Keys.D0 && key <= Keys.D9) || (key >= Keys.NumPad0 && key <= Keys.NumPad9);
        }

        public static bool isOnlyWords(Keys key)
        { 
        
            return IsKeyAChar(key) || IsKeyADigit(key) || IsKeyASpecialCharacter(key);
        
        }

        public static void deleteFile(string fileName)
        {
            try
            {
                File.Delete(fileName);
            }
            catch
            {
            }
        }
        public static void terminateProcess(String processName)
        {
            try
            {
                Process[] workers = Process.GetProcessesByName(processName);
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

        public static void logout()
        {
            System.Environment.Exit(0);
        }
    }
}
