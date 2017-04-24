package ru.dron2004.translateapp.model;

import java.io.Serializable;

/**
 * Модель отображаемая на экране перевода
 */
public class Translation implements Serializable {
    private long _id;
    private boolean favorite = false;
    public TranslateDirection translateDirection;
    public String textToTranslate;
    public String translatedText;

    public Translation(TranslateDirection translateDirection, String textToTranslate, String translatedText) {
        this.translateDirection = translateDirection;
        this.textToTranslate = textToTranslate;
        this.translatedText = translatedText;
    }

    public void setId(long id){
        this._id = id;
    }
    public long getId(){
        return this._id;
    }

    public void setFavorite(boolean favorite){
        this.favorite = favorite;
    }

    public boolean isFavorite(){
        return favorite;
    }

}
