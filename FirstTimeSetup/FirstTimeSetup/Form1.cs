using Microsoft.Win32;

namespace FirstTimeSetup
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            try
            {
                RegistryKey key = Registry.CurrentUser.OpenSubKey(@"SOFTWARE\CorporateEye");

                textBox1.Text = key.GetValue("hostname").ToString();
                textBox2.Text = key.GetValue("port").ToString();

                key.Close(); 

            }
            catch (Exception ex)
            {
                RegistryKey key = Registry.CurrentUser.CreateSubKey(@"SOFTWARE\CorporateEye");
                key.SetValue("hostname" , "localhost");
                key.SetValue("port" , "8100");

                textBox1.Text = key.GetValue("hostname").ToString();
                textBox2.Text = key.GetValue("port").ToString();

                key.Close();

            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            RegistryKey key = Registry.CurrentUser.OpenSubKey(@"SOFTWARE\CorporateEye",true);

            key.SetValue("hostname",textBox1.Text);
            key.SetValue("port", textBox2.Text);

            key.Close();

            MessageBox.Show("Successfully Updated Host Configuration. Click ok to close");

            Application.Exit();
        }
    }
}