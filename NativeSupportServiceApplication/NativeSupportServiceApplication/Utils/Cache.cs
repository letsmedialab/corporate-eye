using NativeSupportServiceApplication.Api;
using NativeSupportServiceApplication.Dto;
using NativeSupportServiceApplication.Models;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NativeSupportServiceApplication.Utils
{
    public static class Cache
    {
        public static List<RestrictedKeyword> restrictedKeywords = new List<RestrictedKeyword>();
        public static List<RestrictedFile> restrictedFiles = new List<RestrictedFile>();
        public static List<RestrictedUrl> restrictedUrls = new List<RestrictedUrl>();
        public static List<RestrictedProcess> restrictedProcesses = new List<RestrictedProcess>();
        public static List<RestrictedEmail> restrictedEmail = new List<RestrictedEmail>();
        public static Dictionary<String, String> ruleVersionMap = new Dictionary<string, string>();

        public static String hashProcess = "";
        public static String hashFiles = "";
        public static String hashUrl = "";
        public static String hashEmail = "";
        public static String hashKeyword = "";

        static Cache()
        {
            
            ruleVersionMap.Add(RuleId.URL.ToString(),"");
            ruleVersionMap.Add(RuleId.KEYWORD.ToString(), "");
            ruleVersionMap.Add(RuleId.FILE.ToString(), "");
            ruleVersionMap.Add(RuleId.PROCESS.ToString(), "");
            ruleVersionMap.Add(RuleId.EMAIL.ToString(), "");
        }
        public static void rebuildRuleCache()
        {

        
            try
            {
             
                ApiResponse<Dictionary<String, string>> response = ApiUtils.callApi<Dictionary<String, string>, String>(ApiEndPoints.UPDATE_URL.Replace("$1", GlobalConstants.getCurrentUser().UserName), HttpMethod.Get, null);

                if (response.ResponseCode != 200)
                {
                   
                    throw new ApiFailureException("Failed at rule check");
                }
                Dictionary<String, string> newRuleVersions = response.ResponseObject;

                // String version = "";

                String currentRuleKey = RuleId.KEYWORD.ToString();
                String newVersion, currentVersion;

                if (newRuleVersions.TryGetValue(currentRuleKey, out newVersion ))
                {
                   
                    ruleVersionMap.TryGetValue(currentRuleKey, out  currentVersion);

                    if (!newVersion.Equals(currentVersion))
                    {
                        ApiResponse<List<RestrictedKeyword>> keywordAPIResponse = ApiUtils.callApi<List<RestrictedKeyword>, Object>(ApiEndPoints.KEYWORD_CONFIG_URL.Replace("$1", GlobalConstants.getCurrentUser().UserName), HttpMethod.Get, null);

                        Cache.restrictedKeywords = keywordAPIResponse.ResponseObject;

                        ruleVersionMap[currentRuleKey] =newVersion;

                    }


                }

                currentRuleKey = RuleId.PROCESS.ToString();

                if (newRuleVersions.TryGetValue(currentRuleKey, out newVersion))
                {

                    ruleVersionMap.TryGetValue(currentRuleKey, out  currentVersion);

                    if (!newVersion.Equals(currentVersion))
                    {
                        ApiResponse<List<RestrictedProcess>> keywordAPIResponse = ApiUtils.callApi<List<RestrictedProcess>, Object>(ApiEndPoints.PROCESS_CONFIG_URL.Replace("$1", GlobalConstants.getCurrentUser().UserName), HttpMethod.Get, null);

                        Cache.restrictedProcesses = keywordAPIResponse.ResponseObject;

                        ruleVersionMap[currentRuleKey] =newVersion;

                    }


                }

                currentRuleKey = RuleId.FILE.ToString();

                if (newRuleVersions.TryGetValue(currentRuleKey, out newVersion))
                {

                    ruleVersionMap.TryGetValue(currentRuleKey, out currentVersion);

                    if (!newVersion.Equals(currentVersion))
                    {
                        ApiResponse<List<RestrictedFile>> keywordAPIResponse = ApiUtils.callApi<List<RestrictedFile>, Object>(ApiEndPoints.FILE_CONFIG_URL.Replace("$1", GlobalConstants.getCurrentUser().UserName), HttpMethod.Get, null);

                        Cache.restrictedFiles = keywordAPIResponse.ResponseObject;

                        ruleVersionMap[currentRuleKey] =newVersion;

                    }


                }

                currentRuleKey = RuleId.EMAIL.ToString();

                if (newRuleVersions.TryGetValue(currentRuleKey, out newVersion))
                {

                    ruleVersionMap.TryGetValue(currentRuleKey, out currentVersion);

                    if (!newVersion.Equals(currentVersion))
                    {
                        ApiResponse<List<RestrictedEmail>> keywordAPIResponse = ApiUtils.callApi<List<RestrictedEmail>, Object>(ApiEndPoints.EMAIL_CONFIG_URL.Replace("$1", GlobalConstants.getCurrentUser().UserName), HttpMethod.Get, null);

                        Cache.restrictedEmail = keywordAPIResponse.ResponseObject;

                        ruleVersionMap[currentRuleKey] =newVersion;

                    }


                }

                currentRuleKey = RuleId.URL.ToString();

                if (newRuleVersions.TryGetValue(currentRuleKey, out newVersion))
                {

                    ruleVersionMap.TryGetValue(currentRuleKey, out currentVersion);

                    if (!newVersion.Equals(currentVersion))
                    {
                        ApiResponse<List<RestrictedUrl>> keywordAPIResponse = ApiUtils.callApi<List<RestrictedUrl>, Object>(ApiEndPoints.FILE_CONFIG_URL.Replace("$1", GlobalConstants.getCurrentUser().UserName), HttpMethod.Get, null);

                        Cache.restrictedUrls = keywordAPIResponse.ResponseObject;

                        ruleVersionMap[currentRuleKey] =newVersion;

                    }


                }

                

            }
            catch (Exception ex)
            {
                string message = "Program will exit.\nError:\n" + ex.StackTrace;
                string title = "Critical Error.";
                MessageBoxButtons buttons = MessageBoxButtons.OK;
                DialogResult result = MessageBox.Show(message, title, buttons, MessageBoxIcon.Error);
                Debug.WriteLine(ex.ToString());
                Application.Exit();


            }

          

        }


    }
}
