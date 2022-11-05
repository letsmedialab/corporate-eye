


using IPC.NamedPipe;
using NativeSupportServiceApplication.Models;
using NativeSupportServiceApplication.Utils;
using System.Diagnostics;

namespace NativeSupportServiceApplication.Threads
{
    [Serializable]  // mandatory
    class Message
    {
        public string title;
        public string content;
    }
    public class Icom
    {
        private static void OnReceived(PipeMessage message)
        {
            if (message.GetPayloadType() == PipeMessageType.PMTString)
            {
                String msg = (string)message.GetPayload();

                Debug.WriteLine("Payload" + msg);

                var keyWordResult = GeneralUtil.checkKeywordMatch(msg);
                var emailResult = GeneralUtil.checkEmailMatch(msg);
                var urlResult = GeneralUtil.checkUrlMatch(msg);

                if (keyWordResult != null)
                {
                    AlertHandler.handle(keyWordResult, EventSource.BROWSER_CONTENT);
                }
                if (urlResult != null)
                {
                    AlertHandler.handle(urlResult, EventSource.BROWSER_CONTENT);
                }

                if (emailResult != null)
                {
                    AlertHandler.handle(emailResult, EventSource.BROWSER_CONTENT);
                }
            }
            if (message.GetPayloadType() == PipeMessageType.PMTByte)
            {
                var data = (byte[])message.GetPayload();
                Debug.WriteLine("[R]{B}-" + BitConverter.ToString((byte[])data));
            }
        }

        public static void startListner()
        {

            Node node = new Node("server", "client", ".", OnReceived);
            if (node.Start() == false)
                throw new Exception("Error: Could not create the named pipe!");
            //while (true)
            //{
            //    string r = Console.ReadLine();
            //    if (!string.IsNullOrEmpty(r))
            //        node.Send(r);
            //}
        }
    }
}
