package in.purelogic.aqi;


import android.support.annotation.DrawableRes;
import org.json.JSONException;
import org.json.JSONObject;

public class OutdoorDataModel {


    // TODO: Declare the member variables here
    private String mPlaceName;
    private int mAqi;
    private int mPm25 ;
    private double mTemperature;
    private int mHumidity;
    private int mAqiDrawable;
    private int mPm25Drawable;
    private String mTimeStamp;
    private String mMessage;

    private static Palette mPalette = new Palette();

    public String getmTimeStamp() {
        return mTimeStamp;
    }

    public double getmTemperature() {
        return mTemperature;
    }

    public int getmAqi() {
        return mAqi;
    }

    public int getmHumidity() {
        return mHumidity;
    }

    public int getmPm25() {
        return mPm25;
    }

    public String getmPlaceName() {
        return mPlaceName;
    }

    public String getmMessage() {
        return mMessage;
    }


    public int getmAqiDrawable() {
        return mAqiDrawable;
    }
    public int getmPm25Drawable() {
        return mPm25Drawable;
    }

    public OutdoorDataModel(String mPlaceName, int mAqi,int mPm25 , double mTemperature, int mHumidity,  String mTimeStamp) {
        this.mPlaceName = mPlaceName;
        this.mTemperature = mTemperature;
        this.mAqi = mAqi;
        this.mHumidity = mHumidity;
        this.mPm25 = mPm25;
        this.mTimeStamp = mTimeStamp;
        this.mAqiDrawable  = mPalette.getAqiDrawable(mPalette.getConditionAqi(this.mAqi));
        this.mPm25Drawable = mPalette.getPm25Drawable(mPalette.getConditionPm25(this.mPm25));
    }

    public OutdoorDataModel(){}

    // TODO: Create a IndoorDataModel from a JSON:
    public static OutdoorDataModel fromJson(JSONObject jsonObject) {
        OutdoorDataModel outdoorDataModel = new OutdoorDataModel();
        try {
            outdoorDataModel.mAqi = jsonObject.getInt("aqi");
            outdoorDataModel.mPm25 = jsonObject.getInt("pm25");
            outdoorDataModel.mTemperature = jsonObject.getDouble("temp");
            outdoorDataModel.mHumidity = jsonObject.getInt("humi");
            outdoorDataModel.mTimeStamp = jsonObject.getString("time");
            outdoorDataModel.mAqiDrawable = mPalette.getAqiDrawable(mPalette.getConditionAqi(outdoorDataModel.mAqi));
            outdoorDataModel.mPm25Drawable = mPalette.getPm25Drawable(mPalette.getConditionPm25(outdoorDataModel.getmPm25()));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return outdoorDataModel;
    }


}
