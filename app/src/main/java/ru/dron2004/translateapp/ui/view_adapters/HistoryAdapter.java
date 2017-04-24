package ru.dron2004.translateapp.ui.view_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.dron2004.translateapp.R;
import ru.dron2004.translateapp.model.Translation;
import ru.dron2004.translateapp.ui.presenters.FavoriteFragmentPresenter;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
    private List<Translation> dataset;
    public FavoriteFragmentPresenter presenter;


    public HistoryAdapter(List<Translation> historyList, FavoriteFragmentPresenter presenter){
        dataset = historyList;
        this.presenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v,this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.setTranslation(dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    /**
     * Изменяет весь список в Recycle
     * @param initList
     */
    public void setInitalList(List<Translation> initList) {
        this.dataset = initList;
        notifyDataSetChanged();
    }

    public void itemRemove(int position){
        dataset.remove(position);
        notifyItemRemoved(position);
    }


    //Проброска события клика для инвертирования избранности
    private void toggleFavorite(int adapterPosition) {
//        Log.d("happy","Adapter Position:"+adapterPosition);
        presenter.toggleHistoryFavorite(dataset.get(adapterPosition));
        //Перегружаем все -
        notifyItemChanged(adapterPosition);
//        notifyItemMoved(adapterPosition,0);
        notifyDataSetChanged();
    }

    //Проброска события клика для отображения перевода во всей красе
    private void showTranslation(int adapterPosition) {
        presenter.showTranslation(dataset.get(adapterPosition));
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View historyItem;
        public TextView textSrc,textDst,translateDirection;
        public ImageView favoriteIcon;
        public Translation translation;

        public ViewHolder(View v, final HistoryAdapter adapter) {
            super(v);
            textDst = (TextView) v.findViewById(R.id.textDst);
            textSrc = (TextView) v.findViewById(R.id.textSrc);
            translateDirection = (TextView) v.findViewById(R.id.translateDirection);
            favoriteIcon = (ImageView) v.findViewById(R.id.iconFavorite);
            favoriteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    adapter.toggleFavorite(getAdapterPosition());
                }
                }
            });
            historyItem = (View) v.findViewById(R.id.historyItem);
            historyItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        adapter.showTranslation(getAdapterPosition());
                    }
                }
            });
        }

        public void setTranslation(Translation translation) {
            this.translation = translation;
            textSrc.setText(translation.textToTranslate);
            textDst.setText(translation.translatedText);
            translateDirection.setText(translation.translateDirection.toString());
            if (translation.isFavorite()) {
                favoriteIcon.setImageResource(R.drawable.ic_favorite_border_red_24dp);
            } else {
                favoriteIcon.setImageResource(R.drawable.ic_favorite_border_gray_24dp);
            }

        }

        public Translation getTranslation(){
            return translation;
        }
    }


}
