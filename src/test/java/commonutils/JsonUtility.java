package commonutils;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonUtility {

	JSONParser jsonParser;

	public void jsonread(String filepath)
	{
		jsonParser = new JSONParser();
		try  {

			FileReader reader = new FileReader(filepath);
			//Read JSON file
			Object obj = jsonParser.parse(reader);
			JSONArray usersList = (JSONArray) obj;
			
			System.out.println("Users List-> "+usersList); 
			
			for(int i=0;i<usersList.size();i++) 
			{
				JSONObject users = (JSONObject) usersList.get(i);
				System.out.println("Users -> "+users);
				JSONObject user = (JSONObject) users.get("users");
				System.out.println("User -> "+user); 
				String username = (String) user.get("username");
				String password = (String) user.get("password");
				System.out.println("username : "+username+ " password : "+password);
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
