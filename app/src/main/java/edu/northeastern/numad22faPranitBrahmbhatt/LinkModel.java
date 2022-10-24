package edu.northeastern.numad22faPranitBrahmbhatt;

import androidx.annotation.NonNull;

import java.net.URL;

public class LinkModel {
    String name = "";
    String link;


    public LinkModel(@NonNull String name, @NonNull String link) {
        this.name = name;
        this.link = link;
    }
}
