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
        public static String UPDATE_URL { get; } = "http://localhost:8080/api/v1/getUpdateHash?username=$1";

        public static String KEYWORD_CONFIG_URL { get; } = "http://localhost:8080/api/v1/getKeywordConfig?username=$1";
        public static String FILE_CONFIG_URL { get; } = "http://localhost:8080/api/v1/getFileConfig?username=$1";
        public static String URL_CONFIG_URL { get; } = "http://localhost:8080/api/v1/getUrlConfig?username=$1";
        public static String PROCESS_CONFIG_URL { get; } = "http://localhost:8080/api/v1/getProcessConfig?username=$1";
        public static String EMAIL_CONFIG_URL { get; } = "http://localhost:8080/api/v1/getEmailConfig?username=$1";


    }
}
