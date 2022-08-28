using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NativeSupportServiceApplication.Dto
{
    public  class ApiResponse<T>
    {
        private T responseObject;
        private String rawResponse;
        private int responseCode;

        public T ResponseObject { get => responseObject; }
        public string RawResponse { get => rawResponse; set => rawResponse = value; }
        public int ResponseCode { get => responseCode; set => responseCode = value; }

        public void setResponse(String response , int responseCode)
        {
            try
            {
                responseObject = JsonConvert.DeserializeObject<T>(response);
            }
            catch (Exception ex) { 
                
            }
            rawResponse = response;
            this.responseCode = responseCode;

        }
    }
}
