package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

	@Autowired
	EtenRepository AWEtenRepository;

	@RequestMapping("/")
	public String index() {
		Eten e = AWEtenRepository.findByName("Boter");
		System.out.println(e.getName());
		return "Greetings from Spring Boot!";
	}

}