package hello;

import org.springframework.data.repository.CrudRepository;

public interface EtenRepository extends CrudRepository<Eten, Integer> {
	Eten findByName(String name);
}
