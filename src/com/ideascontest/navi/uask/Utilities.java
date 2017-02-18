package com.ideascontest.navi.uask;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

public class Utilities {
	private String tag;
	private boolean status;
	private ArrayList<?> res;
	
	
	public Utilities(String tagname,boolean status,ArrayList<?> resobj)
	{
		this.tag = tagname;
		this.status = status;
		this.res = resobj; 
	}
	
	/**
	 * Null check Method
     * 
     * @param txt
     * @return
     */
    public static boolean isNotNull(String txt) {
        // System.out.println("Inside isNotNull");
        return txt != null && txt.trim().length() >= 0 ? true : false;
    }
 
    /**
     * Overloaded Method to construct JSON
     * 
     * @param tag
     * @param status
     * @return
     */
    public static String constructJSON(String tag, boolean status) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", tag);
            obj.put("status", new Boolean(status));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }

    /**
     * Overloaded Method to construct JSON
     * 
     * @param arraylist
     * @return
     */
    public static String constructJSON(String tag, boolean status,ArrayList<?> list) {
    	String jsonString = null;
    	try {
    		
    		Utilities uobj = new Utilities(tag, status, list );
        	jsonString = new Gson().toJson(uobj);
        	System.out.println(jsonString);
             
    	} catch (Exception e) {
            // TODO Auto-generated catch block
        }
        return jsonString;
    }
    /**
     * Overloaded Method to construct JSON
     * 
     * @param arraylist
     * @return
     */
    public static String constructJSON(ArrayList<?> list) {
    	String jsonString = null;
    	try {	
    		//Utilities uobj = new Utilities("login", true, list );
        	jsonString = new Gson().toJson(list);
        	System.out.println(jsonString);
             
    	} catch (Exception e) {
            // TODO Auto-generated catch block
        }
        return jsonString;
    }

    /**
     * Method to construct JSON with Error Msg
     * 
     * @param tag
     * @param status
     * @param err_msg
     * @return
     */
    public static String constructJSON(String tag, boolean status,String err_msg) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", tag);
            obj.put("status", new Boolean(status));
            obj.put("error_msg", err_msg);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }
}

