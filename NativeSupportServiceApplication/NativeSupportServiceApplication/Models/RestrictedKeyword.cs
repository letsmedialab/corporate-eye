using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NativeSupportServiceApplication.Models
{
    public class RestrictedKeyword
    {
        private int id;
        private List<String> restrictedKeyword;
        private String restrictedRegex;
        private List<String> allowedGroupId;
        private List<String> allowedUserId;
        private String policyName;
        private String violatedMsg;

        public int Id { get => id; set => id = value; }
        public List<String> RKeyword { get => restrictedKeyword; set => restrictedKeyword = value; }
        public string RestrictedRegex { get => restrictedRegex; set => restrictedRegex = value; }
        public List<string> AllowedGroupId { get => allowedGroupId; set => allowedGroupId = value; }
        public List<string> AllowedUserId { get => allowedUserId; set => allowedUserId = value; }
        public string PolicyName { get => policyName; set => policyName = value; }

        public string ViolatedMsg { get => violatedMsg; set => violatedMsg = value; }
    }
}
