package net.arpcentral.pizzadoh.models;

import android.content.ClipData;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by jarp on 6/27/16.
 */
public class ExternalResource {

    JSONObject json;

    public ExternalResource(String json_string){

        try {
            json = new JSONObject(json_string);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> resource_keys(){
        ArrayList<String> keys = new ArrayList<String>();
        Iterator resource_keys = json.keys();
        Log.d("EXRES", "getting list of keys. there should be " + json.keys().toString());
        while (resource_keys.hasNext() ){
            keys.add(resource_keys.next().toString());
        }
        return keys;
    }

    public ArrayList<Item> getAll(){

        ArrayList<Item> items =  new ArrayList<Item>();
        try{

        for( String key:resource_keys()){
            Log.d("XRES", "getting all items for key of " + key);
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
        String caption;
        String url;
        String img;
        String action;
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
                action = json.getString("action");
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

        public String getUrl(){return url;}
        public String getAction(){return action;}

        public String getImg(){
            return img;
        }
        public String getCategory(){
            return category;
        }
    }
}
