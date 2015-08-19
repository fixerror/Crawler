package webcrawler;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ProductCrawler {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		List<Product> product = new LinkedList<>();
		List<Page> pageUrl = getPageName();
		try {
			for (Page pageText : pageUrl) {
				List listProduct = new LinkedList<>();
				Document doc = Jsoup.connect(pageText.getNamePage()).get();
				Elements anchors = doc.select("td.views-field");
				for (Element anchor : anchors) {
					listProduct.add(anchor.toggleClass("").text());
				}
				Iterator it = listProduct.iterator();
				while (it.hasNext()) {
					Product pr = new Product();
					it.next();
					pr.setName(it.next().toString());
					try {
						pr.setProtein(Double.parseDouble(it.next().toString()));
						pr.setFat(Double.parseDouble(it.next().toString()));
						pr.setCarbohydrate(Double.parseDouble(it.next().toString()));
						pr.setKcal(Double.parseDouble(it.next().toString()));
						product.add(pr);
					} catch (NumberFormatException e) {
						continue;
					}
				}
			}
			printProduct(product);
		} catch (IOException e) {
			Logger.getLogger(ProductCrawler.class.getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
		}

	}

	public static List<Page> getPageName() {
		String urlsPage = "";
		List<Page> listPage = new LinkedList<>();
		String text[] = { "mushroom", "sausage", "cereals", "butter", "milk", "meal", "beef", "vegetable", "nut", "sea",
				"snack", "cheese", "sea", "raw", "fruit", "bread", "berry", "egg", "cake", "icecream", "tort",
				"chocolate", "alcohol", "drink", "juice", "salad", "soup", "mcdonalds", "japan", "baby", "sport" };
		Page page = null;
		for (int i = 0; i < text.length; i++) {
			page = new Page(urlsPage + text[i]);
			listPage.add(page);
		}
		return listPage;
	}

	public static void printProduct(List<Product> product) {
		for (Product productPrint : product)
			System.out.println(
					productPrint.getName() + " Protein: " + productPrint.getProtein() + "  Fat:" + productPrint.getFat()
							+ "  Carbohydrate:" + productPrint.getCarbohydrate() + " Kcal:" + productPrint.getKcal());
	}

}
