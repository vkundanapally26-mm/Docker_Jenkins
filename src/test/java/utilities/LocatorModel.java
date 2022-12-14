package utilities;

public class LocatorModel {

	private String locatorType;

	public String getLocatorType() {
		return locatorType;
	}

	public String getLocatorValue() {
		return locatorValue;
	}

	private String locatorName;

	public String getLocatorName() {
		return locatorName;
	}

	public void setLocatorName(String name) {
		this.locatorName = name;
	}

	private String locatorValue;

	public LocatorModel(String locatorType, String locatorValue) {
		this.locatorType = locatorType;
		this.locatorValue = locatorValue;
	}

	@Override
	public String toString() {
		return locatorType + ":" + locatorValue;
	}

}
