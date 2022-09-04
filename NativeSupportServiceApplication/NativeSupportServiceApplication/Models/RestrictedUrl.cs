﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NativeSupportServiceApplication.Models
{
    public class RestrictedUrl
    {
        private int id;
        private List<String> url;
        private String policyName;

        public int Id { get => id; set => id = value; }

        public string PolicyName { get => policyName; set => policyName = value; }
        public List<string> Url { get => url; set => url = value; }
    }
}
