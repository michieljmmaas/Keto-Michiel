package hello.Weight;

import java.util.ArrayList;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface WeightRepository extends CrudRepository<Weight, Integer> {
	Weight findById(int id);

	ArrayList<Weight> findAll(Sort sort);

	ArrayList<Weight> findAll();
}
