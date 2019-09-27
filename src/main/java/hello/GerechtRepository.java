package hello;

import java.util.ArrayList;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface GerechtRepository extends CrudRepository<Gerecht, Integer> {

	Gerecht findById(int id);

	ArrayList<Gerecht> findAll(Sort sort);

}
