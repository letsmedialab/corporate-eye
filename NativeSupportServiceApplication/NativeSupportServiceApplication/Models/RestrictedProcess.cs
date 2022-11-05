using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NativeSupportServiceApplication.Models
{
    public class RestrictedProcess
    {
        private int id;
        private String processName;
        private String policyName;

        public int Id { get => id; set => id = value; }
        public string ProcessName { get => processName; set => processName = value; }
        public string PolicyName { get => policyName; set => policyName = value; }
    }
}
