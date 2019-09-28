package hello;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "weight")
public class Weight {
	@Id
	private int id;

	private Date datum;
	private float weight;

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
		Date foundDate = new SimpleDateFormat("dd-MM-yy").parse(datum);
		this.datum = foundDate;
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

}
