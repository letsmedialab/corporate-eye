using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using XSystem.Security.Cryptography;

namespace NativeSupportServiceApplication.Utils
{
    public static class DigestUtil
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

        public static string SHA(string stringToHash)
        {
            using (var sha1 = new SHA1Managed())
            {
                return BitConverter.ToString(sha1.ComputeHash(Encoding.UTF8.GetBytes(stringToHash)));
            }
        }

    }
}
