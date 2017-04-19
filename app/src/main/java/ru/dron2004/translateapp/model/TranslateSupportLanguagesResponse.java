package ru.dron2004.translateapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class TranslateSupportLanguagesResponse {

    @SerializedName("dirs")
    @Expose
    public List<String> dirs = null;
    @SerializedName("langs")
    @Expose
    public HashMap<String,String> langs;

}