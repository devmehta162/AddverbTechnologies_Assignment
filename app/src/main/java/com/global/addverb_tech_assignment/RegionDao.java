package com.global.addverb_tech_assignment;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RegionDao {

    @Insert
    void insert (DisplayRegionModel displayRegionModel);


    @Delete
    void delete(List<DisplayRegionModel> displayRegionModel);


    @Query("SELECT * FROM region_table")
    List<DisplayRegionModel> getAll();


    @Insert
    void insertmutipleRegion(List<DisplayRegionModel> displayRegionModelList);




}
