package net.arpcentral.pizzadoh.models;

import android.util.Log;

/**
 * Created by jarp on 6/26/16.
 */
public class Amount {
    private Double quantity;

    public Amount(int _quantity){
        puts(_quantity);
        quantity = 1.0 * _quantity;
    }

    public Amount(String _quantity){
        puts(_quantity);
        quantity = 1.0 * Integer.parseInt(_quantity);
    }
    public Amount(Double _quantity){
        quantity = _quantity;
    }

    public Double toDouble(){
        return quantity;
    }

    public String toString(){
        int new_value = quantity.intValue();
        return Integer.toString(new_value);
    }

    public Integer toInteger(){
        return quantity.intValue();
    }

    private void puts(int x){
        //Log.d("PUTS", "init amount with " + x);
    }

    private void puts(String x){
        //Log.d("PUTS", "init amount with " + x);
    }
}
