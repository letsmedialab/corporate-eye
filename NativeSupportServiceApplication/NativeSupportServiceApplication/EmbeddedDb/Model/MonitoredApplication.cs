using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NativeSupportServiceApplication.Models
{
    public class MonitoredApplication
    {
        private long id;
        private String applicationName;
        private String applicationPath;
        private Int32 applicationRevision;
        private String policyName;
        private String violationMessage;
        public MonitoredApplication()
        { 
        }
        public MonitoredApplication(long id, string applicationName, string applicationPath, string policyName, int applicationRevision)
        {
            Id = id;
            ApplicationName = applicationName;
            ApplicationPath = applicationPath;
            PolicyName = policyName;
            ApplicationRevision = applicationRevision;
        }

        public long Id { get => id; set => id = value; }
        public string ApplicationName { get => applicationName; set => applicationName = value; }
        public string ApplicationPath { get => applicationPath; set => applicationPath = value; }
        public string PolicyName { get => policyName; set => policyName = value; }
        public int ApplicationRevision { get => applicationRevision; set => applicationRevision = value; }
        public string ViolationMessage { get => violationMessage; set => violationMessage = value; }
    }
}
