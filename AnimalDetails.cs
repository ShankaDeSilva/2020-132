using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace web_project.Models
{
    public class AnimalDetails
    {
        public int Id { get; set; }
        public int AnimalId { get; set; }

        public string Name { get; set; }
        public string Description { get; set; }
    }
}
