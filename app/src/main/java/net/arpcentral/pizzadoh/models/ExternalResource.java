package net.arpcentral.pizzadoh.models;

import android.content.ClipData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by jarp on 6/27/16.
 */
public class ExternalResource {
    final static String[] KEYS = {"Equipment", "Ingredients"};
    JSONObject json;
    public ExternalResource(String json_string){

        try {
            json = new JSONObject(json_string);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[] keys(){
        return KEYS;
    }

    public ArrayList<Item> getAll(){
        try{
        //String raw_json = json.getString("Equipment");
        ArrayList<Item> items =  new ArrayList<Item>();

        JSONArray json_items = json.getJSONArray("Equipment");
        for (int i = 0 ; i < json_items.length(); i++) {
            JSONObject this_item = json_items.getJSONObject(i);
            items.add(new Item(this_item, "Equipment"));
        }
        json_items = json.getJSONArray("Ingredients");
        for (int i = 0 ; i < json_items.length(); i++) {
            JSONObject this_item = json_items.getJSONObject(i);
            items.add(new Item(this_item, "Ingredients"));
        }

        return items;

    } catch (Exception e) {
        e.printStackTrace();
        return new ArrayList<Item>();
    }
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
        String caption;
        String url;
        String img;
        String category;

//        public Item(String _name, String _caption, String _url, String _img){
//            name = _name;
//            caption = _caption;
//            url = _url;
//            img = _img;
//        }

        public Item(JSONObject json, String _category){
            try{
                name = json.getString("name");
                caption = json.getString("caption");
                url = json.getString("url");
                img = json.getString("img");
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

        public String getCaption(){
            return caption;
        }

        public String getUrl(){

            return url;
        }

        public String getImg(){
            return img;
        }
        public String getCategory(){
            return category;
        }
    }
}
