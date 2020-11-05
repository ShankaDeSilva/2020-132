using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using DinkToPdf.Contracts;
using Microsoft.AspNetCore.Mvc;
using web_project.Models;

namespace web_project.Controllers
{
  
    public class HomeController : Controller
    {
       
        public HomeController(  )
        {

          

        }

       
        public IActionResult Index()
        {
            string a = AppManage.root + "/Analyze.html";


            var result = new PhysicalFileResult(a, "text/html");
            return result;
        }

        public IActionResult Privacy()
        {
            return View();
        }

        public async Task<IActionResult> ViewLogIn()
        {
            AppManage.LoggedInType = 0;
            return View();
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}
