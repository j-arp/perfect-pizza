package net.arpcentral.pizzadoh;

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

    public Ratio(String _type, int _amount, boolean _with_starter){
        Log.d("RATIO", "CONSTRUCTED");
        flour = _amount * 125.0;
        water = flour * getPercent(_type);
        with_starter = _with_starter;
    }

    public Double getAdjustedFlour(){

        return flour - getStarterFlourAdjustment();
    }

    public Double getAdjustedWater(){

        return water - getStarterWaterAdjustment();
    }

    private int getStarterFlourAdjustment(){
        if (with_starter) {
            return 100;
        }

        else{
            return 0;
        }
    }

    private int getStarterWaterAdjustment(){
        if (with_starter) {
            return 150;
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
