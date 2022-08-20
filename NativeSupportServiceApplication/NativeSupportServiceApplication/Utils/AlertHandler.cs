using Microsoft.Toolkit.Uwp.Notifications;
using NativeSupportServiceApplication.Models;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.ApplicationModel.Activation;

namespace NativeSupportServiceApplication.Utils
{


    public enum REventType
    {
        NONE, R_PROCESS, R_FILE, R_KEYWORD
    }
    enum ActionType
    {
        EXECUTE, SNOOZE
    }


    class NotificationDto
    {

        private REventType type;
        private String identifier;
        private Boolean snooze = false;
        private DateTime lastShown;
        private String pname;

        public string Identifier { get => identifier; set => identifier = value; }
        public bool Snooze { get => snooze; set => snooze = value; }
        public DateTime LastShown { get => lastShown; set => lastShown = value; }
        public REventType Type { get => type; set => type = value; }
        public string Pname { get => pname; set => pname = value; }

    }
    public class AlertHandler
    {
        public static string EVENT_TYPE = "eventType";
        public static string ID = "identifier";
        public static string VIOLATION_MSG = "violationMessage";
        public static string ACTION = "action";

        public static string PRE_FILE = "PRE_FILE_";
        public static string PRE_PROCESS = "PRE_PROCESS_";
        public static string PRE_KEYWORD = "PRE_KEYWORD_";

        public static double NOTIFICATION_DELAY = 30;

        static AlertHandler()
        {

            // IToastButton button;
            ToastNotificationManagerCompat.OnActivated += toastArgs =>
            {
                // Obtain the arguments from the notification
                ToastArguments args = ToastArguments.Parse(toastArgs.Argument);

                REventType actionType;

                bool isAction = args.TryGetValue(EVENT_TYPE, out actionType);

                if (isAction)
                {
                    switch (actionType)
                    {
                        case REventType.R_PROCESS:
                            String processName;
                            String id;
                            ActionType action;
                            args.TryGetValue(ID, out id);
                            args.TryGetValue(VIOLATION_MSG, out processName);
                            args.TryGetValue(ACTION, out action);

                            if (action.Equals(ActionType.EXECUTE))
                            {
                                deleteProcess(processName);
                            }
                            else
                            {
                                snoozeNotification(id, actionType);
                            }
                            break;

                        case REventType.R_FILE:
                            string fileName;
                            args.TryGetValue(ID, out id);
                            args.TryGetValue(ACTION, out action);
                            args.TryGetValue(VIOLATION_MSG, out fileName);

                            if (action.Equals(ActionType.EXECUTE))
                            {
                                deleteFile(fileName);
                            }
                            else
                            {
                                snoozeNotification(id, actionType);
                            }
                            break;

                       
                    }


                }

            };
        }



       
       
        private static void notify(string identifier, REventType rEvent, string policyName, String id)
        {
            if (newNotifierReadyToShow(id, rEvent))
            {
                new Task(() =>
                {
                    String heading = "";
                    String message = "";
                    ToastContentBuilder toastContentBuilder = new ToastContentBuilder();
                    switch (rEvent)
                    {
                        case REventType.R_PROCESS:
                            heading = "Restricted Process Detected";
                            message = "Running process " + identifier + " violates company policy " + policyName;

                            ToastArguments args = new ToastArguments();

                            args.Add(EVENT_TYPE, rEvent);
                            args.Add(ID, id);
                            args.Add(VIOLATION_MSG, identifier);
                            toastContentBuilder.AddButton("Terminate Process", ToastActivationType.Background, args.Add(ACTION, ActionType.EXECUTE).ToString());
                            args.Remove(ACTION);
                            toastContentBuilder.AddButton("Snooze Notification", ToastActivationType.Background, args.Add(ACTION, ActionType.SNOOZE).ToString());
                            break;

                        case REventType.R_FILE:
                            heading = "Restricted File Detected";
                            message = "Possession of file " + identifier + " violates company policy " + policyName;

                            args = new ToastArguments();

                            args.Add(EVENT_TYPE, rEvent);
                            args.Add(ID, id);
                            args.Add(VIOLATION_MSG, identifier);

                            toastContentBuilder.AddButton("Delete File", ToastActivationType.Background, args.Add(ACTION, ActionType.EXECUTE).ToString());
                            args.Remove(ACTION);
                            toastContentBuilder.AddButton("Snooze Notification", ToastActivationType.Background, args.Add(ACTION, ActionType.SNOOZE).ToString());
                            break;

                        case REventType.R_KEYWORD:
                            heading = "Restricted message/keyword Detected";
                            message = "The text you entered contains " + identifier + ", violates company policy " + policyName;

                            args = new ToastArguments();

                          

                            args.Add(EVENT_TYPE, rEvent);
                            args.Add(ID, id);
                            args.Add(VIOLATION_MSG, id);

                            
                            //toastContentBuilder.AddButton("Snooze Notification", ToastActivationType.Background, args.Add(ACTION, ActionType.SNOOZE).ToString());
                            break;
                    }

                    notifierListRefresh(id, rEvent,identifier );



                    toastContentBuilder
                    .AddText(heading)
                    .AddText(message)
                    .Show();

                    //waitingProcessDialogs.Remove("rprocess_" + restrictedProcess.ProcessName);
                }).Start();
            }
        }



        private static void notifierListRefresh(string idName, REventType r_PROCESS, String pname)
        {
            List<NotificationDto> notifications = AlertHandlerHelpers.waitingNotifications.FindAll(e => e.Identifier.Equals(idName) && e.Type.Equals(r_PROCESS)).ToList();
            if (notifications.Count > 0)
            {
                notifications.ForEach(e => e.LastShown = DateTime.Now);
            }
            else
            {
                NotificationDto notification = new NotificationDto();
                notification.Identifier = idName;
                notification.LastShown = DateTime.Now;
                notification.Type = r_PROCESS;
                notification.Pname = pname;
                lock (AlertHandlerHelpers.waitingNotifications)
                    AlertHandlerHelpers.waitingNotifications.Add(notification);

            }
        }
        private static void removeNotification(string idName, REventType r_PROCESS)
        {
            lock (AlertHandlerHelpers.waitingNotifications)
                AlertHandlerHelpers.waitingNotifications.RemoveAll(e => e.Identifier.Equals(idName) && e.Type.Equals(r_PROCESS));
        }
        private static void snoozeNotification(string idName, REventType r_PROCESS)
        {
            lock (AlertHandlerHelpers.waitingNotifications)
                AlertHandlerHelpers.waitingNotifications.FindAll(e => e.Identifier.Equals(idName) && e.Type.Equals(r_PROCESS)).ForEach(e => e.Snooze = true);
            Debug.WriteLine(AlertHandlerHelpers.waitingNotifications);
        }

        private static bool newNotifierReadyToShow(String restrictedProcess, REventType r_PROCESS)
        {


            List<NotificationDto> cache = AlertHandlerHelpers.waitingNotifications.FindAll(e => e.Identifier.Equals(restrictedProcess) && e.Type.Equals(r_PROCESS)).ToList();

            if (cache.Count == 0)
            {
                return true;
            }
            else
            {

                if (r_PROCESS.Equals(REventType.R_KEYWORD))
                {
                    Debug.WriteLine(cache[0].Identifier + " " + cache[0].Type + " " + (cache[0].LastShown.AddSeconds(NOTIFICATION_DELAY) < DateTime.Now) + " " + cache[0].Snooze);
                    return (cache[0].Snooze == false && cache[0].LastShown.AddSeconds(NOTIFICATION_DELAY/3) < DateTime.Now);
                }
                else
                {
                    Debug.WriteLine(cache[0].Identifier + " " + cache[0].Type + " " + (cache[0].LastShown.AddSeconds(NOTIFICATION_DELAY) < DateTime.Now) + " " + cache[0].Snooze);
                    return (cache[0].Snooze == false && cache[0].LastShown.AddSeconds(NOTIFICATION_DELAY) < DateTime.Now);
                }
            }

        }
        private static void deleteFile(string fileName)
        {
            try
            {
                File.Delete(fileName);
            }
            catch
            {
            }
        }
        private static void deleteProcess(String processName)
        {
            try
            {
                Process[] workers = Process.GetProcessesByName(processName);
                foreach (Process worker in workers)
                {
                    worker.Kill();
                    worker.WaitForExit();
                    worker.Dispose();
                }
            }
            catch (Exception e)
            {
                Debug.Write(e);
            }
        }

        public static void handle(RestrictedFile restrictedFile)
        {


            Debug.WriteLine("notifying for " + restrictedFile.FileName);

            notify(restrictedFile.FileName, REventType.R_FILE, restrictedFile.PolicyName,PRE_FILE + Convert.ToString(restrictedFile.Id));


        }
        public static void handle(RestrictedKeyword restrictedKeyword)
        {
            Debug.WriteLine("notifying for " + restrictedKeyword.ViolatedMsg);

            notify(restrictedKeyword.ViolatedMsg, REventType.R_KEYWORD, restrictedKeyword.PolicyName, PRE_KEYWORD + Convert.ToString(restrictedKeyword.Id));
        }
        public static void handle(RestrictedProcess restrictedProcess)
        {


            Debug.WriteLine("notifying for " + restrictedProcess.ProcessName);

            notify(restrictedProcess.ProcessName, REventType.R_PROCESS, restrictedProcess.PolicyName, PRE_PROCESS + Convert.ToString(restrictedProcess.Id));


        }
    }
}
