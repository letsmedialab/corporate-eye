using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security;
using System.Text;
using System.Threading.Tasks;

namespace NativeSupportServiceApplication.Dto
{
    internal class UserDto
    {
        
        private String userName;
        [JsonIgnore]
        private SecureString passwordSec = new SecureString();
        
        private String passWord;
        
        private String name;
        
        private Boolean isAuthenticated = false;

        [JsonProperty("userName")]
        public string UserName { get => userName; set => userName = value; }

        
        [JsonProperty("name")]
        public string Name { get => name; set => name = value; }


        [JsonProperty("isAuthenticated")]
        public bool IsAuthenticated { get => isAuthenticated; set => isAuthenticated = value; }
       
        [JsonIgnore]
        public SecureString PasswordSec { get => passwordSec; set => passwordSec = value; }

        [JsonProperty("passWord")]
        public string PassWord { get => passWord; set => passWord = value; }

        
        public override string ToString()
        {
            return userName + " " + passWord + " " + name + " " + isAuthenticated;
        }
    }
}
