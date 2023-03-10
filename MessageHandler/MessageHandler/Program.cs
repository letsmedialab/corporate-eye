

using IPC.NamedPipe;
using System.Diagnostics;
using System.IO.MemoryMappedFiles;
using System.Linq.Expressions;
using System.Runtime.Serialization.Formatters.Binary;

[Serializable]  // mandatory
class Message
{
    public string title;
    public string content;
}
public class MessageHandler
{
    private static void OnReceived(PipeMessage message)
    {
        if (message.GetPayloadType() == PipeMessageType.PMTString)
        {
            Console.WriteLine("[R]{S}-" + (string)message.GetPayload());
        }
        if (message.GetPayloadType() == PipeMessageType.PMTByte)
        {
            var data = (byte[])message.GetPayload();
            Console.WriteLine("[R]{B}-" + BitConverter.ToString((byte[])data));
        }
    }

    static void Main(string[] args)
    {
        try
        {

            IPC.NamedPipe.Node node = new IPC.NamedPipe.Node("client", "server", ".", OnReceived);
            if (node.Start() == false)
            {
                File.WriteAllLinesAsync("WriteLines.txt", new String[] { "Error: Could not create the named pipe!" });

                throw new Exception("Error: Could not create the named pipe!");

            }
            //while (true)
            //{
            //    string r = Console.ReadLine();
            //    if (!string.IsNullOrEmpty(r))

            //}



            try
            {
                KillExistingProcesses();
            }
            catch (Exception e)
            {
                File.WriteAllLinesAsync("WriteLines.txt", new String[] { e.ToString() });
            }

            var x = OpenStandardStreamIn();

            while (x != null || x != "")
            {

                string path = "MyTest.txt";
                String content = OpenStandardStreamIn();
                node.Send(content);

                if (!String.IsNullOrEmpty(content))
                {
                    if (!File.Exists(path))
                    {
                        // Create a file to write to.
                        using (StreamWriter sw = File.CreateText(path))
                        {
                            sw.WriteLine(content);
                            sw.Flush();
                            sw.Close();

                        }
                    }

                    // This text is always added, making the file longer over time
                    // if it is not deleted.
                    using (StreamWriter sw = File.AppendText(path))
                    {
                        sw.WriteLine(content);
                        sw.Flush();
                        sw.Close();
                    }
                }
                x = OpenStandardStreamIn();
                //Console.WriteLine(OpenStandardStreamIn());
            }
        }

        catch(Exception ea)
                {
            File.WriteAllLinesAsync("WriteLines.txt", new String[] { ea.ToString() });
        }

    }

    private static string OpenStandardStreamIn()
    {
        //Read first four bytes for length information
        System.IO.Stream stdin = Console.OpenStandardInput();
        int length = 0;
        byte[] bytes = new byte[4];
        stdin.Read(bytes, 0, 4);
        length = System.BitConverter.ToInt32(bytes, 0);

        string input = "";
        for (int i = 0; i < length; i++)
            input += (char)stdin.ReadByte();




        return input;
    }
    private static void KillExistingProcesses()
    {
        Process myProc = Process.GetCurrentProcess();
        ;
        Process[] p = Process.GetProcessesByName(Process.GetCurrentProcess().ProcessName);
        foreach (var pro in p)
        {
            if (pro.Id != myProc.Id)
            {
                pro.Kill();
            }
        }
    }
}

//using System;
//using System.IO;
//using Newtonsoft.Json;
//using Newtonsoft.Json.Linq;

//namespace NativeMessagingHost
//{
//    class Program
//    {
//        public static void Main(string[] args)
//        {
//            JObject data;
//            while ((data = Read()) != null)
//            {
//                var processed = ProcessMessage(data);
//                Write(processed);
//                if (processed == "exit")
//                {
//                    return;
//                }
//            }
//        }

//        public static string ProcessMessage(JObject data)
//        {
//            var message = data["text"].Value<string>();
//            switch (message)
//            {
//                case "test":
//                    return "testing!";
//                case "exit":
//                    return "exit";
//                default:
//                    return "echo: " + message;
//            }
//        }

//        public static JObject Read()
//        {
//            var stdin = Console.OpenStandardInput();
//            var length = 0;

//            var lengthBytes = new byte[4];
//            stdin.Read(lengthBytes, 0, 4);
//            length = BitConverter.ToInt32(lengthBytes, 0);

//            var buffer = new char[length];
//            using (var reader = new StreamReader(stdin))
//            {
//                while (reader.Peek() >= 0)
//                {
//                    reader.Read(buffer, 0, buffer.Length);
//                }
//            }

//            return (JObject)JsonConvert.DeserializeObject<JObject>(new string(buffer));
//        }

//        public static void Write(JToken data)
//        {
//            var json = new JObject();
//            json["data"] = data;

//            var bytes = System.Text.Encoding.UTF8.GetBytes(json.ToString(Formatting.None));

//            var stdout = Console.OpenStandardOutput();
//            stdout.WriteByte((byte)((bytes.Length >> 0) & 0xFF));
//            stdout.WriteByte((byte)((bytes.Length >> 8) & 0xFF));
//            stdout.WriteByte((byte)((bytes.Length >> 16) & 0xFF));
//            stdout.WriteByte((byte)((bytes.Length >> 24) & 0xFF));
//            stdout.Write(bytes, 0, bytes.Length);
//            stdout.Flush();
//        }
//    }
//}

//using NativeMessaging;
//using Newtonsoft.Json.Linq;
//using System.Reflection;

//namespace MessageHandler
//{
//    public class MyHost : Host
//    {
//        private const bool SendConfirmationReceipt = true;

//        public override string Hostname
//        {
//            get { return "com.coporateeye.handler"; }
//        }

//        public MyHost() : base(SendConfirmationReceipt)
//        {

//        }

//        protected override void ProcessReceivedMessage(JObject data)
//        {
//            SendMessage(data);
//        }
//    }
//    class Program
//    {
//        static public string AssemblyLoadDirectory
//        {
//            get
//            {
//                string codeBase = Assembly.GetEntryAssembly().CodeBase;
//                UriBuilder uri = new UriBuilder(codeBase);
//                string path = Uri.UnescapeDataString(uri.Path);
//                return Path.GetDirectoryName(path);
//            }
//        }

//        static public string AssemblyExecuteablePath
//        {
//            get
//            {
//                string codeBase = Assembly.GetEntryAssembly().CodeBase;
//                UriBuilder uri = new UriBuilder(codeBase);
//                return Uri.UnescapeDataString(uri.Path);
//            }
//        }

//        static Host Host;

//        static string[] AllowedOrigins = new string[] { "chrome-extension://kfmnkfepmagcipakhpjmdoacmhbalnkk/" };
//        static string Description = "Description Goes Here";

//        static void Main(string[] args)
//        {
//            Host = new MyHost();
//            Host.SupportedBrowsers.Add(ChromiumBrowser.GoogleChrome);
//            Host.SupportedBrowsers.Add(ChromiumBrowser.MicrosoftEdge);

//            if (args.Contains("--register"))
//            {
//                Host.GenerateManifest(Description, AllowedOrigins);
//                Host.Register();
//            }
//            else if (args.Contains("--unregister"))
//            {
//                Host.Unregister();
//            }
//            else
//            {
//                Host.Listen();
//            }
//        }
//    }
//}