package hello;

import javax.persistence.*;

import org.springframework.lang.Nullable;

@Entity
@Table(name = "eten")
public class Eten {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String name;
	private float kcal;
	private float fat;
	private float prot;
	private float carb;
	private float price;
	private String disc;
	@Nullable
	private int weight;

	public Eten() {
	}


	public Eten(Integer id, String name, float kcal, float fat, float prot, float carb, float price, String disc,
			int weight) {
		super();
		this.id = id;
		this.name = name;
		this.kcal = kcal;
		this.fat = fat;
		this.prot = prot;
		this.carb = carb;
		this.price = price;
		this.disc = disc;
		this.weight = weight;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getKcal() {
		return kcal;
	}

	public void setKcal(float kcal) {
		this.kcal = kcal;
	}

	public float getFat() {
		return fat;
	}

	public void setFat(float fat) {
		this.fat = fat;
	}

	public float getProt() {
		return prot;
	}

	public void setProt(float prot) {
		this.prot = prot;
	}

	public float getCarb() {
		return carb;
	}

	public void setCarb(float carb) {
		this.carb = carb;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getDisc() {
		return disc;
	}

	public void setDisc(String disc) {
		this.disc = disc;
	}

}