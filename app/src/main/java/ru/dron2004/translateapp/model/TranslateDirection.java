package ru.dron2004.translateapp.model;

public class TranslateDirection {
    public Language from;
    public Language to;

    public TranslateDirection(Language from, Language to){
        this.from = from;
        this.to = to;
    }

    public java.lang.String toString(){
        return from.ident+"-"+to.ident;
    }
}
