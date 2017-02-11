package promo.kit.mycinema;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import promo.kit.mycinema.adapter.MovieAdapter;
import promo.kit.mycinema.db.DbManager;
import promo.kit.mycinema.model.Movie;
import promo.kit.mycinema.network.NetData;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Context context;
    private RecyclerView rv;
    private List<Movie> movies;
    private GridLayoutManager  vertikalLayout;
    private MovieAdapter adapter;
    private LinearLayoutManager  horizontLayout;

    DbManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        context = getApplicationContext();
        movies = new ArrayList<>();

        vertikalLayout = new GridLayoutManager(this, 2);
        horizontLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rv = (RecyclerView) findViewById(R.id.recyclerView);
        rv.setLayoutManager(vertikalLayout);

        adapter = new MovieAdapter(context, movies);
        rv.setAdapter(adapter);

        db = new DbManager(context);
        if (!isNetworkAvailable(context)) {
            List<Movie> dbMovies = db.getAll();
            if (dbMovies != null) {
                movies.addAll(dbMovies);
                adapter.notifyDataSetChanged();
            }
        } else

        new NetDateTask().execute();
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rv.setLayoutManager(horizontLayout);
        }else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            rv.setLayoutManager(vertikalLayout);
        }
    }


    class NetDateTask extends AsyncTask<Void, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(Void... params) {
            return new NetData().fetchItems();
        }

        @Override
        protected void onPostExecute(List<Movie> movieMovies) {
            super.onPostExecute(movieMovies);

            db.saveAll(movieMovies);
            movies.addAll(movieMovies);
            adapter.notifyDataSetChanged();
        }
    }
}
