package hello.Weight.Chart;

import hello.Weight.Weight;
import hello.Weight.WeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
	 * @return de Gegevens in een Chart Reponse
	 */
	public ChartsResponse getChartData() {

		ArrayList<Weight> list = AWWeightRepository.findAll(Sort.by(Direction.ASC, "datum"));

		Date first = list.get(0).getDatum();
		Date last = list.get(list.size() - 1).getDatum();

		// Initializeer objecten die gevuld moeten worden
		ChartsResponse chartsResponse = new ChartsResponse();
		List<Dataset> datasets = new ArrayList<>();
		Dataset actualWeight = new Dataset();
		Dataset linearWeight = new Dataset();
		List<Float> actualValue = new ArrayList<>();
		List<Float> linearValue = new ArrayList<>();
		List<String> dates = new ArrayList<>();
		float[] regression = getLinearRegression(list);
		float delta = regression[0];
		float starting = regression[1];
		float current = starting;

		// Loop waar voor iedere maand de gegevens worden ingevuld in de lijsten
		for (int i = 0; i < list.size(); i++) {
			Weight w = list.get(i);
			float weight = w.getWeight();
			int days = getDifferenceInDays(first, w.getDatum());
			current = starting + (days * delta);
			BigDecimal bd = new BigDecimal(current).setScale(2, RoundingMode.HALF_UP);
			linearValue.add(bd.floatValue());
			dates.add(w.getDisplayDate()); // Haal een string op met de Maand en jaar gegevens
			actualValue.add(weight);

			// hier dan ook nog linear toevoegen
		}

		float weightToGo = starting - 70;
		int totalDays = Math.abs(Math.round(weightToGo / delta));
		int daysMonitored = getDifferenceInDays(first, last);
		int daysToGo = totalDays - daysMonitored;
		int loops = Math.abs((daysToGo / 4) + 4);
		last = increaseDateBy(last, 4);

		for (int j = loops; j > 0 + 1; j--) {
			current = current + (4 * delta);
			BigDecimal bd = new BigDecimal(current).setScale(2, RoundingMode.HALF_UP);
			linearValue.add(bd.floatValue());
			dates.add(getDisplayDate(last)); // Haal een string op met de Maand en jaar gegevens
			last = increaseDateBy(last, 4);
		}

		linearWeight.setValue(linearValue);
		linearWeight.setName("Expected");
		actualWeight.setValue(actualValue);
		actualWeight.setName("Gewicht");
		datasets.add(actualWeight);
		datasets.add(linearWeight);

		// Zet de labels (naam van de gegevens op de x-as) en de Datasets
		chartsResponse.setLables(dates);
		chartsResponse.setDatasets(datasets);

		return chartsResponse;
	}

	public static float twoDecimail(float f, int decimals) {
		BigDecimal bd = new BigDecimal(f).setScale(decimals, RoundingMode.HALF_UP);
		return bd.floatValue();
	}

	public float[] getLinearRegression(ArrayList<Weight> list) {
		float weightTotal = 0;
		int dateTotal = 0;

		Date start = list.get(0).getDatum();

		for (Weight w : list) {
			weightTotal += w.getWeight();
			dateTotal += getDifferenceInDays(start, w.getDatum());

		}

		float weightMean = weightTotal / list.size();
		float dayMean = dateTotal / list.size();

		float num = 0;
		float dem = 0;

		for (Weight w : list) {

			num += (getDifferenceInDays(start, w.getDatum()) - dayMean) * (w.getWeight() - weightMean);
			dem += (getDifferenceInDays(start, w.getDatum()) - dayMean)
					* (getDifferenceInDays(start, w.getDatum()) - dayMean);
		}

		float delta = num / dem;

		float starting = weightMean - (dayMean * delta);

		return new float[]{delta, starting};

	}

	public String getDisplayDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
		return dateFormat.format(date);
	}

	public static int getDifferenceInDays(Date start, Date end) {
		long diff = end.getTime() - start.getTime();
		return (int) Math.ceil(diff / (1000 * 60 * 60 * 24));
	}

	public static Date increaseDateBy(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, days);
		return c.getTime();
	}
}