


using IPC.NamedPipe;
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
                Debug.WriteLine( (string)message.GetPayload());
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
