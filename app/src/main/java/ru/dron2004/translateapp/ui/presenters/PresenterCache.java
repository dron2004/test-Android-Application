package ru.dron2004.translateapp.ui.presenters;

import android.support.v4.util.SimpleArrayMap;
import android.util.Log;

import ru.dron2004.translateapp.ui._BasePresenter;

public class PresenterCache {
    private static PresenterCache instance = null;
    private SimpleArrayMap<String, _BasePresenter> presenters;
    private PresenterCache() {}

    public static PresenterCache getInstance() {
        if (instance == null) {
            instance = new PresenterCache();
        }
        return instance;
    }

    /**
     * Returns a presenter instance that will be stored and
     * survive configuration changes
     *
     * @param who A unique tag to identify the presenter
     * @param presenterFactory A factory to create the presenter
     *        if it doesn't exist yet
     * @param <T> The presenter type
     * @return The presenter
     */
    @SuppressWarnings("unchecked") // Handled internally
    public final <T extends _BasePresenter> T getPresenter(
            String who, PresenterFactory<T> presenterFactory) {
        if (presenters == null) {
            presenters = new SimpleArrayMap<>();
        }
        T p = null;
        try {
            p = (T) presenters.get(who);
        } catch (ClassCastException e) {
            Log.w("happy", "Duplicate Presenter tag identified: " + who + ". This could cause issues with state.");
        }
        if (p == null) {
            p = presenterFactory.createPresenter();
            presenters.put(who, p);
        }
        return p;
    }

    /**
     * Remove the presenter associated with the given tag
     *
     * @param who A unique tag to identify the presenter
     */
    public final void removePresenter(String who) {
        if (presenters != null) {
            presenters.remove(who);
        }
    }
}
