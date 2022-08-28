using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net.Http;
using System.Net;
using NativeSupportServiceApplication.Dto;
using Newtonsoft.Json;

namespace ApiUtils

{
    public class ApiTest
    {
       

       public static  ApiResponse<T> callApi<T>(String endPoint)
        {
            var TARGETURL = endPoint;

            HttpClientHandler handler = new HttpClientHandler()
            {
                Proxy = new WebProxy("http://127.0.0.1:8888"),
                UseProxy = false,
            };

            Console.WriteLine("GET: + " + TARGETURL);

            // ... Use HttpClient.            
            HttpClient client = new HttpClient(handler);

            var byteArray = Encoding.ASCII.GetBytes("admin:0spyn@123");
            client.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue("Basic", Convert.ToBase64String(byteArray));


            var task = Task.Run(() => client.GetAsync(TARGETURL));
            task.Wait();
            HttpResponseMessage response = task.Result;

           
            HttpContent content = response.Content;

            // ... Check Status Code                                
            Console.WriteLine("Response StatusCode: " + (int)response.StatusCode);

            // ... Read the string.

            var task2 = Task.Run(() => content.ReadAsStringAsync());
            task2.Wait();
            string result = task2.Result;

            ApiResponse<T> responseData = new ApiResponse<T>();
           

            responseData.setResponse(result, (int)response.StatusCode);

            
            

            return responseData;
        }
    }
}