using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NativeSupportServiceApplication.Dto
{
    public class FileUploadResponse
    {
        private string fileName;
        private string fileDownloadUri;
        private string fileType;
        private string fileSize;

        [JsonProperty("fileName")]
        public string FileName { get => fileName; set => fileName = value; }
        [JsonProperty("fileDownloadUri")]
        public string FileDownloadUri { get => fileDownloadUri; set => fileDownloadUri = value; }
        [JsonProperty("fileType")]
        public string FileType { get => fileType; set => fileType = value; }
        [JsonProperty("fileSize")]
        public string FileSize { get => fileSize; set => fileSize = value; }
    }
}
