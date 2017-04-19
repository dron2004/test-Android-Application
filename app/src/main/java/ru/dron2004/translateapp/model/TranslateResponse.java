
package ru.dron2004.translateapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TranslateResponse {

    @SerializedName("code")
    @Expose
    public Integer code;
//    @SerializedName("detected")
//    @Expose
//    private Detected detected;
//    @SerializedName("lang")
//    @Expose
//    private String lang;
    @SerializedName("text")
    @Expose
    public List<String> text = null;

}
