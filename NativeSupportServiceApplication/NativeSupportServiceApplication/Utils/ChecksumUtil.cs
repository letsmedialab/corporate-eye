using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NativeSupportServiceApplication.Utils
{
    public static class ChecksumUtil
    {
        public static string GetChecksum(string filename)
        {
            using (var hasher = System.Security.Cryptography.HashAlgorithm.Create("SHA1"))
            {
                using (var stream = System.IO.File.OpenRead(filename))
                {
                    var hash = hasher.ComputeHash(stream);
                    return BitConverter.ToString(hash).Replace("-", "");
                }
            }
        }

    }
}
