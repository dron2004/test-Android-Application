
package ru.dron2004.translateapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detected {

    @SerializedName("lang")
    @Expose
    private Language lang;

    public Language getLang() {
        return lang;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }

}
