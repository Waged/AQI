package in.purelogic.aqi;


import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;

public class Palette {

    private static final int GOOD = 1;
    private static final int SATISFACTORY = 2;
    private static final int MODERATE = 3;
    private static final int BAD = 4;
    private static final int UNHEALTHY = 5;
    private static final int HAZARDOUS = 6;

    private static final String txtGOOD = "GOOD";
    private static final String txtSATISFACTORY = "MODERATE";
    private static final String txtMODERATE = "UNHEALTHY\n FOR GROUP";
    private static final String txtBAD = "UNHEALTHY";
    private static final String txtUNHEALTHY = "VERY UNHEALTHY";
    private static final String txtHAZARDOUS = "HAZARDOUS";

    public int getConditionAqi(int mAqi) {
        if (mAqi >= 0 && mAqi <= 50) {
            return GOOD;
        } else if (mAqi >= 51 && mAqi <= 100) {
            return  SATISFACTORY;
        } else if (mAqi >= 101 && mAqi <= 150) {
            return  MODERATE;
        } else if (mAqi >= 151 && mAqi <= 200) {
            return  BAD;
        } else if (mAqi >= 201 && mAqi <= 300) {
            return  UNHEALTHY;
        } else if (mAqi >= 301 ) {
            return  HAZARDOUS;
        }

        return 0;
    }

    public String getConditionString(int mAqi) {
        if (mAqi >= 0 && mAqi <= 50) {
            return txtGOOD;
        } else if (mAqi >= 51 && mAqi <= 100) {
            return  txtSATISFACTORY;
        } else if (mAqi >= 101 && mAqi <= 150) {
            return  txtMODERATE;
        } else if (mAqi >= 151 && mAqi <= 200) {
            return  txtBAD;
        } else if (mAqi >= 201 && mAqi <= 300) {
            return  txtUNHEALTHY;
        } else if (mAqi >= 301 ) {
            return  txtHAZARDOUS;
        }

        return "NA";

    }



    public int getConditionPm25(int mPm25) {
        if(mPm25 >= 1 && mPm25 <=15){
            return GOOD;
        }else if (mPm25 >15 && mPm25 <=41){
            return SATISFACTORY;
        }
        else if (mPm25 >41 && mPm25 <=65){
            return MODERATE;
        }
        else if (mPm25 >65 && mPm25 <=151){
            return BAD;
        }
        else if (mPm25 >151 && mPm25 <=250){
            return UNHEALTHY;
        }
        else if (mPm25 >250 && mPm25 <=501){
            return HAZARDOUS;
        }

        return 0;
    }



    public  @DrawableRes int getAqiDrawable(int condition){
        switch (condition) {
            case GOOD:
                return  R.drawable.ic_temp; // this will be the gif icon for the guy standing in the app
            case SATISFACTORY:

                return  R.drawable.ic_temp ;
            case MODERATE:

                return  R.drawable.ic_temp;
            case BAD:

                return  R.drawable.ic_temp;
            case UNHEALTHY:

                return  R.drawable.ic_temp;
            case HAZARDOUS:

                return  R.drawable.ic_temp ;
            default:

                return 0;
        }
    }



    public  @DrawableRes int getPm25Drawable(int condition){
        switch (condition) {
            case GOOD:
                return  R.drawable.ic_temp;
            case SATISFACTORY:

                return  R.drawable.ic_temp ;
            case MODERATE:

                return  R.drawable.ic_temp;
            case BAD:

                return  R.drawable.ic_temp;
            case UNHEALTHY:

                return  R.drawable.ic_temp;
            case HAZARDOUS:

                return  R.drawable.ic_temp ;
            default:

                return 0;
        }
    }

    int getTxtColor(Context mContext, int condition){
        int res;
        switch (condition) {
            case GOOD:
                res = R.color.good;
                break;
            case SATISFACTORY:
                res = R.color.satisfactory ;
                break;
            case MODERATE:
                res = R.color.moderate;
                break;
            case BAD:
                res = R.color.bad;
                break;
            case UNHEALTHY:
                res = R.color.unhealthy;
                break;
            case HAZARDOUS:
                res = R.color.hazardous ;
                break;
            default:
                return 0;
        }
        return ContextCompat.getColor(mContext, res);
    }





}
