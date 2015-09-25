package com.example.lwilcox.lab2;

import android.content.Context;
import android.media.Image;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lwilcox on 9/17/2015.
 */
public class HttpHandler {
    public RequestQueue queue;
    public HttpHandler(Context context) {
        queue = Volley.newRequestQueue(context);
    }
    public void imageSearch(String searchQuery, final Callback callback) {
        String query = searchQuery.replaceAll(" ", "+");
        String URL = "https://www.googleapis.com/customsearch/v1?cx=015805936300530222953:xfn9wvqvajy&key=AIzaSyBHSXnNE-tEICkyVFO_dgktm1sLbmXxwPw&";
        URL = URL + "q=" + query + "&searchType=image";

        JsonObjectRequest getRequest = new JsonObjectRequest(
            Request.Method.GET,
            URL,
            new JSONObject(),
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response){
                    ArrayList<String> displayImages = new ArrayList<String>();
                    try {
                        JSONArray items = response.getJSONArray("items");
                        for (int i = 0; i < items.length(); i++) {
                            JSONObject item = items.getJSONObject(i);
                            JSONObject image = item.getJSONObject("image");
                            String thumbnailImage = image.getString("thumbnailLink");
                            displayImages.add(thumbnailImage);
                        }
                            callback.callback(displayImages);
                    } catch (Exception e)
                    {
                        Log.d("Failure", "Not extracting thumbnail from JSON Object");
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)  {
                    Log.e("Error", error.getMessage());
                }
            }
        );

        queue.add(getRequest);
    }
}