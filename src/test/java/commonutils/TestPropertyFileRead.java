package commonutils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class TestPropertyFileRead {

	
	static File file = new File("src/test/resources/configfile");
	static String userDirs = file.getAbsolutePath();
	static String envUrl = "";
	
	public String getThePropertyValue(String value) 
	{
		Properties fileEnv = null;
		try{
		FileReader reader = new FileReader(envUrl);  
		fileEnv=new Properties();  
		fileEnv.load(reader);  
		System.out.println(fileEnv.getProperty(value));  
		return fileEnv.getProperty(value);
		}catch (Exception e) {
			e.printStackTrace();
		return e.toString();
		}
	}

	public void envTest(String env)
	{
		 String baseEnv = "start";
		switch (env) {
		case "uat":
			baseEnv = "url for uat";
			System.out.println(baseEnv);
			this.envUrl = userDirs + "/test-uat.properties";
			break;
		case "prod":
			baseEnv = "url for prod";
			System.out.println(baseEnv);
			this.envUrl = userDirs+ "/test-prod.properties";
			break;
		case "dev":
			baseEnv = "url for dev";
			System.out.println(baseEnv);
			this.envUrl = userDirs+"/test-dev.properties";
			break;    
		default:
			System.out.println("url for qa");
			this.envUrl = userDirs+"/test-qa.properties";
		}

	}
}
