package hello.Gerecht;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface MealSetRepository extends CrudRepository<MealSet, Integer> {
	MealSet findById(int id);

	ArrayList<MealSet> findAll(Sort sort);

	@Override
	ArrayList<MealSet> findAll();
}
