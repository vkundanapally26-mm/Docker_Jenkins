package utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.openqa.selenium.support.How;

public class PageObjectGenerator implements FileWrite {

	private static int count = 1;
	private static int pagecount = 0;
	private String nameF = "PageObject";
	private static String name1 = "locator";
	File file;
	StringBuilder build;
	String[] removewords = { "static", "media", "target", "blank", "error", "none", "password", "username", "data-test",
			"data", "id", "placeholder", "autocapitalize", "type", "div", "class", "input", "textarea", "select",
			"span", "button", "title", "autocorrect", "http://www", "https", "www", "com", "jpg", "href", "src",
			"and" };

	protected String formatWebElement(String how, String using) {
		build = new StringBuilder();

		build.append("\n").append("\t").append("\t").append("@FindBy(how=" + how + ",using=\"" + using + "\")")
				.append("\n").append("\t").append("\t");

		if (!"How.XPATH".equalsIgnoreCase(how)) {
			build.append("public WebElement " + using.replaceAll("[^\\w\\s]", "") + ";").append("\n");
			return new String(build);
		}
		build.append("public WebElement xpath_" + count++ + ";").append("\n");
		return new String(build);
	}

	protected String formatWebElements(String how, String usingn, String using) {
		build = new StringBuilder();

		if (!"By.xpath".equalsIgnoreCase(how)) {
			build.append("\n").append("\t").append("public static ")
					.append("By " + usingn.replaceAll("[^\\w\\s]", "") + "").append(" = " + how + "(\"" + using + "\")")
					.append(";").append("\n");
			return new String(build);
		}

		build.append("\n")
				// .append("By xpath_" + count++ +" = ")
				.append("\t").append("public static ")
				// .append("By " + removeIt(usingn) +"")
				.append("By " + usingn.replaceAll("[^\\w\\s]", "") + "").append(" = " + how + "(\"" + using + "\")")
				.append(";").append("\n");
		return new String(build);
	}

	private String getWebElementString(String how, String using) {

		if (!"class".equalsIgnoreCase(how))
			switch (How.valueOf(how.toUpperCase())) {
			case ID:
				return formatWebElement("How.ID", using);
			case NAME:
				return formatWebElement("How.NAME", using);
			case XPATH:
				return formatWebElement("How.XPATH", using);
			default:
				break;
			}

		return formatWebElement("How.CLASS_NAME", using);
	}

	private String getWebElementStrings(String how, String usingn, String using) {

		if (!"class".equalsIgnoreCase(how))
			switch (How.valueOf(how.toUpperCase())) {
			case ID:
				return formatWebElements("By.id", usingn, using);
			case NAME:
				return formatWebElements("By.name", usingn, using);
			case XPATH:
				return formatWebElements("By.xpath", usingn, using);
			default:
				break;
			}

		return formatWebElements("By.className", usingn, using);
	}

	@Override
	public boolean writeToFile(String fileName, List<LocatorModel> dData) {
		fileName = splitText(fileName);
		pagecount = pagecount + 1;
		name1 = fileName + pagecount;
		count = 1;
		file = new File(System.getProperty("user.dir") + "/src/test/java/pageobjects/" + fileName + "" + nameF
				+ pagecount + ".java");
		List<LocatorModel> dDatas = removeDublicates(dData);
		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		try (FileWriter fWrite = new FileWriter(file, false)) {

			fWrite.append("package pageobjects;").append("\n\n").append("import org.openqa.selenium.By;").append("\n\n")
					.append("public class " + fileName + "" + nameF + pagecount + " {" + "\n");

			for (LocatorModel model : dDatas) {
				fWrite.append(
						getWebElementStrings(model.getLocatorType(), model.getLocatorName(), model.getLocatorValue()));
			}
			fWrite.append("\n").append("}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public String removeIt(String textRemake) {
		textRemake = textRemake.toLowerCase();
		textRemake = noAlphnumericChar(textRemake);
		textRemake = textRemake.replaceAll("[^a-zA-Z0-9]", "_");
		textRemake = textRemake.replaceAll("\\d\\d", "");
		for (int i = 0; i < removewords.length; i++) {
			textRemake = textRemake.replaceAll(removewords[i], "");
		}

		textRemake = textRemake.replaceAll("^_+", "");
		textRemake = manageString(textRemake);
		textRemake = textRemake.replaceAll("(?<=\\s|^)(?=\\S)", name1 + "_");
		textRemake = noRepeatedWord(textRemake);
		textRemake = textRemake.replaceAll("_+$", "");
		// textRemake = removeConsecutiveDuplicates(textRemake);
		textRemake = textRemake.replaceAll("(?i)([^a-zA-Z0-9])\\1+", "$1");

		if (textRemake.equals(null) || textRemake.equals(""))
			textRemake = name1;

		return textRemake;
	}

	public String removeConsecutiveDuplicates(String input) {
		if (input.length() <= 1)
			return input;
		if (input.charAt(0) == input.charAt(1))
			return removeConsecutiveDuplicates(input.substring(1));
		else
			return input.charAt(0) + removeConsecutiveDuplicates(input.substring(1));
	}

	public String manageString(String textRemake) {
		String result = null;
		try {
			if (textRemake.length() < 35)
				result = splitwise(textRemake);
			else if (textRemake.length() < 40)
				result = splitwise(textRemake);
			else
				result = splitwise(textRemake.substring(0, 45));
		} catch (Exception e) {
			result = textRemake;
		}

		return result;
	}

	public String splitwise(String textRemake) {
		String[] splitString = textRemake.split("_");
		textRemake = "";
		for (int i = 0; i < (splitString.length) - 1; i++) {
			textRemake = textRemake + "_" + splitString[i];
		}
		return textRemake;
	}

	public String splitTitle(String textRemake) {
		String[] splitString = textRemake.split("_");
		textRemake = "";
		for (int i = 0; i < (splitString.length) - 1; i++) {
			textRemake = textRemake + "_" + splitString[i];
		}
		return textRemake;
	}

	public String noRepeatedWord(String textRemake) {
		String result = "";
		String allWords[];
		try {
			allWords = textRemake.split("_");
			LinkedHashSet<String> set = new LinkedHashSet<String>(Arrays.asList(allWords));

			for (String word : set) {
				result = result + word + "_";
			}
		} catch (Exception e) {
			result = textRemake;
		}

		return result;
	}

	public List<LocatorModel> removeDublicates(List<LocatorModel> dataList) {
		String text;
		for (int s = 0; s < dataList.size(); s++) {
			text = dataList.get(s).getLocatorValue();
			text = removeIt(text);
			dataList.get(s).setLocatorName(text);
		}

		for (int i = 0; i < dataList.size(); i++) {
			int k = 0;
			for (int j = i + 1; j < dataList.size(); j++) {
				if ((dataList.get(i).getLocatorName()).equals(dataList.get(j).getLocatorName())) {
					k++;
					text = dataList.get(j).getLocatorName() + "_" + k;
					dataList.get(j).setLocatorName(text);
				}
			}
		}
		return dataList;
	}

	public String splitText(String textRemake) {
		String newOne = textRemake;
		textRemake = textRemake.replaceAll("[^a-zA-Z0-9]", "");
		textRemake = textRemake.replaceAll("([a-z])([A-Z])", "$1 $2");
		String[] splitString = textRemake.split(" ");
		textRemake = "";
		for (int i = 0; i < (splitString.length) && i < 4; i++) {
			textRemake = textRemake + "" + splitString[i];
		}

		if (textRemake.equals(null) || textRemake.equals(""))
			textRemake = newOne;

		return textRemake;
	}

	public String noAlphnumericChar(String textRemake) {
		String regex = "\\w*\\d+\\w*\\s*";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(textRemake);

		textRemake = matcher.replaceAll("");

		return textRemake;
	}

}
