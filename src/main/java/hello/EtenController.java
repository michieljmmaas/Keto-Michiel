package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Eten")
public class EtenController {

	@Autowired
	EtenRepository AWEtenRepository;

	@DeleteMapping(value = "/{id}")
	public void deleteID(@PathVariable int id) {
		Eten toDelete = AWEtenRepository.findById(id);
		AWEtenRepository.delete(toDelete);
	}

}
