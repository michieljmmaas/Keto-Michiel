package hello;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

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
		List<Eten> foods = AWEtenRepository.findAll();
		model.addAttribute("foods", foods);
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

}