using NativeSupportServiceApplication.Models;
using NativeSupportServiceApplication.Utils;
using System.Diagnostics;
using System.Runtime.InteropServices;

namespace NativeSupportServiceApplication.Modules
{

    public static class FileMonitor
    {

        public static void startMonitor()
        {
            while (true)
            {

                List<String> files = listFiles();


                foreach (String file in files)
                {
                    foreach (RestrictedFile restrictedFile in Cache.restrictedFiles)
                    {
                        if (restrictedFile.FileSHA.Equals(ChecksumUtil.GetChecksum(file).ToUpper()))
                        {
                            restrictedFile.FileName = file;
                            AlertHandler.handle(restrictedFile) ;
                        }
                    }
                }

                Thread.Sleep(10000);
            }
           
        }


        
     

        private static List<String> listFiles() => Directory.EnumerateFiles(@"E:\TestFolder", "*.*", SearchOption.AllDirectories).ToList();
    }
}
