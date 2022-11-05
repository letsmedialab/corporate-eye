using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NativeSupportServiceApplication.Utils
{
    internal class ApiFailureException : Exception
    {
        public ApiFailureException(String message) : base(message)
        {
           
        }

    }
}
