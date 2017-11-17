package in.purelogic.aqi.Models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDataModel {

    // TODO: Declare the member variables here
    private String mTemperature;
    private String mCity ;
    private String mIconName;
    private int mCondition;
    private double mHumidity;
    private double mWindSpeed;

    // TODO: Create a WeatherDataModel from a JSON:
    public static WeatherDataModel fromJson(JSONObject jsonObject){
        WeatherDataModel weatherData = new WeatherDataModel();
        try {
            weatherData.mCity = jsonObject.getJSONObject("city").getString("name");
            Log.d("city = ",weatherData.mCity+"");

            weatherData.mCondition=jsonObject.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherData.mIconName=updateWeatherIcon(weatherData.mCondition);
            Log.d("condition = ",weatherData.mCondition+"");

            double tempResult = jsonObject.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("temp")-273.15;
            int roundedValue = (int) Math.rint(tempResult);
            Log.d("temperature = ",tempResult+"");
            weatherData.mTemperature = Integer.toString(roundedValue);

            double humidityResult = jsonObject.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("humidity");
            weatherData.mHumidity = (int) Math.rint(humidityResult);
            Log.d("humidity = ",humidityResult+"");

            double windSpeedResult = jsonObject.getJSONArray("list").getJSONObject(0).getJSONObject("wind").getDouble("speed");
            Log.d("windSpeed = ",windSpeedResult+"");
            weatherData.mHumidity = (int) Math.rint(windSpeedResult);

        } catch (JSONException e) {
            e.printStackTrace();
            return null ;
        }

        return weatherData ;
    }

    // TODO: Uncomment to this to get the weather image name from the condition:
    private static String updateWeatherIcon(int condition) {
        if (condition >= 0 && condition < 300) {
            return "tstorm1";
        } else if (condition >= 300 && condition < 500) {
            return "light_rain";
        } else if (condition >= 500 && condition < 600) {
            return "shower3";
        } else if (condition >= 600 && condition <= 700) {
            return "snow4";
        } else if (condition >= 701 && condition <= 771) {
            return "fog";
        } else if (condition >= 772 && condition < 800) {
            return "tstorm3";
        } else if (condition == 800) {
            return "sunny";
        } else if (condition >= 801 && condition <= 804) {
            return "cloudy2";
        } else if (condition >= 900 && condition <= 902) {
            return "tstorm3";
        } else if (condition == 903) {
            return "snow5";
        } else if (condition == 904) {
            return "sunny";
        } else if (condition >= 905 && condition <= 1000) {
            return "tstorm3";
        }

        return "dunno";
    }

    // TODO: Create getter methods for temperature, city, and icon name:
    public String getmTemperature() {
        return mTemperature;
    }

    public String getmCity() {
        return mCity;
    }

    public String getmIconName() {
        return mIconName;
    }

    public double getmHumidity() {
         return mHumidity;
    }

    public double getmWindSpeed() {
        return mWindSpeed;
    }
}
