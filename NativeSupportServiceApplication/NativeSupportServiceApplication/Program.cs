using NativeSupportServiceApplication.Modules;

namespace NativeSupportServiceApplication
{
    internal static class Program
    {
        /// <summary>
        ///  The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Console.WriteLine("sfas");
            // To customize application configuration such as set high DPI settdfgfdgings or default font,
            // see https://aka.ms/applicationconfiguration.
            ApplicationConfiguration.Initialize();
            
            
            Application.Run(new LoadingSplash());
          
            //Console.WriteLine("sfas");
           
        }
    }
}