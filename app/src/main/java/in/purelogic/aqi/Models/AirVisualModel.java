package in.purelogic.aqi.Models;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class AirVisualModel {



    // TODO: Declare the member variables here
    private int mTemperature;
    private int mHumidity;
    private int mWindSpeed;
    private String mIcon;
    private int mAQI;

    public static AirVisualModel fromJson(JSONObject jsonObject) {
        AirVisualModel airVisualModel = new AirVisualModel();
        try {
            JSONObject job1 = jsonObject.getJSONObject("data").getJSONObject("current").getJSONObject("weather");
            JSONObject job2 = jsonObject.getJSONObject("data").getJSONObject("current").getJSONObject("pollution");
            airVisualModel.mTemperature = job1.getInt("tp");
            airVisualModel.mIcon = job1.getString("ic");
            airVisualModel.mHumidity = job1.getInt("hu");
            airVisualModel.mWindSpeed = job1.getInt("ws");
            airVisualModel.mAQI = job2.getInt("aqius");

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("json",e.toString());
            return null;
        }
        return airVisualModel;
    }



    public int getmTemperature() {
        return mTemperature;
    }

    public int getmHumidity() {
        return mHumidity;
    }

    public int getmWindSpeed() {
        return mWindSpeed;
    }

    public String getmIcon() {
        return mIcon;
    }

    public int getmAQI() {
        return mAQI;
    }











}
