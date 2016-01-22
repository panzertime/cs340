package shared.models.translator;

/**
 * To be used by the server facade to translate objects from the server proxy.
 * Objects can either come in as a JSONObject and be translated to an object
 * from the game model or objects from the game model can be translated to
 * a JSON object.
 */
public class Translator {
	
	/**
	 * Takes in Java objects and uses the toString to parse it as a JSON object.
	 * @pre Java objects to String is implemented to convert it to a JSON
	 * @post A JSON object representing the JavaObject
	 * @param obj Java object to converted
	 * @return JSONObject
	 */
	public JSONObject fromObjtoJSON(Object[] obj) {
		JSONObject result = null;
		
		return result;
	}
	
	/**
	 * Takes the JSON file and places the fields in an array of Objects. This is
	 * then passed to a constructor which uses the information to fill in the java
	 * object.
	 * @pre JSON is valid
	 * @post The corresponding Java object is created
	 * @param json
	 * @return JavaObject
	 */
	public Object[] fromJSONtoObj(JSONObject json) {
		Object[] result = null;
		
		return result;
	}

}