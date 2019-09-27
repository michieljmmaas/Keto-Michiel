package hello;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Eten")
public class EtenController {

	@Autowired
	EtenRepository AWEtenRepository;

	@Autowired
	GerechtRepository AWGerechtRepository;

	@DeleteMapping(value = "/{id}")
	public void deleteID(@PathVariable int id) {
		Eten toDelete = AWEtenRepository.findById(id);
		AWEtenRepository.delete(toDelete);
	}

	@DeleteMapping(value = "DeleteIngredient/{dishID}/{ingredientID}")
	public void deleteIngredient(@PathVariable int dishID, @PathVariable int ingredientID) {
		Eten toDelete = AWEtenRepository.findById(ingredientID);
		Gerecht gerrecht = AWGerechtRepository.findById(dishID);
		Set<Eten> ingredienten = gerrecht.getIngredienten();
		ingredienten.remove(toDelete);

		AWGerechtRepository.save(gerrecht);
	}

	@DeleteMapping(value = "DeleteDish/{dishID}")
	public void deleteDish(@PathVariable int dishID) {
		Gerecht gerrecht = AWGerechtRepository.findById(dishID);
		System.out.println("Deleting dish id= " + dishID);
		gerrecht.printInfo();
		for (Eten e : gerrecht.getIngredienten()) {
			e.printInfo();
		}
		
		AWGerechtRepository.delete(gerrecht);
	}

	@PostMapping(value = "AddIngredient/{dishID}/{ingredients}")
	public void addIngredient(@PathVariable int dishID, @PathVariable int[] ingredients) {
		Gerecht gerrecht = AWGerechtRepository.findById(dishID);
		Set<Eten> ingredienten = gerrecht.getIngredienten();
		for (int i : ingredients) {
			Eten e = AWEtenRepository.findById(i);
			ingredienten.add(e);
		}
		AWGerechtRepository.save(gerrecht);
	}

}
