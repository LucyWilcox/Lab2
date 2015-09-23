package com.example.lwilcox.lab2;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.HttpAuthHandler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.net.URL;
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
    public ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_search, container, false);

        searchButton = (Button) myFragmentView.findViewById(R.id.search);
        editText = (EditText) myFragmentView.findViewById(R.id.editText);
        imageView = (ImageView) myFragmentView.findViewById(R.id.imageView);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = editText.getText().toString();
                HttpHandler handler = new HttpHandler(getActivity().getApplicationContext());
                handler.imageSearch(searchText, new Callback() {
                    @Override
                    public void callback(ArrayList<String> displayList) {
                        String thumbnailLink = displayList.get(0);
                        try {
                            URL url = new URL(thumbnailLink);
                            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            imageView.setImageBitmap(bmp);
                        } catch (Exception e)
                        {
                            Log.d("Error", "String not converting to URL");
                        }
                    }
                });

            }
        });

        return myFragmentView;
    }
}

