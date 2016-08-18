package com.example.megha.movieplate;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Megha on 27-03-2016.
 */
public class Search implements Serializable {
    String Poster;

    @SerializedName("Title")
    String title;

    String Year;

    @SerializedName("Genre")
    String genre;

    @SerializedName("Runtime")
    String duration;

    @SerializedName("Plot")
    String plot;

    @SerializedName("Released")
    String releaseDate;

    @SerializedName("Director")
    String director;

    @SerializedName("Writer")
    String writer;

    @SerializedName("Actors")
    String actors;

    @SerializedName("Language")
    String language;

    @SerializedName("imdbRating")
    String imdbRating;

    @SerializedName("Metascore")
    String metaScore;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getMetaScore() {
        return metaScore;
    }

    public void setMetaScore(String metaScore) {
        this.metaScore = metaScore;
    }
}
