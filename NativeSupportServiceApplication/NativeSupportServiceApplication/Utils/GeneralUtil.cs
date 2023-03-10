using NativeSupportServiceApplication.Models;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using XAct;

namespace NativeSupportServiceApplication.Utils
{
    public class GeneralUtil

    {
        public static RestrictedKeyword checkKeywordMatch(String currentKey)
        {

            foreach (RestrictedKeyword rkeyword in Cache.restrictedKeywords)
            {
                foreach (String key in rkeyword.RestrictedKeywords)
                {
                    if (currentKey.ToLower().Contains(" " + key.ToLower() + " ") || currentKey.ToLower().Equals(key.ToLower()) || currentKey.ToLower().StartsWith(key.ToLower()) || currentKey.ToLower().EndsWith(key.ToLower()))
                    {
                        rkeyword.ViolatedMsg = " Restricted Keyword " + key;
                        return rkeyword;
                    }
                }
            }

            return null;
        }


        public static RestrictedUrl checkUrlMatch(String currentKey)
        {

            foreach (RestrictedUrl rUrl in Cache.restrictedUrls)
            {
                foreach (String key in rUrl.Url)
                {

                    //if (key.Contains("*"))
                    //{
                 
                        
                    //    var urlComp = key.Split("*");

                    //    if (urlComp.Length == 1)
                    //    { 
                        
                    //    }
                    
                    //}

                    if (currentKey.ToLower().Contains(key.ToLower()) || currentKey.ToLower().Equals(key.ToLower()) || currentKey.ToLower().StartsWith(key.ToLower()) || currentKey.ToLower().EndsWith(key.ToLower()))
                    {
                        rUrl.ViolatedMsg = " Restricted Url " + key;
                        return rUrl;
                    }
                }
            }

            return null;
        }

        public static RestrictedEmail checkEmailMatch(String currentKey)
        {

            foreach (RestrictedEmail rEmail in Cache.restrictedEmail)
            {
                foreach (String key in rEmail.Email)
                {

                    //if (key.Contains("*"))
                    //{


                    //    var urlComp = key.Split("*");

                    //    if (urlComp.Length == 1)
                    //    { 

                    //    }

                    //}

                    if (currentKey.ToLower().Contains(" " + key.ToLower() + " ") || currentKey.ToLower().Contains("\"" + key.ToLower() + "\"") || currentKey.ToLower().Equals(key.ToLower()) || currentKey.ToLower().StartsWith(key.ToLower()) || currentKey.ToLower().EndsWith(key.ToLower()))
                    {
                        rEmail.ViolatedMsg = " Restricted Email " + key;
                        return rEmail;
                    }
                }
            }

            return null;
        }



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

            // Prepare the process to run
            ProcessStartInfo start = new ProcessStartInfo();

            // Enter the executable to run, including the complete path
            start.FileName = System.Diagnostics.Process.GetCurrentProcess().MainModule.FileName;
            // Do you want to show a console window?
            start.WindowStyle = ProcessWindowStyle.Hidden;
            start.CreateNoWindow = true;
            int exitCode;


            // Run the external process & wait for it to finish
            Process proc = Process.Start(start);


            GeneralUtil.applicationExit();
        }

        internal static void applicationExit()
        {
            System.Environment.Exit(0);
           
        }
    }
}
