using NativeSupportServiceApplication.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NativeSupportServiceApplication.Utils
{
    public static class Cache
    {
        public static List<RestrictedProcess> restrictedProcesses = new List<RestrictedProcess>();
        public static List<RestrictedFile> restrictedFiles = new List<RestrictedFile>();
    }
}
