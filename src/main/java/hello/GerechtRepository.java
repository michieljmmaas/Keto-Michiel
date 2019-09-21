package hello;

import org.springframework.data.repository.CrudRepository;

public interface GerechtRepository extends CrudRepository<Gerecht, Integer> {

	Gerecht findById(int id);
}
