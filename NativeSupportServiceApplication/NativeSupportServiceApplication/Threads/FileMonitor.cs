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
                //

                List<RestrictedFile> alertFiles = new List<RestrictedFile>();
                List<String> currentFiles = listFiles();

                AlertHandlerHelpers.flushExpiredNotifications(REventType.R_FILE, currentFiles);


                foreach (String file in currentFiles)
                {
                    foreach (RestrictedFile restrictedFile in Cache.restrictedFiles)
                    {
                        if (restrictedFile.FileSHA.Equals(ChecksumUtil.GetChecksum(file).ToUpper()))
                        {
                            restrictedFile.FileName = file;
                            alertFiles.Add(restrictedFile);
                        }
                    }
                }

                alertFiles.ForEach(e => AlertHandler.handle(e));

                Thread.Sleep(10000);

            }

        }





        private static List<String> listFiles() => Directory.EnumerateFiles(@"E:\TestFolder", "*.*", SearchOption.AllDirectories).ToList();
    }
}
