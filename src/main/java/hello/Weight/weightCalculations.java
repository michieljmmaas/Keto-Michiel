package hello.Weight;

import hello.Weight.Chart.ChartsService;

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
	private float linearStarting;
	private Date startingDate;

	public Date getStartingDate() {
		return startingDate;
	}

	public void setStartingDate(Date startingDate) {
		this.startingDate = startingDate;
	}

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

	public float getLinearStarting() {
		return linearStarting;
	}

	public void setLinearStarting(float linearStarting) {
		this.linearStarting = linearStarting;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public weightCalculations(int weight, float start, float delta, Date startDate, float linearStarting,
			Date startingDate) {
		super();
		this.weight = weight;
		this.start = start;
		this.delta = delta;
		this.linearStarting = linearStarting;
		this.startDate = startDate;
		this.startingDate = startingDate;
	}

	public float getVerschil() {
		float verschil = weight - start;
		BigDecimal bd = new BigDecimal(verschil).setScale(2, RoundingMode.HALF_UP);
		return bd.floatValue();
	}

	public int getDagenTot() {
		int dagen = (int) Math.ceil((this.weight - this.linearStarting) / this.delta);
		Date later = ChartsService.increaseDateBy(this.startingDate, dagen);
		return ChartsService.getDifferenceInDays(this.startDate, later);
	}

	public int getSets() {
		return (int) Math.ceil(getDagenTot() / 4);
	}

	public String getDatum() {
		Date d = ChartsService.increaseDateBy(this.startDate, getDagenTot());
		DateFormat dateFormat = new SimpleDateFormat("MMM d");
		String display = dateFormat.format(d);
		return display;
	}

}
