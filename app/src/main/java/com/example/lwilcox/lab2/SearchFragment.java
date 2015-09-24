package com.example.lwilcox.lab2;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.renderscript.ScriptGroup;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Array;
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
    public ArrayList<String> displayImages;

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
                        displayImages = displayList;
                        setImage(displayImages);
                    }
                });
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (photoIndex < (displayImages.size() - 1)) {
                        photoIndex += 1;
                    }
                    else {
                        photoIndex = 0;
                    }
                    setImage(displayImages);
                } catch (Exception e) {
                    Context context = getActivity().getApplicationContext();
                    CharSequence text = "Please search for an image first.";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (photoIndex > 0) {
                        photoIndex -= 1;
                    }
                    else {
                        photoIndex = (displayImages.size() - 1);
                    }
                    setImage(displayImages);
                } catch (Exception e) {
                    Context context = getActivity().getApplicationContext();
                    CharSequence text = "Please search for an image first.";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
        return myFragmentView;
    }

    public void setImage(ArrayList<String> displayList){
        String thumbnailLink = displayList.get(photoIndex);
        new ImageLoadTask(thumbnailLink, imageView).execute();
    }
}

