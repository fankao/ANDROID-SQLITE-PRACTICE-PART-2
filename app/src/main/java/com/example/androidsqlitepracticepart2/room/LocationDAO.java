package com.example.androidsqlitepracticepart2.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LocationDAO {
    @Query("select * from locations")
    List<Locations> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void create(Locations locations);

    @Update
    public void update(Locations locations);

    @Delete
    public void delete(Locations locations);
}
