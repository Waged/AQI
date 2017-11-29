package in.purelogic.aqi.Database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Update;

import java.util.List;
import in.purelogic.aqi.PlaceRecord;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface DetailLocationDao {

    @Insert(onConflict = REPLACE)
    void insertAll(DetailLocation... detailLocations);

    @Update
    void updateAll(DetailLocation... detailLocations);

    @Query("SELECT * FROM detaillocation")
    List<DetailLocation> getAll();

    @Delete
    void deleteAll(DetailLocation... detailLocations);


   @Delete
    void deleteOne(DetailLocation detailLocation);

   // @Query("UPDATE placerecord SET Column1 = someValue WHERE columnId = :placeRecord.getLocationName()")
  //  void updateOne(PlaceRecord placeRecord);


}
