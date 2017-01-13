package promo.kit.mycinema;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Влад on 31.12.16.
 */

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmHolder> {
    private List<Movie> mMovieList;
    public FilmAdapter(List<Movie> movies) {
        mMovieList = movies;

    }

    @Override
    public FilmAdapter.FilmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new FilmHolder(view);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    @Override
    public void onBindViewHolder(FilmHolder holder, int position) {
        Movie movie = mMovieList.get(position);
        holder.bindFilm(movie);
    }
    public static OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }


    public class FilmHolder extends RecyclerView.ViewHolder {
        private ImageView mImage;
        private TextView yaerMovie;
        private TextView genreMovie;
        private Movie mMovie;

        public FilmHolder(final View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.imageView);
            yaerMovie = (TextView) itemView.findViewById(R.id.yearMovie);
            genreMovie = (TextView) itemView.findViewById(R.id.genreMovie);

        }

        public void bindFilm(Movie movie) {
            mMovie = movie;
            yaerMovie.setText(mMovie.getYear());
            genreMovie.setText(mMovie.getGanre());
            mImage.setImageBitmap(BitmapFactory.decodeResource(itemView.getResources(), mMovie.getPosterId()));
        }
    }


}
