package hello.Eten;

import java.util.ArrayList;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface EtenRepository extends CrudRepository<Eten, Integer> {
	Eten findByName(String name);

	ArrayList<Eten> findAll(Sort sort);

	Eten findById(int id);
}
