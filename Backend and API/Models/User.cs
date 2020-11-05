using Microsoft.AspNetCore.Http;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace web_project.Models
{
    public class User
    {
        public int Id { get; set; }

        [Required]
        [DisplayName("User Name")]
       
        public string UserName { get; set; }
        [Required]
        [StringLength(15, ErrorMessage = "{0} length must be between {2} and {1}.", MinimumLength = 8)]
        [DataType(DataType.Password)]
        public string Password { get; set; }

        
        [DataType(DataType.Password)]
        [Compare("Password")]
        [NotMapped]
        public string ConfirmPassword { get; set; }


        
        [DisplayName("Contact No")]
        [StringLength(10, ErrorMessage = "Invalid Mobile No.", MinimumLength = 10)]
        public string ContactNo { get; set; }

        [DisplayName("Telephone No")]
     
        [StringLength(10, ErrorMessage = "Invalid Mobile No.", MinimumLength = 10)]
        public string TelephoneNo { get; set; }

        [Required]
        [DisplayName("First Name")]
        public string FirstName { get; set; }
        [Required]
        [DisplayName("Last Name")]
        public string LastName { get; set; }

        [Required]
        public int UserTypeId { get; set; }

        [EmailAddress]
        public string Email { get; set; }

        [Required]
        public string Address { get; set; }

        [Required]
        public string Gender { get; set; }

        [NotMapped]
        public IFormFile Image { get; set; }

        public string ImageLocation { get; set; }



    }
}
