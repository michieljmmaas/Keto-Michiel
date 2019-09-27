package hello;

import org.springframework.data.repository.CrudRepository;

public interface MealSetRepository extends CrudRepository<MealSet, Integer> {
	MealSet findById(int id);
}
