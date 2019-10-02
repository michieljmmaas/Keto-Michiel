package hello;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("unused")
@Controller
public class HelloController {

	@ModelAttribute("foodForm")
	public Eten createEten() {
		return new Eten();
	}

	@Autowired
	EtenRepository AWEtenRepository;

	@Autowired
	WeightRepository AWWeightRepository;

	@Autowired
	GerechtRepository AWGerechtRepository;

	@Autowired
	MealSetRepository AWMealSetRepository;

	@Autowired
	ChartsService AWChartsService;

	@RequestMapping("/")
	public RedirectView redirect() {
		return new RedirectView("/1");
	}

	@RequestMapping("/{id}")
	public String index(Model model, @PathVariable int id) {
		List<Gerecht> gerechten = (List<Gerecht>) AWGerechtRepository.findAll(Sort.by(Direction.ASC, "datum"));
		MealSet mealset = AWMealSetRepository.findById(id);
		ArrayList<MealSet> allSets = AWMealSetRepository.findAll();
		Gerecht ontbijt = AWGerechtRepository.findById(mealset.getOntbijtID());
		Gerecht snack = AWGerechtRepository.findById(mealset.getSnackID());
		Gerecht dinner = AWGerechtRepository.findById(mealset.getDinnerID());
		ArrayList<Gerecht> set = new ArrayList<Gerecht>(Arrays.asList(ontbijt, snack, dinner));
		model.addAttribute("dishes", gerechten);
		float kcalSum = 0;
		float carbSum = 0;
		float protSum = 0;
		float fatSum = 0;
		float priceSum = 0;

		for (Gerecht g : set) {
			kcalSum += Float.valueOf(g.getTotalKcal());
			carbSum += Float.valueOf(g.getTotalCarb());
			protSum += Float.valueOf(g.getTotalProt());
			fatSum += Float.valueOf(g.getTotalFat());
			priceSum += Float.valueOf(g.getTotalPrice());

		}
		model.addAttribute("kcalSum", round(kcalSum, 2));
		model.addAttribute("carbSum", round(carbSum, 2));
		model.addAttribute("protSum", round(protSum, 2));
		model.addAttribute("fatSum", round(fatSum, 2));
		model.addAttribute("priceSum", priceSum);

		model.addAttribute("kcalDiff", round(1500 - kcalSum, 2));
		model.addAttribute("carbDiff", round(20 - carbSum, 2));
		model.addAttribute("protDiff", round(96 - protSum, 2));
		model.addAttribute("fatDiff", round(118 - fatSum, 2));
		model.addAttribute("priceDay", round(priceSum / 4, 2));
		model.addAttribute("MealSetId", id);

		for (int j = allSets.size() - 4; j > 0; j--) {
			allSets.remove(j);
		}

		model.addAttribute("allSets", allSets);

		model.addAttribute("set", set);
		model.addAttribute("mealSet", mealset);
		return "index";
	}

	@RequestMapping("export/{id}")
	public String export(@PathVariable int id, Model model) {
		MealSet set = AWMealSetRepository.findById(id);
		Gerecht ontbijt = AWGerechtRepository.findById(set.getOntbijtID());
		Set<Eten> ontbijtList = ontbijt.getIngredienten();
		Gerecht snack = AWGerechtRepository.findById(set.getSnackID());
		Set<Eten> snackList = snack.getIngredienten();
		Gerecht dinner = AWGerechtRepository.findById(set.getDinnerID());
		Set<Eten> dinnerList = dinner.getIngredienten();

		ArrayList<Eten> eten = new ArrayList<Eten>();

		for (Eten e : ontbijtList) {
			eten.add(e);
		}
		for (Eten e : snackList) {
			eten.add(e);
		}
		for (Eten e : dinnerList) {
			eten.add(e);
		}
		model.addAttribute("list", eten);

		return "export";
	}

	public static BigDecimal round(float d, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Float.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd;
	}

	public void setDelta(ArrayList<Weight> weightList) {
		for (int i = 1; i < weightList.size(); i++) {
			Weight w = weightList.get(i);
			float delta = weightList.get(i - 1).getWeight() - w.getWeight();

			float decimal = ChartsService.twoDecimail(Math.abs(delta), 1);

			w.setDelta(decimal);
		}
	}

	@RequestMapping("/gewicht")
	public String gewicht(Model model) {
		ArrayList<Weight> weightList = AWWeightRepository.findAll(Sort.by(Direction.ASC, "datum"));
		setDelta(weightList);
		model.addAttribute("weightList", weightList);
		float[] barArray = getWeights(weightList);
		String array = Arrays.toString(barArray);
		String adjustedString = array.substring(1, array.length() - 1);
		model.addAttribute("barArray", adjustedString);
		Weight startWeightItem = weightList.get(0);
		Weight lastWeightItem = weightList.get(weightList.size() - 1);

		float[] linear = AWChartsService.getLinearRegression(weightList);
		float linearDelta = linear[0];
		float perSet = linearDelta * 4;
		float perWeek = linearDelta * 7;
		float perMaand = linearDelta * 31;
		float linearStarting = linear[1];

		Date startingDate = startWeightItem.getDatum();
		float startGewicht = startWeightItem.getWeight();
		float currentGewicht = lastWeightItem.getWeight();
		Date currentDate = lastWeightItem.getDatum();
		float afgevallen = startGewicht - currentGewicht;
		float tegaan = startGewicht - 70 - afgevallen;
		float percentage = (afgevallen / (startGewicht - 70)) * 100;

		weightCalculations w80 = new weightCalculations(80, currentGewicht, linearDelta, currentDate, linearStarting,
				startingDate);
		weightCalculations w75 = new weightCalculations(75, currentGewicht, linearDelta, currentDate, linearStarting,
				startingDate);
		weightCalculations w70 = new weightCalculations(70, currentGewicht, linearDelta, currentDate, linearStarting,
				startingDate);

		ArrayList<weightCalculations> weightItems = new ArrayList<weightCalculations>(Arrays.asList(w80, w75, w70));

		model.addAttribute("weightItems", weightItems);
		model.addAttribute("delta1", ChartsService.twoDecimail(Math.abs(linearDelta), 3));
		model.addAttribute("delta2", 4 * ChartsService.twoDecimail(Math.abs(linearDelta), 3));
		model.addAttribute("delta3", 7 * ChartsService.twoDecimail(Math.abs(linearDelta), 3));
		model.addAttribute("delta4", 31 * ChartsService.twoDecimail(Math.abs(linearDelta), 3));
		model.addAttribute("afgevallen", ChartsService.twoDecimail(afgevallen, 1));
		model.addAttribute("tegaan", ChartsService.twoDecimail(tegaan, 1));
		model.addAttribute("percentage", ChartsService.twoDecimail(percentage, 2));

		return "gewicht";
	}

	public float[] getWeights(ArrayList<Weight> weightList) {
		float[] res = new float[weightList.size()];
		for (int i = 0; i < weightList.size(); i++) {
			res[i] = weightList.get(i).getWeight();
		}

		return res;
	}

	@RequestMapping("/eten")
	public String eten(Model model) {
		List<Eten> foods = AWEtenRepository.findAll(Sort.by(Direction.ASC, "name"));
		model.addAttribute("foods", foods);
		model.addAttribute("Eten", new Eten());
		return "eten";
	}

	@PostMapping("/gerechtPost")
	public RedirectView gerechtPost(@Valid @ModelAttribute("Gerecht") Gerecht gerecht, BindingResult bindResult,
			HttpServletRequest request, Model model) {
		if (bindResult.hasErrors()) {
			List<FieldError> s = bindResult.getFieldErrors();
			for (FieldError t : s) {
				System.out.println(t.getDefaultMessage());
			}

			return new RedirectView("/");
		}

		AWGerechtRepository.save(gerecht);
		return new RedirectView("/gerechten");
	}

	@RequestMapping("/gerechten")
	public String gerechten(Model model) {
		List<Gerecht> gerechten = (List<Gerecht>) AWGerechtRepository.findAll(Sort.by(Direction.ASC, "datum"));
		List<Eten> foods = AWEtenRepository.findAll(Sort.by(Direction.ASC, "name"));
		model.addAttribute("Gerecht", new Gerecht());
		model.addAttribute("dishes", gerechten);
		model.addAttribute("ingredienten", foods);
		ArrayList<String> types = new ArrayList<String>(Arrays.asList("Dinner", "Ontbijt", "Other", "Snack"));
		model.addAttribute("types", types);

		return "gerechten";
	}

	@PostMapping("/foodPost")
	public RedirectView foodPost(@Valid @ModelAttribute("Eten") Eten eten, BindingResult bindResult,
			HttpServletRequest request, Model model) {
		if (bindResult.hasErrors()) {
			System.out.println("---Binding has Error");
			return new RedirectView("/");

		}
		if (eten.getWeight() != 0) {
			eten.setKcal(PortionCalc(eten.getKcal(), eten.getWeight()));
			eten.setCarb(PortionCalc(eten.getCarb(), eten.getWeight()));
			eten.setProt(PortionCalc(eten.getProt(), eten.getWeight()));
			eten.setFat(PortionCalc(eten.getFat(), eten.getWeight()));
		}

		eten.setWeight(0);

		AWEtenRepository.save(eten);
		return new RedirectView("/eten");
	}

	public float PortionCalc(float value, int weight) {
		float current = ((value * (weight / 100)) / 4);
		return current;
	}

}