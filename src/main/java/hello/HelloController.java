package hello;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HelloController {

	@ModelAttribute("foodForm")
	public Eten createEten() {
		return new Eten();
	}

	@Autowired
	EtenRepository AWEtenRepository;

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
		List<Eten> foods = AWEtenRepository.findAll(Sort.by(Sort.DEFAULT_DIRECTION.ASC, "name"));
		model.addAttribute("foods", foods);
		model.addAttribute("Eten", new Eten());
		return "eten";
	}

	@RequestMapping("/gerechten")
	public String gerechten() {
		return "gerechten";
	}

	@RequestMapping("/rotation")
	public String rotation() {
		return "rotation";
	}

	@PostMapping("/foodPost")
	public RedirectView foodPost(@Valid @ModelAttribute("Eten") Eten eten, BindingResult bindResult,
			HttpServletRequest request, Model model) {
		if (bindResult.hasErrors()) {
			System.out.println("---Binding has Error");
			return new RedirectView("/");

		}
		AWEtenRepository.save(eten);
		return new RedirectView("/eten");
	}

}