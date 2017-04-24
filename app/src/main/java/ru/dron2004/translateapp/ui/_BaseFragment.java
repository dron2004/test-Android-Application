package ru.dron2004.translateapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.dron2004.translateapp.ui.presenters.PresenterCache;
import ru.dron2004.translateapp.ui.presenters.PresenterFactory;

public abstract class _BaseFragment<PI extends _BasePresenter> extends Fragment implements _BaseView {

    //Ссылка на кеш презентеров
    public PresenterCache presenterCache = PresenterCache.getInstance();
    //TAG для сохранения презентера в кеше (Имя класса презентера)
    public final String TAG = this.getClass().getName();
    //Флаг для индикации что фрагмент будет уничтожен
    private boolean isDestroyedBySystem;
//    //Фабрика создания презентеров
    private PresenterFactory<PI> presenterFactory;
    //Ссылка на презентер
    private PI presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //При создании фрагмента - вытаскиваем презентер из кэша, или создаем новый фабрикой
        this.presenter = this.presenterCache.getPresenter(TAG, this.presenterFactory);
//        Log.d("happy","["+TAG+"] get ["+getPresenter()+"] from Cache or Factory");
    }

    @Nullable
    @Override
    //Нужно переопределять потому как без этого не надуется layout
    public abstract View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState);

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Как только VIEW готова - подписываем презентер на нее
        getPresenter().setView(getFragment());
//        Log.d("happy","["+TAG+"] Presenter set View");
    }

    @Override
    public void onResume() {
        super.onResume();
        //Выставляем флаг что фрагмент существует
        this.isDestroyedBySystem = false;
//        Log.d("happy","["+TAG+"] Presenter flag isDestroyedBySystem now FALSE");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Если пошли сохранять State - значит скоро помрем, ставим флаг
        this.isDestroyedBySystem = true;
//        Log.d("happy","["+TAG+"] Presenter flag isDestroyedBySystem now TRUE");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //В момент уничтожения View отписываем презентер от нее
        getPresenter().unsetView();
//        Log.d("happy","["+TAG+"] Presenter unset View");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!this.isDestroyedBySystem) {
            //Если фрагмент был активен - при полноценном дестоинге надо убрать его из кэша
            //TODO подумать о переносе во onStop
            presenterCache.removePresenter(TAG);
//            Log.d("happy","["+TAG+"] Presenter["+getPresenter()+"] removed from Cache");
        }
    }

    protected _BaseFragment getFragment(){
        return this;
    }

    protected PI getPresenter(){
        return presenter;
    }

    protected void setPresenterFactory(PresenterFactory<PI> factory){
        presenterFactory = factory;
    }

    protected PresenterFactory<PI> getPresenterFactory(){
        return presenterFactory;
    }
}
