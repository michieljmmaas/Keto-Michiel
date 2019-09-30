package hello;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Dit is een service die de goede gegevens van de ECManage Grafieken ophaalt,
 * verwerkt en verstuurd voor de ChartJS grafieken
 * 
 * @author Michiel
 *
 */
@Service
public class ChartsService {

	@Autowired
	private WeightRepository AWWeightRepository;

	/**
	 * Deze methode pakt de ecm_aggregate gegevens van een specifieke leverancier en
	 * maakt er een Chart Response van
	 * 
	 * @param name    naam van de Leverancier
	 * @param maxSize Maximale grote van hoeveel items er in de grafiek staan
	 * @return de Gegevens in een Chart Reponse
	 */
	public ChartsResponse getChartData() {

		ArrayList<Weight> list = AWWeightRepository.findAll();

		// Initializeer objecten die gevuld moeten worden
		ChartsResponse chartsResponse = new ChartsResponse();
		List<Dataset> datasets = new ArrayList<>();
		Dataset actualWeight = new Dataset();
//		Dataset linearWeight = new Dataset();
		List<Float> actualValue = new ArrayList<>();
//		List<Float> linearValue = new ArrayList<>();
		List<String> dates = new ArrayList<>();

		// Loop waar voor iedere maand de gegevens worden ingevuld in de lijsten
		for (int i = list.size()-1; i > 0; i--) {
			Weight w = list.get(i);
			float weight = w.getWeight();
			dates.add(w.getDisplayDate()); // Haal een string op met de Maand en jaar gegevens
			actualValue.add(weight);

			// hier dan ook nog linear toevoegen
		}

		actualWeight.setValue(actualValue);
		actualWeight.setName("Gewicht");
		datasets.add(actualWeight);

		// Zet de labels (naam van de gegevens op de x-as) en de Datasets
		chartsResponse.setLables(dates);
		chartsResponse.setDatasets(datasets);

		return chartsResponse;
	}
}