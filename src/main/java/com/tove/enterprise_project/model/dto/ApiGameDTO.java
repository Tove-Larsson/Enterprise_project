package com.tove.enterprise_project.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;

public class ApiGameDTO {

    private Long id;
    private String name;
    private String storyline;
    private double rating;
    @JsonProperty("first_release_date")
    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    private LocalDateTime firstReleaseDate;

    public ApiGameDTO() {}

    public ApiGameDTO(Long id, String name, String storyline, double rating, LocalDateTime firstReleaseDate) {
        this.id = id;
        this.name = name;
        this.storyline = storyline;
        this.rating = rating;
        this.firstReleaseDate = firstReleaseDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStoryline() {
        return storyline;
    }

    public void setStoryline(String storyline) {
        this.storyline = storyline;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public LocalDateTime getFirstReleaseDate() {
        return firstReleaseDate;
    }

    public void setFirstReleaseDate(LocalDateTime firstReleaseDate) {
        this.firstReleaseDate = firstReleaseDate;
    }

    @Override
    public String toString() {
        return "GameApi{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", storyline='" + storyline + '\'' +
                ", rating=" + rating +
                ", firstReleaseDate=" + firstReleaseDate +
                '}';
    }
}
