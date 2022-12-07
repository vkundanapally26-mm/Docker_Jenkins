package utilities;

import static com.codeborne.selenide.Selenide.open;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Collector;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.codeborne.selenide.WebDriverRunner;

import commonutils.GlobalCap;

public class ElementLocator {

	private PropertyFileReader rReader;
	private WebDriver dDriver;
	private Document dDocument;

	private static final String TAG_LINK = "a";
	private static final String TAG_BUTTON = "button";
	private static final String TAG_INPUT = "input";
	private static final String TAG_DROP_DOWN = "select";
	private static final String TAG_TEXT_AREA = "textarea";
	private static final String TAG_SPAN = "span";

	private static int REC_COUNT = 1;
	private static final String LOCATOR_ID = "id";
	private static final String LOCATOR_CLASS = "class";
	private static final String LOCATOR_NAME = "name";

	private LocatorModel getXpath(WebDriver aDriver, Element bElement, List<String> locatorList) {
		boolean flag = true;
		String locator = "";
		Iterator<Attribute> attIterator = bElement.attributes().iterator();
		ArrayList<String> locatorArrayList = new ArrayList<String>();

		if (REC_COUNT == 5 || bElement == null || locatorList.isEmpty())
			return null;

		REC_COUNT++;

		while (attIterator.hasNext()) {
			Attribute attribute = (Attribute) attIterator.next();
			if (IgnoreAttribute.ignoreAttribute.contains(attribute.getKey()) || attribute.getValue().isEmpty())
				continue;
			for (String s : locatorList) {
				locator = "//" + bElement.nodeName() + "[@" + attribute.getKey() + "='" + attribute.getValue() + "']"
						+ s;
				locatorArrayList.add(locator);
				if (isUnique(aDriver, By.xpath(locator))) {
					flag = false;
					break;
				}
				locator = "";
			}
			if (!flag)
				break;
		}

		if (locatorArrayList.isEmpty()) {
			for (String s : locatorList) {
				locator = "//" + bElement.nodeName() + s;
				locatorArrayList.add(locator);
				if (isUnique(aDriver, By.xpath(locator)))
					break;
				locator = "";
			}
		}

		return locator.length() == 0 ? getXpath(aDriver, bElement.parent(), locatorArrayList)
				: new LocatorModel("Xpath", locator);
	}

	private LocatorModel getXpath(WebDriver aDriver, Element bElement) {
		REC_COUNT = 1;
		String locator = "";
		Iterator<Attribute> attIterator = bElement.attributes().iterator();
		ArrayList<String> locatorList = new ArrayList<String>();

		while (attIterator.hasNext()) {
			Attribute attribute = (Attribute) attIterator.next();
			if (IgnoreAttribute.ignoreAttribute.contains(attribute.getKey()) || attribute.getValue().isEmpty())
				continue;
			locator = "//" + bElement.nodeName() + "[@" + attribute.getKey() + "='" + attribute.getValue() + "']";
			locatorList.add(locator);
			if (isUnique(aDriver, By.xpath(locator)))
				break;
			locator = "";
		}

		return locator.length() == 0
				? getXpath(aDriver,
						bElement.parent(), locatorList.isEmpty()
								? new ArrayList<String>(Arrays.asList("//" + bElement.nodeName())) : locatorList)
				: new LocatorModel("Xpath", locator);
	}

	private LocatorModel getUniqueLocator(WebDriver aDriver, Element bElement) {
		Attributes aAttributes = bElement.attributes();

		if (!aAttributes.get(LOCATOR_ID).isEmpty() && isUnique(aDriver, By.id(aAttributes.get(LOCATOR_ID)))) {
			return new LocatorModel(LOCATOR_ID, aAttributes.get(LOCATOR_ID));
		} else if (!aAttributes.get(LOCATOR_CLASS).isEmpty()
				&& isUnique(aDriver, By.className(aAttributes.get(LOCATOR_CLASS)))) {
			return new LocatorModel(LOCATOR_CLASS, aAttributes.get(LOCATOR_CLASS));
		} else if (!aAttributes.get(LOCATOR_NAME).isEmpty()
				&& isUnique(aDriver, By.name(aAttributes.get(LOCATOR_NAME)))) {
			return new LocatorModel(LOCATOR_NAME, aAttributes.get(LOCATOR_NAME));
		} else if (bElement.hasText()) {
			String xPath = "//" + bElement.nodeName() + "[normalize-space(text())='" + bElement.ownText().trim() + "']";
			if (isUnique(aDriver, By.xpath(xPath))) {
				return new LocatorModel("Xpath", xPath);
			}
		}
		// System.out.println("the attributes belements: " + bElement);
		return getXpath(dDriver, bElement);

	}

	private List<LocatorModel> getElementsByTag(String tagName, WebDriver aDriver) {
		Elements eleList = dDocument.getElementsByTag(tagName);
		List<LocatorModel> locator = new ArrayList<LocatorModel>();
		for (int i = 0; i < eleList.size(); i++) {
			locator.add(getUniqueLocator(aDriver, eleList.get(i)));
		}
		return locator;
	}

	private boolean isUnique(WebDriver driver, By locator) {
		try {
			return driver.findElements(locator).size() == 1;
		} catch (Exception e) {
		}
		return false;

	}

	public List<LocatorModel> getLocator(WebDriver aDriver) {
		List<LocatorModel> locatorList = new ArrayList<LocatorModel>();
		locatorList.addAll(getElementsByTag(TAG_LINK, aDriver));
		locatorList.addAll(getElementsByTag(TAG_BUTTON, aDriver));
		locatorList.addAll(getElementsByTag(TAG_DROP_DOWN, aDriver));
		locatorList.addAll(getElementsByTag(TAG_INPUT, aDriver));
		locatorList.addAll(getElementsByTag(TAG_TEXT_AREA, aDriver));
		locatorList.addAll(getElementsByTag(TAG_SPAN, aDriver));
		// locatorList.removeIf(new NullRemove());
		return locatorList;

	}

	private void openPage(String url) {
		try {
			dDocument = Jsoup.connect(url).get();
			/*
			 * dDocument = Jsoup.connect(url) .data("query", "Java")
			 * .userAgent("chrome") .cookie("auth", "token") .timeout(3000)
			 * .post();
			 */
			dDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			// dDriver.get(url);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ElementLocator() {
		rReader = new PropertyFileReader();
		dDriver = WebDriverRunner.getWebDriver();
		// dDriver = GlobalCap.gwebDriver;
		dDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	}

	public void writeToFile(FileType type) throws Exception {
		FileWrite writer = ObjectFactory.getObject(type);
		String currenturl = dDriver.getCurrentUrl();
		String currentTitle = dDriver.getTitle();
	
		writer.writeToFile(
				this.dDriver.getTitle().isEmpty() ? currenturl
						: this.dDriver.getTitle().replaceAll("[^\\w\\s]", "").replaceAll("\\s", ""),
				getloc(this.dDriver, currentTitle));
	}

	// 1st part
	public List<LocatorModel> getloc(WebDriver aDriver, String ObjectName) throws Exception {
		List<LocatorModel> locatorLists = new ArrayList<LocatorModel>();

		List<String> tags = Arrays.asList("a", "img", "input", "button");
		List<WebElement> wes = new ArrayList<WebElement>();
		List<WebElement> wesTemp = getWebElements(tags);

		for (int k = 0; k < wesTemp.size(); k++)
			if (getAttributes(wesTemp, k).size() != 0)
				if (!getAttributesAsString(wesTemp, k).contains("undefined"))
					wes.add(wesTemp.get(k));

		for (int i = 0; i < wes.size(); i++) {
			HashMap<String, String> attributes = getAttributes(wes, i);
			String xpathValue = "";
			int count = 1;
			for (String key : attributes.keySet()) {
				if (count == attributes.size())
					xpathValue = xpathValue + "@" + key.trim() + "='" + attributes.get(key).toString() + "'";
				else
					xpathValue = xpathValue + "@" + key.trim() + "='" + attributes.get(key).toString() + "'" + " and ";
				count++;
			}
			String aliasName = "";

			if (attributes.containsKey("id"))
				aliasName = attributes.get("id").toString() + "_";
			else if (attributes.containsKey("value"))
				aliasName = attributes.get("value").toString().toLowerCase().replace(" ", "_") + "_";
			else if (attributes.containsKey("href")) {
				aliasName = attributes.get("href").toString().toLowerCase().replace(" ", "_").replace("https://", "_")
						.replace("http://", "_").replace("/", "").replace(".", "_") + "_";
			}
			String xpath = "//" + wes.get(i).getTagName() + "[" + xpathValue + "]";

			// System.out.println("xpath: "+xpath);

			if ((attributes.size() == 1 && attributes.containsKey("href"))) {
				if (attributes.get("href").contains("http")) {
					if (isElementVisible(xpath, 2)) {

						/*
						 * System.out.println(ObjectName + "_" + aliasName +
						 * "alias" + i + ":" + "//" + wes.get(i).getTagName() +
						 * "[" + xpathValue + "]");
						 */
						locatorLists.add(
								new LocatorModel("Xpath", "//" + wes.get(i).getTagName() + "[" + xpathValue + "]"));
					}
				}
			} else {
				if (isElementVisible(xpath, 2)) {

					/*
					 * System.out.println(ObjectName + "_" + aliasName + "alias"
					 * + i + ":" + "//" + wes.get(i).getTagName() + "[" +
					 * xpathValue + "]");
					 */
					locatorLists
							.add(new LocatorModel("Xpath", "//" + wes.get(i).getTagName() + "[" + xpathValue + "]"));
				} else {
					String[] ss = xpathValue.split("and");
					for (int k = 0; k < ss.length; k++) {
						// System.out.println("ss:::::::"+ss[k]);
						String path = "//" + wes.get(i).getTagName() + "[" + ss[k] + "]";
						if (isElementVisible(path, 2)) {

							/*
							 * System.out.println(ObjectName + "_" + aliasName +
							 * "alias" + i + ":" + "//" +
							 * wes.get(i).getTagName() + "[" + ss[k] + "]");
							 */

							locatorLists
									.add(new LocatorModel("Xpath", "//" + wes.get(i).getTagName() + "[" + ss[k] + "]"));
						}
					}
				}
			}
		}
		wes.clear();

		for (int i = 0; i < locatorLists.size(); i++) {
			for (int j = i + 1; j < locatorLists.size(); j++) {
				if ((locatorLists.get(i).getLocatorValue()).equals(locatorLists.get(j).getLocatorValue())) {
					locatorLists.remove(j);
					j--;
				}
			}
		}
		return locatorLists;
	}

	// ==2nd part
	public List<WebElement> getWebElements(List<String> tags) {
		List<WebElement> wesTemp = null;
		List<WebElement> wes = new ArrayList<WebElement>();

		if (!tags.isEmpty())
			for (String tag : tags) {
				wesTemp = dDriver.findElements(By.tagName(tag));
				wes.addAll(wesTemp);
			}
		else
			wes = dDriver.findElements(By.id("*"));
		return wes;
	}

	public HashMap<String, String> getAttributes(List<WebElement> wes, int index) throws Exception {
		HashMap<String, String> attributes = new HashMap<String, String>();
		WebElement element = wes.get(index);
		JavascriptExecutor executor = (JavascriptExecutor) dDriver;
		Object obj = executor.executeScript(
				"var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;",
				element);
		String objString = obj.toString().replace("}", "").replace("{", "");
		int len = objString.split(",").length;

		for (int i = 0; i < len; i++) {
			try {
				if (!objString.split(",")[i].trim().endsWith("="))
					attributes.put(objString.split(",")[i].split("=")[0].trim(),
							objString.split(",")[i].split("=")[1].trim());
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.print("");
			} catch (Exception e) {
				System.out.println("exception:" + e.toString());
			}

		}
		// System.out.println("attributes: "+attributes);
		return attributes;
	}

	public String getAttributesAsString(List<WebElement> wes, int index) throws Exception {
		WebElement element = wes.get(index); // Your element
		JavascriptExecutor executor = (JavascriptExecutor) dDriver;
		Object obj = executor.executeScript(
				"var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;",
				element);
		// System.out.println("obj.toString(): "+obj.toString());
		return obj.toString();
	}

	public boolean isElementVisible(String xpath, int waitTime) throws Exception {
		boolean flag = false;
		try {
			if (xpath.contains("%"))
				flag = false;
			else {
				WebDriverWait wait = new WebDriverWait(dDriver, waitTime);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			return flag;
		}
	}

}
