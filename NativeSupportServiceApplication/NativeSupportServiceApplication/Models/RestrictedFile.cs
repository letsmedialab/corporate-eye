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
        private List<String> allowedGroupId;
        private List<String> allowedUserId;
        private String policyName;

        public int Id { get => id; set => id = value; }
        public string FileName { get => fileName; set => fileName = value; }
        public string FileSHA { get => fileSHA; set => fileSHA = value; }
        public List<string> AllowedGroupId { get => allowedGroupId; set => allowedGroupId = value; }
        public List<string> AllowedUserId { get => allowedUserId; set => allowedUserId = value; }
        public string PolicyName { get => policyName; set => policyName = value; }
    }
}
