package hello;

import java.text.ParseException;
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

	@Autowired
	MealSetRepository AWMealSetRepository;

	@Autowired
	WeightRepository AWWeightRepository;

	@DeleteMapping(value = "/{id}")
	public void deleteID(@PathVariable int id) {
		Eten toDelete = AWEtenRepository.findById(id);
		AWEtenRepository.delete(toDelete);
	}

	@DeleteMapping(value = "weight/{id}")
	public void deleteWeight(@PathVariable int id) {
		Weight toDelete = AWWeightRepository.findById(id);
		AWWeightRepository.delete(toDelete);
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
		AWGerechtRepository.delete(gerrecht);
	}

	@PostMapping(value = "NewMealSet/{datum}/{ontbijtDish}/{snackDish}/{dinnerDish}")
	public void newMealSet(@PathVariable String datum, @PathVariable int ontbijtDish, @PathVariable int snackDish,
			@PathVariable int dinnerDish) throws ParseException {
		System.out.println(datum);
		Gerecht ontbijt = AWGerechtRepository.findById(ontbijtDish);
		ontbijt.setDashDatum(datum);
		Gerecht snack = AWGerechtRepository.findById(snackDish);
		snack.setDashDatum(datum);
		Gerecht dinner = AWGerechtRepository.findById(dinnerDish);
		dinner.setDashDatum(datum);
		MealSet meal = new MealSet();
		meal.setOntbijtID(ontbijtDish);
		meal.setSnackID(snackDish);
		meal.setDinnerID(dinnerDish);
		meal.setDate(datum);

		AWMealSetRepository.save(meal);
	}

	@PostMapping(value = "UpdateSet/{set}/{meal}/{gerecht}")
	public void updateSet(@PathVariable int set, @PathVariable int meal, @PathVariable int gerecht) {
		MealSet mealSet = AWMealSetRepository.findById(set);
		switch (meal) {
		case 0:
			mealSet.setOntbijtID(gerecht);
			break;
		case 1:
			mealSet.setSnackID(gerecht);
			break;
		case 2:
			mealSet.setDinnerID(gerecht);
			break;
		default:
			break;
		}
		AWMealSetRepository.save(mealSet);

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
