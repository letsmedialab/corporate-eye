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

                List<DetectedRestrictedFile> alertFiles = new List<DetectedRestrictedFile>();
                List<String> currentFiles = listFiles();

                AlertHandlerHelpers.flushExpiredNotifications(REventType.R_FILE, currentFiles);


                foreach (String file in currentFiles)
                {
                    foreach (RestrictedFile restrictedFile in Cache.restrictedFiles)
                    {
                        if (restrictedFile.FileSHA.Equals(DigestUtil.GetChecksum(file).ToLower()))
                        {
                            //restrictedFile.FileName = file;
                            alertFiles.Add(new DetectedRestrictedFile(restrictedFile,file));
                        }
                    }
                }

                alertFiles.ForEach(e => AlertHandler.handle(alertFiles,EventSource.FILE_SCAN));

                Thread.Sleep(10000);

            }

        }





        private static List<String> listFiles() => Directory.EnumerateFiles(@"E:\TestFolder", "*.*", SearchOption.AllDirectories).ToList();
    }
}
