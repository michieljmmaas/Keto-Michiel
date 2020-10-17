package hello.Gerecht;

import hello.Eten.Eten;
import hello.Weight.Chart.ChartsService;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "gerecht")
public class Gerecht {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String naam;
	@Enumerated(EnumType.STRING)
	private Type type;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date datum;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "gerecht_eten", joinColumns = { @JoinColumn(name = "gerecht_id") }, inverseJoinColumns = {
			@JoinColumn(name = "eten_id") })
	private Set<Eten> ingredienten;

	public Gerecht() {

	}

	public void printInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("ID: ").append(this.getId()).append(" \n");
		sb.append("Naam: ").append(this.getNaam()).append("\n");
		sb.append("Type: ").append(this.getType()).append("\n");
		sb.append("Date: ").append(this.getDate()).append("\n");
		sb.append("Eten: " + "\n");
		for (Eten e : this.ingredienten) {
			sb.append("\t ").append(e.getName()).append("\n");
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

	public void setDatum(String datum) throws ParseException {
		this.datum = new SimpleDateFormat("yyyy/MM/dd").parse(datum);
	}

	public void setDashDatum(String datum) throws ParseException {
		this.datum = new SimpleDateFormat("yyyy-MM-dd").parse(datum);
	}

	public String getDays() {
		String result = "Nog niet gemaakt";
		if (this.datum != null) {
			Date datum = this.datum;
			Date now = new Date();
			int diff = ChartsService.getDifferenceInDays(now, datum);

			if (diff >= 0) {
				result = "Geplanned over: " + (diff + 1) + " dagen";
			} else {
				result = "Dagen Sinds: " + Math.abs(diff - 1);
			}
		}

		return result;
	}

	public String getTypeDisplay() {
		String result = "";
		switch (this.type) {
		case DINNER:
			result = "Dinner";
			break;
		case ONTBIJT:
			result = "Ontbijt";
			break;
		case SNACK:
			result = "Snack";
			break;
		case OTHER:
			result = "Anders";
			break;
		default:
			break;

		}
		return result;

	}

	public void setType(String type) {
		switch (type) {
		case "ONTBIJT":
			this.type = Type.ONTBIJT;
			break;
		case "DINNER":
			this.type = Type.DINNER;
			break;
		case "SNACK":
			this.type = Type.SNACK;
			break;
		case "OTHER":
			this.type = Type.OTHER;
			break;
		default:
			break;

		}
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

	public String format2Decimals(float a) {
		DecimalFormat decimalFormat = new DecimalFormat("#.00");
		return decimalFormat.format(a);
	}

	public String getTotalKcal() {
		float total = 0;
		for (Eten e : this.ingredienten) {
			total += e.getKcal();
		}
		return format2Decimals(total);
	}

	public String getTotalCarb() {
		float total = 0;
		for (Eten e : this.ingredienten) {
			total += e.getCarb();
		}
		return format2Decimals(total);
	}

	public String getTotalProt() {
		float total = 0;
		for (Eten e : this.ingredienten) {
			total += e.getProt();
		}
		return format2Decimals(total);
	}

	public String getTotalFat() {
		float total = 0;
		for (Eten e : this.ingredienten) {
			total += e.getFat();
		}
		return format2Decimals(total);
	}

	public float getTotalPrice() {
		float total = 0;
		for (Eten e : this.ingredienten) {
			total += e.getPrice();
		}
		return total;
	}
}