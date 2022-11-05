using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NativeSupportServiceApplication.Api
{
    internal class ApiEndPoints
    {
        public static string SERVER_ADDRESS;
        public static string PORT;

        public static string USER_AUTH = "http://" + SERVER_ADDRESS + ":" + PORT + "/api/v1/validateCredentials";
        public static String UPDATE_URL = "http://" + SERVER_ADDRESS + ":" + PORT + "/api/v1/getUpdateHash?username=$1";

        public static String KEYWORD_CONFIG_URL = "http://" + SERVER_ADDRESS + ":" + PORT + "/api/v1/getKeywordConfig?username=$1";
        public static String FILE_CONFIG_URL = "http://" + SERVER_ADDRESS + ":" + PORT + "/api/v1/getFileConfig?username=$1";
        public static String URL_CONFIG_URL = "http://" + SERVER_ADDRESS + ":" + PORT + "/api/v1/getUrlConfig?username=$1";
        public static String PROCESS_CONFIG_URL = "http://" + SERVER_ADDRESS + ":" + PORT + "/api/v1/getProcessConfig?username=$1";
        public static String EMAIL_CONFIG_URL = "http://" + SERVER_ADDRESS + ":" + PORT + "/api/v1/getEmailConfig?username=$1";

        public static String APP_CONFIG_URL = "http://" + SERVER_ADDRESS + ":" + PORT + "/api/v1/getMonitoredApplicationConfig?username=$1";

        public static String UPLOAD_URL = "http://" + SERVER_ADDRESS + ":" + PORT + "/api/v1/uploadFile";

        static ApiEndPoints()
        {
            try
            {
                RegistryKey key = Registry.CurrentUser.OpenSubKey(@"SOFTWARE\CorporateEye");
                var value = key.GetValue("hostname");

                if (value != null)
                    SERVER_ADDRESS = value.ToString();

                var value2 = key.GetValue("port");

                if (value2 != null)
                    PORT = value2.ToString();

            }
            catch (Exception ex)
            {
                //RegistryKey key = Registry.CurrentUser.CreateSubKey(@"SOFTWARE\CorporateEye");
                //key.SetValue("hostname", SERVER_ADDRESS);
                //key.SetValue("port", PORT);
                //key.Close();
                Debug.WriteLine(ex);

            }
            USER_AUTH = "http://" + SERVER_ADDRESS + ":" + PORT + "/api/v1/validateCredentials";
            UPDATE_URL = "http://" + SERVER_ADDRESS + ":" + PORT + "/api/v1/getUpdateHash?username=$1";

            KEYWORD_CONFIG_URL = "http://" + SERVER_ADDRESS + ":" + PORT + "/api/v1/getKeywordConfig?username=$1";
            FILE_CONFIG_URL = "http://" + SERVER_ADDRESS + ":" + PORT + "/api/v1/getFileConfig?username=$1";
            URL_CONFIG_URL = "http://" + SERVER_ADDRESS + ":" + PORT + "/api/v1/getUrlConfig?username=$1";
            PROCESS_CONFIG_URL = "http://" + SERVER_ADDRESS + ":" + PORT + "/api/v1/getProcessConfig?username=$1";
            EMAIL_CONFIG_URL = "http://" + SERVER_ADDRESS + ":" + PORT + "/api/v1/getEmailConfig?username=$1";

            APP_CONFIG_URL = "http://" + SERVER_ADDRESS + ":" + PORT + "/api/v1/getMonitoredApplicationConfig?username=$1";

            UPLOAD_URL = "http://" + SERVER_ADDRESS + ":" + PORT + "/api/v1/uploadFile";


        }


    }
}
