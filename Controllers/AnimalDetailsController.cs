using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using web_project.Data;
using web_project.Models;

namespace web_project.Controllers
{
    public class AnimalDetailsController : Controller
    {
        private readonly web_projectContext _context;

        public AnimalDetailsController(web_projectContext context)
        {
            _context = context;
        }

        // GET: AnimalDetails
        public async Task<IActionResult> Index()
        {
            return View(await _context.AnimalDetails.ToListAsync());
        }

        // GET: AnimalDetails/Details/5
        public async Task<IActionResult> Details(string animalName, string percentage)
        {
            double highestPredictValue = 0.0;
            string name = "";
            int index = 0;
            List<string> names = new List<string>();
            List<string> percentages = new List<string>();

            names = animalName.Split(",").ToList();
            percentages = percentage.Split(",").ToList();


            for (int i = 0; i < percentages.Count(); i++)
            {
                if (percentages.ElementAt(i) != " ")
                {
                    var a = percentages.ElementAt(i);

                    try
                    {
                        if (Convert.ToDouble(percentages.ElementAt(i)) > highestPredictValue)
                        {
                            highestPredictValue = Convert.ToDouble(percentages.ElementAt(i));
                            index = i;
                        }
                    }
                    catch (Exception ex)
                    {

                    }

                }
            }

            string predictedAnimal = names.ElementAt(index);


            AnimalDetails animalDetails = new AnimalDetails();

            try
            {
                animalDetails = await _context.AnimalDetails.SingleOrDefaultAsync(m => m.Name == predictedAnimal.Trim());

            }
            catch (Exception ex)
            {

            }

            return Ok(animalDetails.Description);
        }



        public async Task<IActionResult> Create([Bind("Id,AnimalId,Name,Description")] AnimalDetails animalDetails)
        {
            if (ModelState.IsValid)
            {
                _context.Add(animalDetails);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(animalDetails);
        }

        // GET: AnimalDetails/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var animalDetails = await _context.AnimalDetails.FindAsync(id);
            if (animalDetails == null)
            {
                return NotFound();
            }
            return View(animalDetails);
        }

        // POST: AnimalDetails/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("Id,AnimalId,Name,Description")] AnimalDetails animalDetails)
        {
            if (id != animalDetails.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(animalDetails);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!AnimalDetailsExists(animalDetails.Id))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            return View(animalDetails);
        }

        // GET: AnimalDetails/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var animalDetails = await _context.AnimalDetails
                .FirstOrDefaultAsync(m => m.Id == id);
            if (animalDetails == null)
            {
                return NotFound();
            }

            return View(animalDetails);
        }

        // POST: AnimalDetails/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            var animalDetails = await _context.AnimalDetails.FindAsync(id);
            _context.AnimalDetails.Remove(animalDetails);
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool AnimalDetailsExists(int id)
        {
            return _context.AnimalDetails.Any(e => e.Id == id);
        }
    }
}
