package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class ResourceHelper {

	
	public static String getResourcePath(String resource) {
		String path = getBaseResourcePath() + resource;
		//System.out.println(path);
		return path;
	}
	
	public static String getBaseResourcePath() {
		//String path = ResourceHelper.class.getResource("/").getPath();
		String path = System.getProperty("user.dir") + "/src/test/resources/";
		//System.out.println(path);
		return path;
	}
	
	public static InputStream getResourcePathInputStream(String resource) throws FileNotFoundException {
		return new FileInputStream(ResourceHelper.getResourcePath(resource));
	}
	
}
