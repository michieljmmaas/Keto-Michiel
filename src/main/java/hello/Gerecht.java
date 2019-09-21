package hello;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "gerecht")
public class Gerecht {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String naam;
	@Enumerated(EnumType.STRING)
	private Type type;
	private Date datum;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "gerecht_eten", joinColumns = { @JoinColumn(name = "gerecht_id") }, inverseJoinColumns = {
			@JoinColumn(name = "eten_id") })
	private Set<Eten> ingredienten;

	public Gerecht() {

	}

	public void printInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("ID: " + this.getId() + " \n");
		sb.append("Naam: " + this.getNaam() + "\n");
		sb.append("Type: " + this.getType() + "\n");
		sb.append("Date: " + this.getDate() + "\n");
		sb.append("Eten: " + "\n");
		for (Eten e : this.ingredienten) {
			sb.append("\t " + e.getName() + "\n");
		}
		String text = sb.toString() + "\n";
		System.out.println(text);

	}

	public Gerecht(int id, String naam, Type type, Date datum) {
		super();
		this.id = id;
		this.naam = naam;
		this.type = type;
		this.datum = datum;
	}

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

	public Set<Eten> getIngredienten() {
		return ingredienten;
	}

	public void setIngredienten(Set<Eten> ingredienten) {
		this.ingredienten = ingredienten;
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
		return datum;
	}

	public void setDate(Date datum) {
		this.datum = datum;
	}

	enum Type {
		ONTBIJT, DINNER, SNACK, OTHER
	}
}