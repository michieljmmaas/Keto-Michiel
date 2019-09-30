package hello;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class weightCalculations {
	private int weight;
	private float start;
	private float delta;
	private Date startDate;

	public float getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public float getStart() {
		return start;
	}

	public void setStart(float start) {
		this.start = start;
	}

	public float getDelta() {
		return delta;
	}

	public void setDelta(float delta) {
		this.delta = delta;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public weightCalculations(int weight, float start, float delta, Date startDate) {
		super();
		this.weight = weight;
		this.start = start;
		this.delta = delta;
		this.startDate = startDate;
	}

	public float getVerschil() {
		float verschil = weight - start;
		BigDecimal bd = new BigDecimal(verschil).setScale(2, RoundingMode.HALF_UP);
		return bd.floatValue();
	}

	public int getDagenTot() {
		return (int) (getVerschil() / delta);
	}

	public int getSets() {
		return getDagenTot() / 4;
	}

	public String getDatum() {
		Date d = ChartsService.increaseDateBy(this.startDate, getDagenTot());
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
		String display = dateFormat.format(d);
		return display;
	}

}
