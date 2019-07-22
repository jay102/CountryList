package com.jaycodes.rebtel_assignment.repository.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.jaycodes.rebtel_assignment.repository.models.countryModel;
import java.util.List;


@Dao
public interface countryDao {

    @Query("SELECT * FROM countryList")
    LiveData<List<countryModel>> getAll();

    @Insert
    void insert(List<countryModel> countryList);

    @Query("DELETE FROM countryList")
    void deleteAll();
}
/* Get access to all methods to retrieve and insert information into our database**/