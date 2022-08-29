using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net.Http;
using System.Net;
using NativeSupportServiceApplication.Dto;
using Newtonsoft.Json;
using NativeSupportServiceApplication.Utils;
using System.Diagnostics;
using System.Net.Mime;
using System.Net.Http.Headers;
using MySqlX.XDevAPI.Common;

namespace NativeSupportServiceApplication.Api

{
    public class ApiUtils
    {
       

       public static  ApiResponse<T> callApi<T,R>(String endPoint , HttpMethod httpMethod , R requestBody)
        {
            var TARGETURL = endPoint;

            HttpClientHandler handler = new HttpClientHandler()
            {
                Proxy = new WebProxy("http://127.0.0.1:8888"),
                UseProxy = false,
            };

            Debug.WriteLine("GET: + " + TARGETURL);

            // ... Use HttpClient.            
            HttpClient client = new HttpClient(handler);


            Debug.WriteLine(GlobalConstants.getCurrentUser());
            var byteArray = Encoding.ASCII.GetBytes(GlobalConstants.getCurrentUser().UserName+ ":"+ GlobalConstants.getCurrentUser().PassWord);
            client.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue("Basic", Convert.ToBase64String(byteArray));


            

            var json = JsonConvert.SerializeObject(requestBody);

            var buffer = System.Text.Encoding.UTF8.GetBytes(json);
            var byteContent = new ByteArrayContent(buffer);
            byteContent.Headers.ContentType = new MediaTypeHeaderValue("application/json");

            Debug.WriteLine(byteContent);
            HttpResponseMessage response = null;
            try
            {
                if (httpMethod == HttpMethod.Post)
                {

                    var task = Task.Run(() => client.PostAsync(TARGETURL, byteContent));
                    task.Wait();
                    response = task.Result;
                }
                else
                {
                    var task = Task.Run(() => client.GetAsync(TARGETURL));
                    task.Wait();
                    response = task.Result;
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex);
            }

            ApiResponse<T> responseData = new ApiResponse<T>();

            if (response != null)
            {
                HttpContent content = response.Content;



                // ... Check Status Code                                
                Debug.WriteLine("Response StatusCode: " + (int)response.StatusCode);

                // ... Read the string.

                var task2 = Task.Run(() => content.ReadAsStringAsync());
                task2.Wait();
                string result = task2.Result;




                responseData.setResponse(result, (int)response.StatusCode);
            }
            else
            {
                responseData.RawResponse = "Server Unreachable";
                responseData.ResponseCode = 404;
            }


            return responseData;
        }
    }
}