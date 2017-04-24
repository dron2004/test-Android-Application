package ru.dron2004.translateapp.model;

import java.io.Serializable;

public class Language implements Serializable {
    public String ident;
    public String title;

    public Language(String id, String name) {
        this.ident = id;
        this.title = name;
    }
    public Language(String id) {
        this.ident = id;
    }

    //Сравниваем только по идентификатору
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Language language = (Language) o;

        return ident.equals(language.ident);

    }

    @Override
    public int hashCode() {
        return ident.hashCode();
    }

    @Override
    public String toString(){
        return title;
    }
}
