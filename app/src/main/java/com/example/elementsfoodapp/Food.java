package com.example.elementsfoodapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "food_table")
public class Food {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "food")
    private String mFood;

    @NonNull
    @ColumnInfo(name = "effect")
    private String mEffect;

    @NonNull
    @ColumnInfo(name = "type")
    private String mType;

    @NonNull
    @ColumnInfo(name = "element")
    private String mElement;

    @NonNull
    @ColumnInfo(name = "flavor")
    private String mFlavor;

    @NonNull
    @ColumnInfo(name = "thermal_effect")
    private String mThermalEffect;

    @NonNull
    @ColumnInfo(name = "target_organ")
    private String mTargetOrgan;

    public Food(@NonNull String food,
                @NonNull String effect,
                @NonNull String type,
                @NonNull String element,
                @NonNull String flavor,
                @NonNull String thermalEffect,
                @NonNull String targetOrgan) {

        this.mFood = food;
        this.mEffect = effect;
        this.mType = type;
        this.mElement = element;
        this.mFlavor = flavor;
        this.mThermalEffect = thermalEffect;
        this.mTargetOrgan = targetOrgan;
    }

    @Ignore
    public Food(int id,
                @NonNull String food,
                @NonNull String effect,
                @NonNull String type,
                @NonNull String element,
                @NonNull String flavor,
                @NonNull String thermalEffect,
                @NonNull String targetOrgan) {

        this.id = id;
        this.mFood = food;
        this.mEffect = effect;
        this.mType = type;
        this.mElement = element;
        this.mFlavor = flavor;
        this.mThermalEffect = thermalEffect;
        this.mTargetOrgan = targetOrgan;
    }

    public void setId(int id) { this.id = id; }
    public int getId() { return id; }
    public String getFood() { return this.mFood; }
    public String getEffect() { return this.mEffect; }
    public String getType() { return this.mType; }
    public String getElement() { return this.mElement; }
    public String getFlavor() { return this.mFlavor; }
    public String getThermalEffect() { return this.mThermalEffect; }
    public String getTargetOrgan() { return this.mTargetOrgan; }
}
