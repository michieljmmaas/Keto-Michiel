package hello.Weight.Chart;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dit is de controller van de Chart API. Dit is handig voor het generen van de
 * overzichten van de ECManage rapportages en grafieken
 * 
 * @author Michiel
 *
 */
@RestController
public class ChartController {

	@Autowired
	private ChartsService AWChartsService; // De Service maakt contact met de API en geeft de goede informatie door

	/**
	 * Mapping van de '/viewAllCharts' endpoint. Hiermee haal je de gegevens op van
	 * het de overzicht grafiek met alle omgevingen
	 * 
	 * @return een ChartResponse list die gebruikt wordt voor de grafiek
	 */
	@RequestMapping(value = "/viewAllCharts", method = RequestMethod.GET)
	public List<ChartsResponse> allReponse() {
		List<ChartsResponse> chartsResponse = new ArrayList<>();
		chartsResponse.add(AWChartsService.getChartData());
		return chartsResponse;
	}

}