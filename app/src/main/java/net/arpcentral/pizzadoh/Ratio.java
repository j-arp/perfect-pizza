package net.arpcentral.pizzadoh;

/**
 * Created by jarp on 6/15/16.
 */
public class Ratio {
    public int amount = 0;
    public int water = 0;
    public int flour = 0;
    public String type = "";

    public Ratio(String _type, int _amount){
        flour = _amount * 125;
        water = _amount * getPercent(_type);
    }

    public String getFlour(){
       return String flour.toString();
    }

    public String getWater(){
        return water.toString();
    }

    public String getWater(){

    }

    private int getPercent(String _type) {
        int percent = 0;
        switch (_type){
            case "Thin":
                percent = 35 / 100;
                break;
            case "New York":
                percent = 33 / 100;
                break;
            case "Pan":
                percent = 30 / 100;
                break;
        }
        return percent;
    }
}
