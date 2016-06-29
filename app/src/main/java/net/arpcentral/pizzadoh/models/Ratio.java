package net.arpcentral.pizzadoh.models;

import android.util.Log;

/**
 * Created by jarp on 6/15/16.
 */
public class Ratio {
    public int amount = 0;
    public Double water = 0.0;
    public Double flour = 0.0;
    public String type = "";
    public Boolean with_starter = true;
    public int custom_starter_water = 0;
    public int custom_starter_flour = 0;

    public Ratio(String _type, int _amount, boolean _with_starter, int _custom_water, int _custom_flour){
        Log.d("RATIO", "CONSTRUCTED");
        flour = _amount * 125.0;
        water = flour * getPercent(_type);
        with_starter = _with_starter;
        custom_starter_water = _custom_water;
        custom_starter_flour = _custom_flour;
    }

    public Double getAdjustedFlour(){
        return flour - getStarterFlourAdjustment();
    }

    public Double getAdjustedWater(){
        return water - getStarterWaterAdjustment();
    }

    private int getStarterFlourAdjustment(){
        if (with_starter) {
            return custom_starter_flour == 0 ? 100 : custom_starter_flour;

        }

        else{
            return 0;
        }
    }

    private int getStarterWaterAdjustment(){
        if (with_starter) {
            return custom_starter_flour == 0 ? 150 : custom_starter_water;
        }

        else{
            return 0;
        }
    }

    private Double getPercent(String _type) {
        Double percent = 0.00;
        switch (_type){
            case "Thin":
                percent = (65.0 / 100.0);
                break;
            case "New York":
                percent = 67.0 / 100.0;
                break;
            case "Pan":
                percent = 70.0 / 100.0;
                break;
        }
        return percent;
    }
}
