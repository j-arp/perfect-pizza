package net.arpcentral.pizzadoh.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class History {

    public static final String PREFS_NAME = "history";
    public Context context;
    public SharedPreferences settings;
    private SharedPreferences.Editor editor;
    public int id = 1;
    public int quantity = 0;
    public String type = "";
    public int starter_water = 0;
    public int starter_flour = 0;

    public History(int _quantity, String _type, int _starter_water, int _starter_flour, Context _context){
        // Log.d("HISTORY CONTSRUCT", "quant: " + _quantity + " type: " + _type + " water: " + _starter_water + " flour: " + _starter_flour );
        quantity = _quantity;
        type = _type;
        starter_flour = _starter_flour;
        starter_water = _starter_water;

        context = _context;
        settings = context.getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
    }

    public boolean using_starter(){
        if ( starter_water == 0 || starter_flour == 0 ){
            return false;
        }

        else{
            return true;
        }
    }

    public String toString(){
        return Integer.toString(quantity) + " " + type + " Pizzas with a starter of " + Integer.toString(starter_water) + "g water/" + Integer.toString(starter_flour) + "g flour";
    }

    public static boolean contains(History history){

        boolean result = false;
        ArrayList<History> all = History.fetch(history.context);

        for (History item : all) {
            Log.d("HISTORY#contains", "does history: " + item.toDelimited(true) + " contain? " + history.toDelimited(true));
            if (item.toDelimited(true).toString().equals(history.toDelimited(true).toString())){
                Log.d("HISTORY#contains", "yes! it was found");
                result = true;
                break;
            }
        }
        return result;
    }


    public String toDelimited(){
        // Log.d("HISTORY", "#toDelimited");
        return Integer.toString(id) + ":" + Integer.toString(quantity) + ":" + type + ":" + Integer.toString(starter_water) + ":" + Integer.toString(starter_flour);
    }

    public String toDelimited(boolean with_id){
        // Log.d("HISTORY", "#toDelimited");
        if ( with_id ){
            return Integer.toString(quantity) + ":" + type + ":" + Integer.toString(starter_water) + ":" + Integer.toString(starter_flour);
        }

        else{
            return toDelimited();
        }
    }

    public void save() {

        Set<String> stored_history = settings.getStringSet("history", null);

        if (stored_history == null) {
            stored_history = new HashSet<String>();
        }

        id = stored_history.isEmpty() ? 1 : stored_history.size();
        Log.d("HISTORY#save", "is " + toDelimited(true) + " found in " + History.fetch(context) + " / answer is " + History.contains(this));

        if (!History.contains(this)){
            Log.d("HISTORY#save", "NEW history // Not Found: " + toDelimited());

            stored_history.add(toDelimited());
            editor.putStringSet("history", stored_history);
            editor.commit();
        }

        Log.d("HISTORY#save" , "saved. its now " + History.fetch(context));
    }

    public static void replaceLast(History history){
        SharedPreferences temp_settings = history.context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor my_editor = temp_settings.edit();

        Set<String> stored_history = temp_settings.getStringSet("history", null);
        ArrayList<History> all = History.fetch((history.context));

        Log.d("HISTORY#replace", "Stored list is: " + stored_history);
        Log.d("HISTORY#replace", "History list is: " + all);

        History last = all.get(0);
        Log.d("HISTORY#replace", "Removing last entered: " + last.toDelimited());
        stored_history.remove(last.toDelimited());
        my_editor.putStringSet("history", stored_history);
        my_editor.commit();

        Log.d("HISTORY", "list is now : " + stored_history);


        history.save();

    }

    public static void clear(Context _context){
        SharedPreferences temp_settings = _context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor my_editor = temp_settings.edit();
        my_editor.clear();
        my_editor.commit();
    }

    public static ArrayList<History> fetch(Context _context){
        ArrayList<History> all = new ArrayList<History>();
        SharedPreferences temp_settings = _context.getSharedPreferences(PREFS_NAME, 0);

        Set<String> stored_history = temp_settings.getStringSet("history", null);

        if (stored_history != null) {

            for (String temp : stored_history) {
                String attrs[] = temp.split(":");
                // Log.d("HIST ARGS", "quant: " + attrs[1]);
                // Log.d("HIST ARGS", "type: " + attrs[2]);
                History history = new History(Integer.parseInt(attrs[1]), attrs[2], Integer.parseInt(attrs[3]), Integer.parseInt(attrs[4]), _context);
                history.id = Integer.parseInt(attrs[0]);
                all.add(history);
            }
        }

        Collections.sort(all, new Comparator<History>() {
            public int compare(History h1, History h2) {
                return h2.id - h1.id;
            }
        });

        return all;

    }
}
