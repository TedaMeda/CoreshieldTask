package org.example.support;

import org.example.model.Model;

import java.util.Map;

public class ObjectModelMapper {
    public static Model mapper(Map<String, Object> mp) {
        Model model = new Model();
        if (mp.containsKey(Model.ID)) {
            model.setId((String) mp.get(Model.ID));
        }
        if (mp.containsKey(Model.LATITUDE)) {
            model.setLatitude((Double) mp.get(Model.LATITUDE));
        }
        if (mp.containsKey(Model.LONGITUDE)) {
            model.setLongitude((Double) mp.get(Model.LONGITUDE));
        }
        if (mp.containsKey(Model.TYPE)) {
            model.setType((String) mp.get(Model.TYPE));
        }
        if (mp.containsKey(Model.RATING)) {
            model.setRating((Double) mp.get(Model.RATING));
        }
        if (mp.containsKey(Model.REVIEWS)) {
            model.setReviews((Integer) mp.get(Model.REVIEWS));
        }
        return model;
    }
}
