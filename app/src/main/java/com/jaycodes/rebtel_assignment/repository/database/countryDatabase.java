package com.jaycodes.rebtel_assignment.repository.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.jaycodes.rebtel_assignment.repository.database.converters.ArrayListConverter;
import com.jaycodes.rebtel_assignment.repository.database.dao.countryDao;
import com.jaycodes.rebtel_assignment.repository.models.countryModel;



@Database(entities = {countryModel.class}, version = 1) //define entities here
@TypeConverters({ArrayListConverter.class}) //type converter to convert our entity arrayList to string and save to Database
public abstract class countryDatabase extends RoomDatabase {
    private static countryDatabase countryDatabase;

    public abstract countryDao countryDao(); //define room dao methods here

//get access to single instance of database add database and build,
// also add fallbackToDestructiveMigration in case of version change
    public static synchronized countryDatabase getInstance(Context context) {

        if (countryDatabase == null) {
            countryDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    countryDatabase.class, "countryList").fallbackToDestructiveMigration().build();
        }
        return countryDatabase;
    }

}
