package dk.mwnck.scraper;

import com.gargoylesoftware.htmlunit.WebClient;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import dk.mwnck.constants.Country;
import dk.mwnck.constants.Currency;
import dk.mwnck.dto.Car;

import java.util.ArrayList;
import java.util.List;

public class MobileDeScraper implements Scraper {


    @Override
    public Country getCountry() {
        return Country.DE;
    }

    @Override
    public List<Car> getCars(Car searchCar) {

        List<Car> cars = null;
        List<HtmlElement> items = (List<HtmlElement>) getPage(searchCar).getByXPath("//div[contains(@class,'cBox-body--resultitem')]");
        System.out.println("Fandt items: " + items.size());
        if (items.isEmpty()) {
            return null;
        } else {
            cars = new ArrayList<>();


            for (HtmlElement htmlElement : items) {
                HtmlElement km = htmlElement.getFirstByXPath(".//div[contains(@class,'vehicle-data--ad-with-price-rating-label')]");
                HtmlElement version = htmlElement.getFirstByXPath(".//span[contains(@class,'h3 u-text-break-word')]");
                HtmlElement price = htmlElement.getFirstByXPath(".//span[contains(@class, 'h3 u-block')]");
                int intKm = Integer.parseInt(km.asText().split(",")[1].replaceAll("[^0-9]+", ""));
                // filter on mileage.
                if (intKm >= searchCar.getKm() - 20000 && intKm <= searchCar.getKm()+20000)
                    cars.add(new Car(searchCar.getManufacturer(), searchCar.getModel(), price.asText(), intKm, searchCar.getYear(), getCountry(), Currency.EUR));
            }
        }

        return cars;

    }

    private HtmlPage getPage(Car car) {
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        try {
            String searchUrl = concatURL(car);
            System.out.println("Going to : " + searchUrl);
            //System.out.println(client.getPage(searchUrl));
            return client.getPage(searchUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String concatURL(Car car) {
        StringBuilder sb = new StringBuilder();
        //https://suchen.mobile.de/auto/opel-meriva-2012.html
        sb.append("https://suchen.mobile.de/auto/")
                .append(car.getManufacturer())
                .append("-")
                .append(car.getModel())
                .append("-")
                .append(car.getYear())
                .append(".html");

        return sb.toString();
    }

}


