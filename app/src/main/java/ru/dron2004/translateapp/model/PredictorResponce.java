package ru.dron2004.translateapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PredictorResponce {
    @SerializedName("endOfWord")
    @Expose
    public Boolean endOfWord;
    @SerializedName("pos")
    @Expose
    public Integer pos;
    @SerializedName("text")
    @Expose
    public List<String> text = null;
}
