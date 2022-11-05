using Newtonsoft.Json;
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
        private List<String> restrictedKeywords;
        private String restrictedRegex;
        private String policyName;
        private String violatedMsg;

        public int Id { get => id; set => id = value; }

        [JsonProperty("restrictedKeyword")]
        public List<String> RestrictedKeywords { get => restrictedKeywords; set => restrictedKeywords = value; }
        public string RestrictedRegex { get => restrictedRegex; set => restrictedRegex = value; }
        public string PolicyName { get => policyName; set => policyName = value; }

        public string ViolatedMsg { get => violatedMsg; set => violatedMsg = value; }
    }
}
