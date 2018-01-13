package in.purelogic.aqi.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;



@Database(entities = {DetailLocation.class}, version = 7)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DB_NAME = "app_db";
    public abstract DetailLocationDao getDetailLocationDao();


}