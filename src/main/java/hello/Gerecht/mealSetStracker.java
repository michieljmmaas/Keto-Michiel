package hello.Gerecht;

import org.springframework.stereotype.Service;

@Service
public class mealSetStracker {
	private int tracker;

	public int getTracker() {
		return tracker;
	}

	public void setTracker(int tracker) {
		this.tracker = tracker;
	}

	public mealSetStracker() {
		super();
		this.tracker = 1;
	}
	
}
