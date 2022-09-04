using LiteDB;
using NativeSupportServiceApplication.EmbeddedDb.Model;
using NativeSupportServiceApplication.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NativeSupportServiceApplication.EmbeddedDb
{
    internal class DbService
    {
        const String connString = "ProcessDb.db";
        static DbService()
        {
            using (var db = new LiteDatabase(connString))
            {
                db.GetCollection<FileInfoRecord>("fileInfo");
                db.GetCollection<MonitoredApplication>("monitoredApplication");
                // collection.EnsureIndex("PolicyName");
            }
        }

        public static void insertFileRecord(List<FileInfoRecord> records, string policyName)
        {
            using (var db = new LiteDatabase(connString))
            {
                var collection = db.GetCollection<FileInfoRecord>("fileInfo");
                collection.DeleteMany(m => m.policyName.Equals(policyName));
                collection.InsertBulk(records);
            }
        }

        public static void deleteFileRecord( string policyName)
        {
            using (var db = new LiteDatabase(connString))
            {
                var collection = db.GetCollection<FileInfoRecord>("fileInfo");
                collection.DeleteMany(m => m.policyName.Equals(policyName));
               
            }
        }

        public static List<FileInfoRecord> getFileRecords(string policyName)
        {
            using (var db = new LiteDatabase(connString))
            {
                var collection = db.GetCollection<FileInfoRecord>("fileInfo");

                return collection.Query().Where(x => x.policyName.Equals(policyName)).ToList();
            }
        }

        public static void insertMonitoredApplication(List<MonitoredApplication> records)
        {
            using (var db = new LiteDatabase(connString))
            {
                var collection = db.GetCollection<MonitoredApplication>("monitoredApplication");
               // collection.DeleteAll();
                collection.InsertBulk(records);
            }
        }

        public static List<MonitoredApplication> getMonitoredApplications()
        {
            using (var db = new LiteDatabase(connString))
            {
                var collection = db.GetCollection<MonitoredApplication>("monitoredApplication");

                return collection.Query().OrderBy(x => x.Id).ToList();
            }
        }

        internal static void removeMonitoredApplication(List<MonitoredApplication> appBuffer)
        {
            using (var db = new LiteDatabase(connString))
            {
                var collection = db.GetCollection<MonitoredApplication>("monitoredApplication");
                // collection.DeleteAll();
                collection.DeleteMany(m => appBuffer.Select(a=> a.Id).ToList().Contains(m.Id));
            }
        }

        internal static void updateMonitoredApplication(List<MonitoredApplication> appBuffer)
        {
            using (var db = new LiteDatabase(connString))
            {
                var collection = db.GetCollection<MonitoredApplication>("monitoredApplication");
                // collection.DeleteAll();
                appBuffer.ForEach(app => {
                    collection.Update(app);
                });
            }
        }
    }
}
