package info.pauek.moviesearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MovieActivity extends AppCompatActivity {

    private Movie movie;
    private Gson gson;
    private TextView titleview;
    private TextView yearview;
    private TextView ratedview;
    private TextView runtimeview;
    private TextView genreview;
    private TextView directorview;
    private TextView actorsview;
    private TextView plotview;
    private TextView writerview;
    private ImageView posterview;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        gson = new Gson();
        queue = Volley.newRequestQueue(this);

        try {
            InputStream stream = getAssets().open("lord.json");
            InputStreamReader reader = new InputStreamReader(stream);
            movie = gson.fromJson(reader, Movie.class);
        } catch (IOException e) {
            Toast.makeText(this,"No se puedo leer json", Toast.LENGTH_LONG).show();
        }

        titleview = findViewById(R.id.titleview);
        yearview = findViewById(R.id.yearview);
        ratedview = findViewById(R.id.ratedview);
        runtimeview = findViewById(R.id.runtimeview);
        genreview = findViewById(R.id.genreview);
        directorview = findViewById(R.id.directorview);
        actorsview = findViewById(R.id.actorsview);
        plotview = findViewById(R.id.plotview);
        writerview = findViewById(R.id.writerview);
        posterview = findViewById(R.id.posterview);

        StringRequest req = new StringRequest(Request.Method.GET,
                "http://www.omdbapi.com/?i=tt3896198&apikey=62e78dc5",
                new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    movie = gson.fromJson(response, Movie.class);
                    updateMovie();
                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    Toast.makeText(MovieActivity.this,"Error de xarxa",Toast.LENGTH_LONG).show(); }
        });

        queue.add(req);


        Glide.with(this).load("file:///android_asset/lord.png").into(posterview);

    }

    private void updateMovie() {

        titleview.setText(movie.getTitle());
        yearview.setText(movie.getYear());
        ratedview.setText(movie.getRated());
        runtimeview.setText(movie.getRuntime());
        genreview.setText(movie.getGenre());
        directorview.setText(movie.getDirector());
        writerview.setText(movie.getWriter());
        actorsview.setText(movie.getActors());
        plotview.setText(movie.getPlot().replace(", ", " \n"));

    }
}
