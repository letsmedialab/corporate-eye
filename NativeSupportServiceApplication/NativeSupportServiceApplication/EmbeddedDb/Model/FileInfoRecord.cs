using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NativeSupportServiceApplication.EmbeddedDb.Model
{
    internal class FileInfoRecord
    {
  

        public string policyName { get; set; }
        public string programName { get; set; }

        public string filePath { get; set; }

        public string hashValue { get; set; }
    }
}
