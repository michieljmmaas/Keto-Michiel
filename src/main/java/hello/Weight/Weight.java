package hello.Weight;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "weight")
public class Weight {
	@Id
	private int id;

	private Date datum;
	private float weight;
	private float delta;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public void setDatum(String datum) throws ParseException {
		this.datum = new SimpleDateFormat("dd-MM-yy").parse(datum);
	}
	
	public void setDatumJS(String datum) throws ParseException {
		this.datum = new SimpleDateFormat("yyyy-MM-dd").parse(datum);
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public Weight(int id, Date datum, float weight) {
		super();
		this.id = id;
		this.datum = datum;
		this.weight = weight;
	}

	public Weight() {

	}
	

	public String getDisplayDate() {
		Date date = this.datum;
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
		return dateFormat.format(date);
	}

	public float getDelta() {
		return delta;
	}

	public void setDelta(float delta) {
		this.delta = delta;
	}

}
