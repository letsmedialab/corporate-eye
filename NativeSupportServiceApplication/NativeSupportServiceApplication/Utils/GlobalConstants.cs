using NativeSupportServiceApplication.Dto;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace NativeSupportServiceApplication.Utils
{
    internal class GlobalConstants
    {
        private   static readonly UserDto currentUser = new UserDto();

        internal static void setCurrentUser(UserDto newUser)
        {
            currentUser.UserName = newUser.UserName;
            currentUser.PasswordSec.Clear();
            foreach (char c in newUser.PassWord)
                currentUser.PasswordSec.AppendChar(c);

            currentUser.IsAuthenticated = newUser.IsAuthenticated;
            currentUser.Name = newUser.Name;
        }

        internal static UserDto getCurrentUser()
        {


           UserDto tempUser = new UserDto();
            tempUser.UserName = currentUser.UserName;
            tempUser.Name = currentUser.Name;
            tempUser.IsAuthenticated = currentUser.IsAuthenticated;
            tempUser.PassWord = new NetworkCredential("", currentUser.PasswordSec).Password;
            return tempUser;
        }
    }
}
