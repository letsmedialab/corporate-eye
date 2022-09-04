using NativeSupportServiceApplication.EmbeddedDb;
using NativeSupportServiceApplication.EmbeddedDb.Model;
using NativeSupportServiceApplication.Models;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NativeSupportServiceApplication.Utils
{
    internal class ApplicationMonitorUtil
    {
        public static void checkAndUpdateLocalDatabase()
        {
            Cache.ruleVersionMap.TryGetValue(RuleId.APP.ToString(),  out string currentHash);


            List<MonitoredApplication> applicationList =  DbService.getMonitoredApplications();

            Debug.WriteLine("api Hash" + currentHash);

            Debug.WriteLine("current Hash" + hashMonitoredAppCollection(applicationList));

            //add new apps
            List<MonitoredApplication>  appBuffer = new List<MonitoredApplication>();
            Cache.appConfig.ForEach(app => {
                if (!applicationList.Exists(q => q.Id == app.Id))
                { 
                    appBuffer.Add(app);
                }
            });
            

            addApplicationToFileInfo(appBuffer);
            

            //remove outdated

            applicationList = DbService.getMonitoredApplications();


            appBuffer.Clear();

            applicationList.ForEach(app => {
                if (!Cache.appConfig.Exists(q => q.Id == app.Id))
                {
                    appBuffer.Add(app);
                }
            });

            removeApplicationFromFileInfo(appBuffer);

           

            appBuffer.Clear();

            //update new hash

            applicationList = DbService.getMonitoredApplications();

            //check version
            Cache.appConfig.ForEach(app => {
                if (applicationList.Exists(q => q.Id == app.Id && q.ApplicationRevision!= app.ApplicationRevision))
                {
                    appBuffer.Add(app);
                }
            });

            updateApplicationFileInfo(appBuffer);

            applicationList = DbService.getMonitoredApplications();

            string temphash = hashMonitoredAppCollection(applicationList);

           
            Cache.ruleVersionMap[RuleId.APP.ToString()] = temphash;

            Cache.ruleVersionMap.TryGetValue(RuleId.APP.ToString(), out string newHash);

            Debug.WriteLine("new Hash" + newHash);

            Debug.WriteLine("done");


        }

        private static void updateApplicationFileInfo(List<MonitoredApplication> appBuffer)
        {

            addOrUpdateFileRecord(appBuffer);

            DbService.updateMonitoredApplication(appBuffer);
           
        }

        private static void addOrUpdateFileRecord(List<MonitoredApplication> appBuffer)
        {
            appBuffer.ForEach(app => {

                List<FileInfoRecord> files = new List<FileInfoRecord>();

                listFiles(app.ApplicationPath).ForEach(f => {
                    Debug.WriteLine("adding or updaing " + app.PolicyName + " file " + f );
                    FileInfoRecord fileinfo = new FileInfoRecord();
                    fileinfo.hashValue = DigestUtil.GetChecksum(f).ToLower();
                    fileinfo.policyName = app.PolicyName;
                    fileinfo.filePath = f;
                    fileinfo.programName = app.ApplicationName;
                    files.Add(fileinfo);


                });


                DbService.insertFileRecord(files, app.PolicyName);
            });
        }

        private static void removeApplicationFromFileInfo(List<MonitoredApplication> appBuffer)
        {
            // throw new NotImplementedException();
            appBuffer.ForEach(app => {

                Debug.WriteLine("removing " + app.PolicyName +" files");
                DbService.deleteFileRecord(app.PolicyName);
            });


            DbService.removeMonitoredApplication(appBuffer);
        }

        private static void addApplicationToFileInfo(List<MonitoredApplication> appBuffer)
        {
            // throw new NotImplementedException();
            addOrUpdateFileRecord(appBuffer);
            DbService.insertMonitoredApplication(appBuffer);
        }

        private static string hashMonitoredAppCollection(List<MonitoredApplication> applicationList)
        {
            StringBuilder sb = new StringBuilder();
            applicationList.ForEach(x => { sb.Append(x.ApplicationRevision + ""); });
            return DigestUtil.SHA(sb.ToString()+GlobalConstants.getCurrentUser().UserName).ToLower().Replace("-","");
        }


        public static List<String> listFiles(String path) => Directory.EnumerateFiles(path, "*.*", SearchOption.AllDirectories).Where(file => file.EndsWith(".dll") || file.EndsWith(".exe") || file.EndsWith(".ini") || file.EndsWith(".sig") || file.Contains("conf") || file.Contains("preference")).ToList();
    }
}
