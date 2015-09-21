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
        URL = URL + "q=" + searchQuery + "&searchType=image";

        JsonObjectRequest getRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){
                        ArrayList<String> displayImages = new ArrayList<String>();
                        try {
                            JSONObject items = response.getJSONObject("Items");
                            JSONObject image = response.getJSONObject("image");
                            for (int i = 0; i < items.length(); i++) {
                                String thumbnailImage = image.getString("thumbnailLink");
                                displayImages.add(thumbnailImage);
                            }
                                callback.callback(displayImages);
                        } catch (Exception e)
                        {
                            Log.d("Success", "whoo");
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