package hello;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mealset")
public class MealSet {
	@Id
	private int id;
	private int ontbijtID;
	private int snackID;
	private int dinnerID;
	private Date date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOntbijtID() {
		return ontbijtID;
	}

	public void setOntbijtID(int ontbijtID) {
		this.ontbijtID = ontbijtID;
	}

	public int getSnackID() {
		return snackID;
	}

	public void setSnackID(int snackID) {
		this.snackID = snackID;
	}

	public int getDinnerID() {
		return dinnerID;
	}

	public void setDinnerID(int dinnerID) {
		this.dinnerID = dinnerID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public MealSet(int id, int ontbijtID, int snackID, int dinnerID, int soortId, Date date) {
		super();
		this.id = id;
		this.ontbijtID = ontbijtID;
		this.snackID = snackID;
		this.dinnerID = dinnerID;
		this.date = date;
	}

	public MealSet() {

	}

}
