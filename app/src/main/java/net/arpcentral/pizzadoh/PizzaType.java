package net.arpcentral.pizzadoh;

/**
 * Created by jarp on 6/15/16.
 */
public class PizzaType {
    public int id = 0;
    public String name = "";
    public int ratio = 0;

    public PizzaType(int _id, String _name, int _ratio){
        id = _id;
        name = _name;
        ratio = _ratio;
    }

    public String toString(){
        return name;
    }
}
