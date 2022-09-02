
using NativeSupportServiceApplication.Api;
using NativeSupportServiceApplication.Dto;
using NativeSupportServiceApplication.Modules;
using NativeSupportServiceApplication.Utils;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Ubiety.Dns.Core;

namespace NativeSupportServiceApplication.Forms
{
    
    public partial class Login : Form
    {
        Boolean testing = true;
        Task processKiller;
        Boolean shouldKill = true;
        public Login()
        {
            InitializeComponent();
           hookID = SetHook(HookCallback);

          //  processKiller = Task.Run(() =>
            //{

            //    while (true)
            //    {
            //        try
            //        {
            //            Thread.Sleep(2000);
            //          //  GeneralUtil.terminateProcess("Taskmgr");
            //            GeneralUtil.terminateProcess("explorer");

            //        }
            //        catch (Exception e)
            //        { 
            //        }

            //    }

            //});


        }

        private void Login_Load(object sender, EventArgs e)
        {
            FormBorderStyle = FormBorderStyle.None;
            WindowState = FormWindowState.Maximized;
          
            if (testing == true)
            {
                usernameBox.Text = "admin";
                passwordBox.Text = "0spyn@123";
                loginBtn.PerformClick();
            }
          
        }

        private void Form1_SizeChanged(object sender, EventArgs e)
        {
            CenterControlInParent(panel1);
        }
        private void CenterControlInParent(Control ctrlToCenter)
        {
            ctrlToCenter.Left = (ctrlToCenter.Parent.Width - ctrlToCenter.Width) / 2;
            ctrlToCenter.Top = (ctrlToCenter.Parent.Height - ctrlToCenter.Height) / 2;
        }
        Form currentForm ;
        private void button1_Click(object sender, EventArgs e)
        {
            status.Text = "";
            currentForm = this;
            //processKiller.Dispose();
            progressBar1.Visible = true;
            loginBtn.Enabled = false;
            backgroundWorker1.RunWorkerAsync();



        }

        private void nextForm()
        {
            UnhookWindowsHookEx(hookID);
            currentForm.Hide();
            var main = new LoadingSplash();
            main.Show();
            //main.Closed += (s, args) => currentForm.Close();



         
        }

        public delegate IntPtr LowLevelKeyboardProc(int nCode, IntPtr wParam, IntPtr lParam);




        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern IntPtr SetWindowsHookEx(
            int idHook, LowLevelKeyboardProc lpfn, IntPtr hMod, uint dwThreadId);

        private const int WH_KEYBOARD_LL = 13;
        private static IntPtr SetHook(LowLevelKeyboardProc proc)
        {
            return SetWindowsHookEx(
                WH_KEYBOARD_LL,
                proc,
                (IntPtr)0, 
                0); // for all threads
        }


        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern IntPtr CallNextHookEx(IntPtr hhk, int nCode,
    IntPtr wParam, IntPtr lParam);
       

        private const int WM_KEYDOWN = 0x0100;

        private static IntPtr HookCallback(
            int nCode, IntPtr wParam, IntPtr lParam)
        {
            // check if it is a key down message
            if (nCode >= 0 && wParam == (IntPtr)WM_KEYDOWN)
            {
                int vkCode = Marshal.ReadInt32(lParam);

                if ((Keys)vkCode == Keys.F7)
                {
                    System.Environment.Exit(0);
                }

                Debug.WriteLine((Keys)vkCode);
                // check if the pressed key is the `Alt` key
                if (!GeneralUtil.isOnlyWords((Keys)vkCode) && (Keys)vkCode != Keys.Back
                    && (Keys)vkCode != Keys.Tab && (Keys)vkCode != Keys.ShiftKey 
                    && (Keys)vkCode != Keys.Shift && (Keys)vkCode != Keys.RShiftKey 
                    && (Keys)vkCode != Keys.Oemtilde && (Keys)vkCode != Keys.LShiftKey
                    && (Keys)vkCode != Keys.CapsLock)
                {
                    // return 1 for handled if its the alt key
                    return (IntPtr)1;
                }
            }

            // let the message bubble if its not a keydown message or it wasn't the alt key which was pressed               
            return CallNextHookEx(hookID, nCode, wParam, lParam);
        }
        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        [return: MarshalAs(UnmanagedType.Bool)]
        private static extern bool UnhookWindowsHookEx(IntPtr hhk);
        // with this line you can set the `HookCallback` into the message loop
        static IntPtr hookID ;

       

        private void Login_FormClosing(object sender, FormClosingEventArgs e)
        {
            

           // UnhookWindowsHookEx(hookID);

            e.Cancel = true;
        }

        private void backgroundWorker1_DoWork(object sender, DoWorkEventArgs e)
        {

            
            UserDto userDto = new UserDto();
            userDto.PassWord = passwordBox.Text;
            userDto.UserName = usernameBox.Text;

            GlobalConstants.setCurrentUser(userDto);


            userDto.UserName = usernameBox.Text;



            ApiResponse<UserDto> response = ApiUtils.callApi<UserDto, UserDto>(ApiEndPoints.USER_AUTH, HttpMethod.Post, userDto);
            e.Result = response;
            if (response.ResponseCode == 200)
            {
                if (response.ResponseObject.IsAuthenticated)
                {
                    GlobalConstants.setCurrentUser(response.ResponseObject);
                    
                }
            }


            
            
        }

        private void backgroundWorker1_RunWorkerCompleted(object sender, RunWorkerCompletedEventArgs e)
        {
            progressBar1.Visible = false;
            ApiResponse<UserDto> result = (ApiResponse<UserDto>)e.Result;

            if (result.ResponseCode == 401)
            {
                status.Text = "Invalid Credentials!";
            }
            else if (result.ResponseCode == 200)
            {
                if (GlobalConstants.getCurrentUser().IsAuthenticated)
                    nextForm();

            }
            else
            {
                status.Text = "Error:"+ result.ResponseCode;

                if (result.RawResponse != null)
                    status.Text += result.RawResponse;
            }
           

            loginBtn.Enabled = true ;
        }

        private void passwordBox_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                loginBtn.PerformClick();
            }
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }
    }
}
