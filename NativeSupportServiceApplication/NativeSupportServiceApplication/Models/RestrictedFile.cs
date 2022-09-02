using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NativeSupportServiceApplication.Models
{
    public class RestrictedFile
    {
        private int id;
        private String fileName;
        private String fileSHA;
        private String policyName;

        public int Id { get => id; set => id = value; }
        public string FileName { get => fileName; set => fileName = value; }
        public string FileSHA { get => fileSHA; set => fileSHA = value; }
    
        public string PolicyName { get => policyName; set => policyName = value; }
    }
}
