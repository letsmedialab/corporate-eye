
using NativeSupportServiceApplication.EmbeddedDb;
using NativeSupportServiceApplication.EmbeddedDb.Model;
using NativeSupportServiceApplication.Models;
using NativeSupportServiceApplication.Utils;
using System.Diagnostics;
using System.Runtime.InteropServices;

namespace NativeSupportServiceApplication.Modules
{

    public static class ApplicationMonitor
    {

        public static void startMonitor()
        {
            while (true)
            {

                //

                List<MonitoredApplication> alertFiles = new List<MonitoredApplication>();
                
                List<MonitoredApplication> currentApps = DbService.getMonitoredApplications();

                currentApps.ForEach(app =>
                {
                    List<FileInfoRecord> fileInfo = DbService.getFileRecords(app.PolicyName);

                    List<String> currentFiles = ApplicationMonitorUtil.listFiles(app.ApplicationPath);

                    string message = "";

                    Boolean add = false;

                    if (fileInfo.Count != currentFiles.Count)
                    {
                        message = "Critical file list changed";
                        add = true;
                    
                    }

                    List<FileInfoRecord> changedFiles = new List<FileInfoRecord>();

                    fileInfo.ForEach(fi => {
                        try
                        {

                            if (!DigestUtil.GetChecksum(fi.filePath).ToLower().Equals(fi.hashValue))
                            {
                                changedFiles.Add(fi);
                            }
                        }
                        catch
                        {
                            changedFiles.Add(fi);
                        }

                    });

                    if (changedFiles.Count > 0)
                    {
                        add = true;
                        if (changedFiles.Count > 1)
                        {
                            if (message.Length > 0)
                            {
                                message += " and multiple file's content has changed.";
                            }
                            else
                            {
                                message += "Multiple file's content has changed.";
                            }

                        }
                        else
                        {
                            if (message.Length > 0)
                            {
                                message += " and " + changedFiles[0].filePath + " content has been changed";
                            }
                            else
                            {
                                message += changedFiles[0].filePath + " content has been changed";
                            }
                        }
                    }


                    if (add)
                    {
                        app.ViolationMessage = message;
                        alertFiles.Add(app);
                    }


                });


               // AlertHandlerHelpers.flushExpiredNotifications(REventType.R_APP, alertFiles);


                //foreach (String file in currentFiles)
                //{
                //    foreach (RestrictedFile restrictedFile in Cache.restrictedFiles)
                //    {
                //        if (restrictedFile.FileSHA.Equals(DigestUtil.GetChecksum(file).ToLower()))
                //        {
                //            //restrictedFile.FileName = file;
                //            alertFiles.Add(new MonitoredApplication(restrictedFile,file));
                //        }
                //    }
                //}

                alertFiles.ForEach(e => AlertHandler.handle(e,EventSource.APP_MON));

                Thread.Sleep(10000);

            }

        }





        private static List<String> listFiles() => Directory.EnumerateFiles(@"E:\TestFolder", "*.*", SearchOption.AllDirectories).ToList();
    }
}
