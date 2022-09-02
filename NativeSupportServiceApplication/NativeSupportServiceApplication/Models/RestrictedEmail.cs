using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NativeSupportServiceApplication.Models
{
    public class RestrictedEmail
    {
        private int id;
        private String email;
        private String policyName;

        public int Id { get => id; set => id = value; }
        public string Email { get => email; set => email = value; }
        public string PolicyName { get => policyName; set => policyName = value; }
    }
}
