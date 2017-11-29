package in.purelogic.aqi.Database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;


@Entity(tableName = "detaillocation")
public class DetailLocation {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "locationname")
    private String locationName;
    @ColumnInfo(name = "aqilevel")

    private int aqiLevel;
    @ColumnInfo(name = "temprature")
    private int temprature;

    @ColumnInfo(name = "humidity")
    private int humidity;

    public int getId() {
        return id;
    }

    public String getLocationName() {
        return locationName;
    }

    public int getAqiLevel() {
        return aqiLevel;
    }

    public int getTemprature() {
        return temprature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setAqiLevel(int aqiLevel) {
        this.aqiLevel = aqiLevel;
    }

    public void setTemprature(int temprature) {
        this.temprature = temprature;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }


}