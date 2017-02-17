package promo.kit.mycinema.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import promo.kit.mycinema.interfaces.MovieDAO;

import promo.kit.mycinema.model.Movie;


public class DbManager implements MovieDAO<Movie> {

    private static final String LOG_TAG = DbManager.class.getSimpleName();
    private MovieOpenHelper dbHalper;

    public DbManager(Context context) {
        dbHalper = new MovieOpenHelper(context);
    }

    @Override
    public long save(Movie movie) throws IOException {
        SQLiteDatabase db = dbHalper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Movie.KEY_TITLE, movie.title);
        cv.put(Movie.KEY_ID, movie.id);
        cv.put(Movie.KEY_OVERVIEW, movie.overview);
        cv.put(Movie.KEY_POSTER_PATH, movie.posterPath);
        cv.put(Movie.KEY_RATE, movie.popularity);
        cv.put(Movie.KEY_RELEASE_DATE, movie.releaseDate);

        long _id = db.insert(Movie.TABLE_MOVIE, null, cv);
        db.close();
        return  _id;

    }

    @Override
    public boolean delete(Movie movie) {
        SQLiteDatabase db = dbHalper.getWritableDatabase();
        String[] whereArgs = {String.valueOf(movie.id)};
        int rows = db.delete(Movie.TABLE_MOVIE, Movie.KEY_ID,
                whereArgs);
        db.close();

        return rows > 0;
    }

    @Override
    public Movie get(int id) {
        SQLiteDatabase db = dbHalper.getWritableDatabase();
        String[] whereArgs = {String.valueOf(id)};
        Cursor c = db.query(Movie.TABLE_MOVIE,
                Movie.projection,
                Movie.KEY_ID + " = ? ",
                whereArgs,
                null,
                null,
                null);

        Movie item = null;
        while (c != null && c.moveToFirst()) {
            item = Movie.getItemFromCursor(c);
        }

        return item;
    }

    @Override
    public List<Movie> getAll() {
        SQLiteDatabase db = dbHalper.getWritableDatabase();
        Cursor c = db.query(Movie.TABLE_MOVIE,
                Movie.projection,
                null,
                null,
                null,
                null,
                null);

        if (c == null)
            return null;

        List<Movie> items = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Movie item = Movie.getItemFromCursor(c);
                items.add(item);
            } while (c.moveToNext());
        }

        c.close();

        return items;
    }

    @Override
    public void saveAll(List<Movie> movies) { SQLiteDatabase db = dbHalper.getWritableDatabase();

        for (Movie movie: movies) {
            ContentValues cv = new ContentValues();
            cv.put(Movie.KEY_TITLE, movie.title);
            cv.put(Movie.KEY_OVERVIEW, movie.overview);
            cv.put(Movie.KEY_RATE, movie.popularity);
            cv.put(Movie.KEY_POSTER_PATH, movie.posterPath);
            long id = db.insert(Movie.TABLE_MOVIE, null, cv);
            Log.d(LOG_TAG, "Inserted id=" + id);
        }

        db.close();


    }



}