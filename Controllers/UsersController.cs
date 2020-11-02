//using System;
//using System.Collections.Generic;
//using System.IO;
//using System.Linq;
//using System.Net.Mail;
//using System.Threading.Tasks;
//using Microsoft.AspNetCore.Mvc;
//using Microsoft.AspNetCore.Mvc.Rendering;
//using Microsoft.EntityFrameworkCore;
//using web_project.Models;

//namespace web_project.Controllers
//{
//    public class UsersController : Controller
//    {
//        private readonly web_projectContext _context;
//        public static int UserId = 0;

//        public UsersController(web_projectContext context)
//        {
//            _context = context;


//        }

//        public async Task<IActionResult> SessionCheck()
//        {
//            if (AppManage.LoggedInType <= 0)
//            {
//                return RedirectToAction("ViewLogIn", "Home");
//            }
//            else
//            {
//                return null;
//            }

//        }



//        public async Task<IActionResult> ForgotPassword([Bind("UserName,Password")] User user)
//        {
//            int a = AppManage.LoggedInType;
//            AppManage.ErrorMsg = String.Empty;
//            AppManage.ErrorMsg = "Please Check your email...";


//            int userTypeId = 0;
//            User LoggedUser = new User();
//            LoggedUser = _context.User.FirstOrDefault(x => x.UserName == user.UserName);

//            if (LoggedUser != null)
//            {
//                string path = "https://localhost:5001/Users/LogIn?UserName=" + LoggedUser.UserName + "&Password=" + LoggedUser.Password;
//            }
//            return RedirectToAction("ViewLogIn", "Home");


//        }

//        // GET: Users
//        public async Task<IActionResult> Index()
//        {
//            if (AppManage.LoggedInType <= 0)
//            {
//                return RedirectToAction("ViewLogIn", "Home");
//            }
//            else
//            {
//                return View(await _context.User.ToListAsync());
//            }

//        }

//        public async Task<IActionResult> Complain()
//        {
//            if (AppManage.LoggedInType <= 0)
//            {
//                return RedirectToAction("ViewLogIn", "Home");
//            }
//            else
//            {
//                return View();
//            }

//        }

//        public async Task<IActionResult> MainHome()
//        {
//            if (AppManage.LoggedInType > 0)
//            {
//                return View();
//            }
//            else
//            {
//                return RedirectToAction("ViewLogIn", "Home");

//            }
//        }

//        public async Task<IActionResult> LogIn([Bind("UserName,Password")] User user)
//        {
//            int a = AppManage.LoggedInType;
//            AppManage.ErrorMsg = String.Empty;


//            int userTypeId = 0;
//            User LoggedUser = new User();
//            LoggedUser = _context.User.FirstOrDefault(x => x.UserName == user.UserName && x.Password == user.Password);

//            if (LoggedUser != null)
//            {

//                AppManage.LoggedInType = LoggedUser.UserTypeId;
//                AppManage.LoggedInUserId = LoggedUser.Id;

//                UsersController.UserId = LoggedUser.Id;

//                return RedirectToAction("MainHome", "Users");

//            }
//            else
//            {
//                AppManage.ErrorMsg = "Invalod Log in !...";
//                return RedirectToAction("ViewLogIn", "Home");

//            }




//        }

//        // GET: Users/Details/5
//        public async Task<IActionResult> Details()
//        {
//            if (AppManage.LoggedInType > 0)
//            {
//                var user = await _context.User
//               .FirstOrDefaultAsync(m => m.Id == AppManage.LoggedInUserId);
//                if (user == null)
//                {
//                    return NotFound();
//                }
//                user.ImageLocation = "images/FarmerRequestsImages/" + user.ImageLocation;

//                return View(user);
//            }
//            else
//            {
//                return RedirectToAction("ViewLogIn", "Home");

//            }


//        }

//        public async Task<IActionResult> LogOut()
//        {
//            AppManage.LoggedInType = 0;
//            return RedirectToAction("Index", "Home");

//        }

//        public IActionResult CreateUser1()
//        {


//            return View("CreateUser");

//        }

//        // GET: Users/Create
//        public IActionResult CreateUser()
//        {

//            //if (AppManage.LoggedInType <= 0)
//            //{
//            //    return RedirectToAction("ViewLogIn", "Home");
//            //}
//            //else
//            //  {
//            return View();
//            //  }


//        }

//        // POST: Users/Create
//        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
//        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
//        [HttpPost]
//        [ValidateAntiForgeryToken]
//        public async Task<IActionResult> Create([Bind("Id,Image,UserName,Password,ConfirmPassword,FirstName,LastName,Email,Address,ContactNo,TelephoneNo,Gender")] User user)
//        {


//            if (ModelState.IsValid)
//            {
//                try
//                {
//                    User user1 = _context.User.Where(a => a.UserName == user.UserName).ToList().ElementAt(0);

//                    if (user1 != null)
//                    {
//                        ModelState.AddModelError(nameof(user.UserName), "This user name is already exists");

//                    }
//                }
//                catch (Exception ex)
//                {

//                }

//            }

//            user.UserTypeId = 2;
//            if (ModelState.IsValid)
//            {


//                _context.Add(user);
//                await _context.SaveChangesAsync();
//                return RedirectToAction("MainHome", "Users");

//            }

//            return View("CreateUser", user);
//        }

//        // GET: Users/Edit/5
//        public async Task<IActionResult> Edit()
//        {
//            if (UsersController.UserId == null)
//            {
//                return RedirectToAction("ViewLogIn", "Home");

//            }

//            var user = await _context.User.FindAsync(UsersController.UserId);
//            if (user == null)
//            {

//                return RedirectToAction("ViewLogIn", "Home");
//            }
//            return View(user);
//        }

//        // POST: Users/Edit/5
//        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
//        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
//        [HttpPost]
//        [ValidateAntiForgeryToken]
//        public async Task<IActionResult> Edit(int id, [Bind("Id,UserName,Password,ConfirmPassword,FirstName,LastName,Email,Address,ContactNo,TelephoneNo,Gender,UserTypeId")] User user)
//        {
//            if (id != user.Id)
//            {
//                return NotFound();
//            }
//            user.UserTypeId = AppManage.LoggedInType;
//            if (ModelState.IsValid)
//            {
//                try
//                {
//                    _context.Update(user);


//                    await _context.SaveChangesAsync();
//                }
//                catch (DbUpdateConcurrencyException)
//                {
//                    if (!UserExists(user.Id))
//                    {
//                        return NotFound();
//                    }
//                    else
//                    {
//                        throw;
//                    }
//                }


//            }
//            return RedirectToAction("Details", "Users");

//        }

//        // GET: Users/Delete/5
//        public async Task<IActionResult> Delete(int? id)
//        {
//            if (id == null)
//            {
//                return NotFound();
//            }

//            var user = await _context.User
//                .FirstOrDefaultAsync(m => m.Id == id);
//            if (user == null)
//            {
//                return NotFound();
//            }

//            if (AppManage.LoggedInType <= 0)
//            {
//                return RedirectToAction("ViewLogIn", "Home");
//            }
//            else
//            {
//                return View(user);


//            }
//        }



//        private bool UserExists(int id)
//        {
//            return _context.User.Any(e => e.Id == id);
//        }

//    }
//}
