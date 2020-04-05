package hello.Weight.Chart;

import java.util.List;

/**
 * Dit is een model die gebruikt wordt bij het maken van de de Chart JS
 * grafieken. Het houdt alle gegevens van een onderwerp en een bijbehorende naam
 * 
 * @author Michiel
 *
 */
@SuppressWarnings(value = { "all" })
public class Dataset {

	private String name;
	private List<Float> value;

	/*
	 * Standaard Constructors
	 */
	public Dataset() {

	}

	public Dataset(String name, List<Float> value) {
		super();
		this.name = name;
		this.value = value;
	}

	/*
	 * Standaard Getters en Setters
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Float> getValue() {
		return value;
	}

	public void setValue(List<Float> value) {
		this.value = value;
	}
}