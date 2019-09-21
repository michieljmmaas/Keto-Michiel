package hello;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "gerecht")
public class Gerecht {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String naam;
	private Type type;
	private Date date;

	@ManyToMany
	private ArrayList<Eten> eten;

	public Gerecht() {

	}

	public Gerecht(int id, String naam, Type type, Date date, ArrayList<Eten> eten) {
		super();
		this.id = id;
		this.naam = naam;
		this.type = type;
		this.date = date;
		this.eten = eten;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ArrayList<Eten> getEten() {
		return eten;
	}

	public void setEten(ArrayList<Eten> eten) {
		this.eten = eten;
	}

	enum Type {
		Ontbijt, Dinner, Snack, Other
	}
}