package hello.Gerecht;

import java.util.ArrayList;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface MealSetRepository extends CrudRepository<MealSet, Integer> {
	MealSet findById(int id);

	ArrayList<MealSet> findAll(Sort sort);

	ArrayList<MealSet> findAll();
}
