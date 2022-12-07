package utilities;

public class RecoverTestItems {
	
	ElementLocator locator = new ElementLocator();
	PropertyFileReader reader = new PropertyFileReader();
	
	public void TestSelfHealmethod()
	{
	try {
		locator.writeToFile(reader.getFileType());
		System.out.println("LocatorUpdateCompleted");
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
}
