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

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment {

    public SearchFragment() {
    }

    public Button searchButton;
    public  Button nextButton;
    public Button previousButton;
    private View myFragmentView;
    public EditText editText;
    public ImageView imageView;
    public Integer photoIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_search, container, false);

        searchButton = (Button) myFragmentView.findViewById(R.id.search);
        nextButton = (Button) myFragmentView.findViewById(R.id.next);
        previousButton = (Button) myFragmentView.findViewById(R.id.previous);
        editText = (EditText) myFragmentView.findViewById(R.id.editText);
        imageView = (ImageView) myFragmentView.findViewById(R.id.imageView);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = editText.getText().toString();
                HttpHandler handler = new HttpHandler(getActivity().getApplicationContext());
                photoIndex = 0;
                handler.imageSearch(searchText, new Callback() {
                    @Override
                    public void callback(ArrayList<String> displayList) {
                        String thumbnailLink = displayList.get(photoIndex);
                        setImage(thumbnailLink);
                    }
                });
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoIndex += 1;
            }
        });
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoIndex -= 1;
            }
        });
        return myFragmentView;
    }

    public void setImage(String thumbnailLink){
        try {
            URL url = new URL(thumbnailLink);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input;
            input = connection.getInputStream();
            Bitmap bmp = BitmapFactory.decodeStream(input);
            imageView.setImageBitmap(bmp);
        } catch (Exception e) {
            Log.d("Error", "String not converting to URL");
        }
    }
}

