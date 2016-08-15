package net.arpcentral.pizzadoh.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by jarp on 6/27/16.
 */
public class Step {

    JSONObject json;

    public Step(String json_string){

        try {
            json = new JSONObject(json_string);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> step_keys(){
        ArrayList<String> keys = new ArrayList<String>();
        Iterator step_keys = json.keys();
        while (step_keys.hasNext() ){
            keys.add(step_keys.next().toString());
        }
        return keys;
    }

    public ArrayList<Item> getAll(){

        ArrayList<Item> items =  new ArrayList<Item>();
        try{

        for( String key:step_keys()){

            JSONArray json_items = json.getJSONArray(key);
            for (int i = 0 ; i < json_items.length(); i++) {
                JSONObject this_item = json_items.getJSONObject(i);
                items.add(new Item(this_item, key));
            }
        }

    } catch (Exception e) {
        e.printStackTrace();

    }
        return items;
    }

    public ArrayList<Item> getByKey(String key){
        try {
            String raw_json = json.getString(key);

            JSONArray json_items = json.getJSONArray(key); //new JSONArray(raw_json);

            ArrayList<Item> items =  new ArrayList<Item>();

            for (int i = 0 ; i < json_items.length(); i++) {
                JSONObject this_item = json_items.getJSONObject(i);
                items.add(new Item(this_item, key));
            }
            return items;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Item>();
        }
    }

    public class Item{
        String name;
        String directions;
        String img;
        String category;
        String url;

//        public Item(String _name, String _directions, String _url, String _img){
//            name = _name;
//            directions = _directions;
//            url = _url;
//            img = _img;
//        }

        public Item(JSONObject json, String _category){
            try{
                name = json.getString("name");
                directions = json.getString("directions");
                img = json.getString("img");
                url = json.getString("url");
                category = _category;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        public String toString(){
            return name;
        }

        public String getName(){
            return name;
        }

        public String getUrl(){
            return url;
        }

        public String getDirections(){
            return directions;
        }

        public String getImg(){
            return img;
        }
        public String getCategory(){
            return category;
        }
    }
}
