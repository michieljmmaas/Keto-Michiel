package hello;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

	@RequestMapping("/gewicht")
	public String gewicht(Model model) {
		ArrayList<Weight> weightList = AWWeightRepository.findAll();
		model.addAttribute("weightList", weightList);
		return "gewicht";
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

	@RequestMapping("/rotation")
	public String rotation() throws IOException {

//		File file = ResourceUtils.getFile("weight.json");
//
//		// Read File Content
//		String content = new String(Files.readAllBytes(file.toPath()));
//
//		ObjectMapper objectMapper = new ObjectMapper();
//		List<Weight> listWeight = objectMapper.readValue(content, new TypeReference<List<Weight>>() {
//		});
//
//		int i = 1;
//
//		for (Weight w : listWeight) {
//			w.setId(i);
//			AWWeightRepository.save(w);
//			System.out.println("Saved: " + w.getDatum());
//			i++;
//		}

		return "rotation";
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