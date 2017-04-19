package ru.dron2004.translateapp.ui.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ru.dron2004.translateapp.R;
import ru.dron2004.translateapp.interactors.FavoriteFragmentInteactorImpl;
import ru.dron2004.translateapp.model.Translation;
import ru.dron2004.translateapp.ui._BaseFragment;
import ru.dron2004.translateapp.ui.presenters.FavoriteFragmentPresenter;
import ru.dron2004.translateapp.ui.presenters.FavoriteFragmentPresenterImpl;
import ru.dron2004.translateapp.ui.presenters.PresenterFactory;
import ru.dron2004.translateapp.ui.view_adapters.HistoryAdapter;
import ru.dron2004.translateapp.utility.LocaleUtils;


public class FavoriteFragment
        extends _BaseFragment<FavoriteFragmentPresenter>
        implements FavoriteFragmentView {

    private RecyclerView historyView;
    private HistoryAdapter adapter;
    private TextView historyEmptyView;

    public FavoriteFragment(){
        //Устанавливаем собиратель презентера
        setPresenterFactory(new PresenterFactory<FavoriteFragmentPresenter>() {
            @NonNull
            @Override
            public FavoriteFragmentPresenter createPresenter() {
                return new FavoriteFragmentPresenterImpl(new FavoriteFragmentInteactorImpl());
            }
        });
    }



    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            historyEmptyView = (TextView) getActivity().findViewById(R.id.historyEmptyView);
            historyView = (RecyclerView) getActivity().findViewById(R.id.historyView);
            historyView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
            historyView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

            HistoryTouchCallback callback = new HistoryTouchCallback();
            ItemTouchHelper helper = new ItemTouchHelper(callback);
            helper.attachToRecyclerView(historyView);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //Уйти за историей в БД
        getPresenter().onStart();

    }

    @Override
    public void showError(String message) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setHistory(List<Translation> translationList) {
        historyEmptyView.setVisibility(View.GONE);
        historyView.setVisibility(View.VISIBLE);
        adapter = new HistoryAdapter(translationList,getPresenter());
        historyView.setAdapter(adapter);
    }


    @Override
    public void showTranslateFragment(Translation translation) {
        if (getActivity() != null){
            ((MainActivityView)getActivity()).showTranslateFragment();
        }
    }

    @Override
    public void setEmptyHistory() {
        historyEmptyView.setVisibility(View.VISIBLE);
        historyView.setVisibility(View.GONE);
        historyEmptyView.setText(LocaleUtils.getLocaleStringResource(R.string.no_history_elements));
    }

    private class HistoryTouchCallback extends ItemTouchHelper.SimpleCallback {

        public HistoryTouchCallback() {
            super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            HistoryAdapter.ViewHolder myHolder = (HistoryAdapter.ViewHolder) viewHolder;

            switch (direction){
                case ItemTouchHelper.LEFT:
                    adapter.itemRemove(viewHolder.getAdapterPosition());
                    getPresenter().historyItemSwipeLeft(myHolder.getTranslation());
                    break;
                case ItemTouchHelper.RIGHT:
                    adapter.itemRemove(viewHolder.getAdapterPosition());
                    getPresenter().historyItemSwipeRight(myHolder.getTranslation());
                    break;
                default:
                    break;
            }
        }
    }
}
