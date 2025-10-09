package com.terra.terradisto.distosdkapp.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "projects")
public class ProjectCreate {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String location;
    public String sheetNumber;
    public String memo;

    public ProjectCreate(String name, String location, String sheetNumber, String memo) {
        this.name = name;
        this.location = location;
        this.sheetNumber = sheetNumber;
        this.memo = memo;
    }
}
