using System.Diagnostics;

namespace NativeSupportServiceApplication.Utils
{
    internal static class AlertHandlerHelpers
    {
        public static List<NotificationDto> waitingNotifications = new List<NotificationDto>();

        internal static void flushExpiredNotifications(REventType r_PROCESS, List<String> currentList)
        {
            try
            {
                List<NotificationDto> expiredNotifications = new List<NotificationDto>();

                lock (AlertHandlerHelpers.waitingNotifications)
                    AlertHandlerHelpers.waitingNotifications.ForEach(e => {
                        if (!currentList.Contains(e.Pname) && e.Type.Equals(r_PROCESS))
                        {
                            expiredNotifications.Add(e);
                        }
                    });


                expiredNotifications.ForEach(e => { AlertHandlerHelpers.waitingNotifications.Remove(e); Debug.WriteLine("Cleanup Removing id (" + e.Identifier + ")"); });
            }
            catch
            {

            }
        }
    }
}