package net.arpcentral.pizzadoh;

/**
 * Created by jarp on 6/15/16.
 */
public class PizzaType {
    public int id = 0;
    public String name = "";
    public int ratio = 0;

    public PizzaType(int id, String name, int ratio){
        id = id;
        name = name;
        ratio = ratio;
    }

    public String toString(){
        return name;
    }
}
