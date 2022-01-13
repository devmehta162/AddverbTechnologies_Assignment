package com.global.addverb_tech_assignment;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "region_table")
public class DisplayRegionModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_num")
    private int id;

    @ColumnInfo(name = "common_name")
    private String common;

    @ColumnInfo(name = "capital_name")
    private String capital;

    @ColumnInfo(name = "borders_name")
    private String borders;

    @ColumnInfo(name = "png_name")
    private String png;

    @ColumnInfo(name = "region_name")
    private String region;

    @ColumnInfo(name = "subregion_name")
    private String subregion;

    @ColumnInfo(name = "population_count")
    private String population;

    @ColumnInfo(name = "languages_name")
    private String languages;

    public DisplayRegionModel(String common, String capital, String borders, String png, String region, String subregion, String population, String languages) {
        this.common = common;
        this.capital = capital;
        this.borders = borders;
        this.png = png;
        this.region = region;
        this.subregion = subregion;
        this.population = population;
        this.languages = languages;
    }

    @Ignore
    public DisplayRegionModel() {
    }

    public String getCommon() {
        return common;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getBorders() {
        return borders;
    }

    public void setBorders(String borders) {
        this.borders = borders;
    }

    public String getPng() {
        return png;
    }

    public void setPng(String png) {
        this.png = png;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }
}
