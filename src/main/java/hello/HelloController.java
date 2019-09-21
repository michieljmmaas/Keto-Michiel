package hello;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	GerechtRepository AWGerechtRepository;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/gewicht")
	public String gewicht() {
		return "gewicht";
	}

	@RequestMapping("/eten")
	public String eten(Model model) {
		List<Eten> foods = AWEtenRepository.findAll(Sort.by(Direction.ASC, "name"));
		model.addAttribute("foods", foods);
		model.addAttribute("Eten", new Eten());
		return "eten";
	}

	@RequestMapping("/gerechten")
	public String gerechten() {
		List<Gerecht> gerechten = (List<Gerecht>) AWGerechtRepository.findAll();
		for (Gerecht g : gerechten) {
			g.printInfo();
		}

		return "gerechten";
	}

	@RequestMapping("/rotation")
	public String rotation() throws IOException {

//		File file = ResourceUtils.getFile("jsonKeto.json");
//
//		// Read File Content
//		String content = new String(Files.readAllBytes(file.toPath()));
//
//		ObjectMapper objectMapper = new ObjectMapper();
//		List<Eten> listEten = objectMapper.readValue(content, new TypeReference<List<Eten>>() {
//		});
//
//		for (Eten e : listEten) {
//			AWEtenRepository.save(e);
//			System.out.println("Saved: " + e.getName());
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

		AWEtenRepository.save(eten);
		return new RedirectView("/eten");
	}

	public float PortionCalc(float value, int weight) {
		float current = ((value * (weight / 100)) / 4);
		return current;
	}

}