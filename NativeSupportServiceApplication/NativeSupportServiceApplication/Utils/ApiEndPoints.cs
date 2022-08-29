using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NativeSupportServiceApplication.Api
{
    internal class ApiEndPoints
    {
        public static string USER_AUTH { get; } = "http://localhost:8080/api/v1/validateCredentials";
    }
}
