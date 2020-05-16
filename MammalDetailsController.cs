using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace WebAPI_Basic.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class MammalDetailsController : ControllerBase
    {
        public IEnumerable<string> Get()
        {
            return new string[] { " value1", "value2" };
        }

        public string Get(int id)
        {
            return "value";
        }

        public void Post(int id, [FromBody]string value)
        {
        }

        public void Put(int id, [FromBody]string value)
        {
        }

        public void Delete(int id)
        {
        }

    }
}