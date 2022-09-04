using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NativeSupportServiceApplication.Api
{
    internal class ApiEndPoints
    {
        public  const string USER_AUTH = "http://localhost:8080/api/v1/validateCredentials";
        public const String UPDATE_URL = "http://localhost:8080/api/v1/getUpdateHash?username=$1";

        public const String KEYWORD_CONFIG_URL = "http://localhost:8080/api/v1/getKeywordConfig?username=$1";
        public const String FILE_CONFIG_URL = "http://localhost:8080/api/v1/getFileConfig?username=$1";
        public const String URL_CONFIG_URL = "http://localhost:8080/api/v1/getUrlConfig?username=$1";
        public const String PROCESS_CONFIG_URL = "http://localhost:8080/api/v1/getProcessConfig?username=$1";
        public const String EMAIL_CONFIG_URL = "http://localhost:8080/api/v1/getEmailConfig?username=$1";

        public const String APP_CONFIG_URL = "http://localhost:8080/api/v1/getMonitoredApplicationConfig?username=$1";


    }
}
