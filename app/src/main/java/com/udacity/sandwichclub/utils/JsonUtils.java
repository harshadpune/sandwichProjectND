package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject sandwichJson = new JSONObject(json);
            JSONObject nameDetailsJson = sandwichJson.optJSONObject(JsonConstants.JSON_NAME);
            sandwich.setMainName(nameDetailsJson.optString(JsonConstants.JSON_MAIN_NAME));
            sandwich.setAlsoKnownAs(convertJsonArrayToList(nameDetailsJson.optJSONArray(JsonConstants.JSON_ALSO_KNOWN_AS)));
            sandwich.setPlaceOfOrigin(sandwichJson.optString(JsonConstants.JSON_PLACE_OF_ORIGIN));
            sandwich.setDescription(sandwichJson.optString(JsonConstants.JSON_DESCRIPTION));
            sandwich.setImage(sandwichJson.optString(JsonConstants.JSON_IMAGE));
            sandwich.setIngredients(convertJsonArrayToList(sandwichJson.optJSONArray(JsonConstants.JSON_INGREDIENTS)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }

    private static List<String> convertJsonArrayToList(JSONArray jsonArrayToConvert){
            List<String> alsoKnownAsList = new ArrayList<String>();
            if(jsonArrayToConvert !=null){
                for (int i=0; i<jsonArrayToConvert.length(); i++) {
                    try {
                        alsoKnownAsList.add(jsonArrayToConvert.getString(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            return alsoKnownAsList;
    }

}
