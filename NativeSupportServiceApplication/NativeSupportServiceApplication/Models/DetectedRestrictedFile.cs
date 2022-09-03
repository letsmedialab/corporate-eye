using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NativeSupportServiceApplication.Models
{
    public class DetectedRestrictedFile
    {
        RestrictedFile restrictedFile;
        String fileSystemPath;

        public DetectedRestrictedFile(RestrictedFile restrictedFile, string fileSystemPath)
        {
            this.restrictedFile = restrictedFile;
            this.fileSystemPath = fileSystemPath;
        }

        public RestrictedFile RestrictedFile { get => restrictedFile; set => restrictedFile = value; }
        public string FileSystemPath { get => fileSystemPath; set => fileSystemPath = value; }
    }
}
