package com.example.lwilcox.lab2;

import android.content.ContentValues;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.HttpAuthHandler;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment {

    public SearchFragment() {
    }

    public Button searchButton;
    private View myFragmentView;
    public EditText editText;
    private HttpHandler httpHandler = new HttpHandler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_search, container, false);

        searchButton = (Button) getActivity().findViewById(R.id.search);
        editText = (EditText) getActivity().findViewById(R.id.editText);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = editText.getText().toString();
                HttpHandler handler = new HttpHandler(MainActivity.this);
                handler.imageSearch(searchText);
                     @Override
                     public void callback(ArrayList<String> )
            }
        });

        return myFragmentView;
    }

    public void searchForImage(String search) {

        RequestQueue queue = Volley.newRequestQueue(this);

        // setup the request data
        String URL = "https://www.googleapis.com/customsearch/v1?cx=015805936300530222953:xfn9wvqvajy&key=AIzaSyBHSXnNE-tEICkyVFO_dgktm1sLbmXxwPw&";
        search = search.replaceAll(" ","+");
        URL = URL+"q="+search+"&searchType=image";

               JSONObject body = new JSONObject();
        try {
            body.put("random", "thing"); // unnecessary, but I wanted to show you how to include body data
        } catch (Exception e) {
            Log.e("JSONException", e.getMessage());
        }

        JsonObjectRequest getRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do something with response
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.getMessage());
                    }
                }
        );

        queue.add(getRequest);
    }

}

