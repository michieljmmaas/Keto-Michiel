package hello.Weight.Chart;

import java.util.List;

/**
 * Dit is een model die gebruikt wordt voor het maken van de ChartJS grafieken,
 * en gestuurd naar de website voor verwerken
 * 
 * @author Michiel
 *
 */
@SuppressWarnings(value = { "all" })
public class ChartsResponse {
	private String appName;
	private List<String> lables;
	private List<Dataset> datasets;

	/*
	 * Standaard constructors
	 */
	public ChartsResponse(String appName, List<String> lables, List<Dataset> datasets) {
		super();
		this.appName = appName;
		this.lables = lables;
		this.datasets = datasets;
	}

	public ChartsResponse() {

	}

	/*
	 * Standaard getters en setters
	 */
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public List<String> getLables() {
		return lables;
	}

	public void setLables(List<String> lables) {
		this.lables = lables;
	}

	public List<Dataset> getDatasets() {
		return datasets;
	}

	public void setDatasets(List<Dataset> datasets) {
		this.datasets = datasets;
	}

}