package net.arpcentral.pizzadoh.models;

import net.arpcentral.pizzadoh.models.ExternalResource;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExternalResourceTest {
    @Test
    public void get_resources_by_key() throws Exception {
        String raw_json = getRawJSON();
        ExternalResource xjson = new ExternalResource(raw_json);

        //assertEquals("", xjson.getByKey("Equipment"));
    }






    private String getRawJSON(){
        String raw_json = "{\n" +
                "  \"Equipment\" :\n" +
                "  [\n" +
                "    {\"name\" : \"Kitchen Scale\", \"caption\" : \"foo bar baz\", \"url\": \"https://www.amazon.com/Ozeri-Professional-Digital-Tempered-18-Pound/dp/B006N0OIIG/ref=sr_1_3?s=home-garden&ie=UTF8&qid=1467032390&sr=1-3-spons&keywords=kitchen+scale&psc=1\", \"img\": \"https://images-na.ssl-images-amazon.com/images/I/717c8e9N2PL._SL1500_.jpg\"}\n" +
                "  ],\n" +
                "\n" +
                "  \"Ingredients\":\n" +
                "  [\n" +
                "    {\"name\" : \"Antimo Caputo Chef's 00 Flour\", \"caption\" : \"foo bar baz\", \"url\": \"https://www.amazon.com/Antimo-Caputo-Chefs-00-Flour/dp/B001TZJ3VC?ie=UTF8&*Version*=1&*entries*=0\", \"img\": \"https://images-na.ssl-images-amazon.com/images/I/71yrqGr8fBL._SL1500_.jpg\"},\n" +
                "    {\"name\" : \"King Arthur Flour\", \"caption\" : \"foo bar baz\", \"url\": \"https://www.amazon.com/King-Arthur-Flour-Unbleached-Bread/dp/B00P6EQW6G/ref=sr_1_2_s_it?ie=UTF8&qid=1467032737&sr=1-2&keywords=king+arthur+flour\", \"img\": \"https://images-na.ssl-images-amazon.com/images/I/71b7oSQDP0L._SL1300_.jpg\"}\n" +
                "  ]\n" +
                "}";

        return raw_json;
    }
}