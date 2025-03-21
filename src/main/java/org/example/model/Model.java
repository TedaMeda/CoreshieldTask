package org.example.model;

public class Model {
    public static final String ID = "id";
    public static final String LATITUDE= "latitude";
    public static final String LONGITUDE= "longitude";
    public static final String TYPE= "type";
    public static final String RATING= "rating";
    public static final String REVIEWS = "reviews";

    private String id;
    private Double latitude;
    private Double longitude;
    private String type;
    private Double rating;
    private Integer reviews;

    public Model() {
    }

    public Model(String id, Double latitude, Double longitude, Double rating, Integer reviews, String type) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
        this.reviews = reviews;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getReviews() {
        return reviews;
    }

    public void setReviews(Integer reviews) {
        this.reviews = reviews;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id='" + id + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", type='" + type + '\'' +
                ", rating=" + rating +
                ", reviews=" + reviews +
                '}';
    }
}
