package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

	@Autowired
	EtenRepository AWEtenRepository;

	@RequestMapping("/")
	public String index() {
		Eten e = AWEtenRepository.findByName("Boter");
		System.out.println(e.getName());
		return "index";
	}

}