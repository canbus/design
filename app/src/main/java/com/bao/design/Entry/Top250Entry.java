package com.bao.design.Entry;

import android.provider.MediaStore;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Top250Entry {
    private int count;
    private int start;
    private int total;
    private String title;
    private List<Subject> subjects;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public class Subject{
        private Rating rating;
        private List<String> genres;
        String title;
        String year;
        @SerializedName("original_title")
        String originalTitle;
        private List<Cast> casts;
        private List<Director> directors;
        private Images images;

        public Images getImages() {
            return images;
        }

        public void setImages(Images images) {
            this.images = images;
        }

        public Rating getRating() {
            return rating;
        }

        public void setRating(Rating rating) {
            this.rating = rating;
        }

        public List<String> getGenres() {
            return genres;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getOriginalTitle() {
            return originalTitle;
        }

        public void setOriginalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
        }

        public List<Cast> getCasts() {
            return casts;
        }

        public void setCasts(List<Cast> casts) {
            this.casts = casts;
        }

        public List<Director> getDirectors() {
            return directors;
        }

        public void setDirectors(List<Director> directors) {
            this.directors = directors;
        }


    }

    public class Rating {
        float max;
        float average;
        float stars;

        public float getMax() {
            return max;
        }

        public void setMax(float max) {
            this.max = max;
        }

        public float getAverage() {
            return average;
        }

        public void setAverage(float average) {
            this.average = average;
        }

        public float getStars() {
            return stars;
        }

        public void setStars(float stars) {
            this.stars = stars;
        }

        public float getMin() {
            return min;
        }

        public void setMin(float min) {
            this.min = min;
        }

        float min;

    }

    public class Cast {
        String alt;
        String name;
        Images avatars;

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Images getAvatars() {
            return avatars;
        }

        public void setAvatars(Images avatars) {
            this.avatars = avatars;
        }
    }

    public class Images {
        String small;
        String large;
        String medium;
        int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }
    }

    public class Director {
        String alt;
        Images avatars;
        String name;
        int id;

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public Images getAvatars() {
            return avatars;
        }

        public void setAvatars(Images avatars) {
            this.avatars = avatars;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }


}
