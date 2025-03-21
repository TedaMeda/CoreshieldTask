package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.file.FileReader;
import org.example.model.Model;
import org.example.support.ObjectModelMapper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Controller {
    private final Logger logger = LogManager.getLogger(Controller.class);

    private final Map<String, Model> db;
    private final Map<String, Model> invalidDb;

    public Controller() {
        this.db = new HashMap<>();
        this.invalidDb = new HashMap<>();
    }

    public void start() {
        //read files
        List<Map<String, Object>> jsonData = FileReader.readFile("src/main/resources/file1.json");
        List<Map<String, Object>> jsonData2 = FileReader.readFile("src/main/resources/file2.json");

        //add it to db
        for (Map<String, Object> mp : jsonData) {
            Model dataEntry = ObjectModelMapper.mapper(mp);
            save(dataEntry);
        }

        for (Map<String, Object> mp : jsonData2) {
            Model dataEntry = ObjectModelMapper.mapper(mp);
            save(dataEntry);
        }

        smartValidityChecker();

        //analysis
        System.out.println("Number of valid points exist per type: ");
        validPointsPerType();

        System.out.println("Average rating per type");
        avgRatingPerType();

        System.out.println("Location with highest reviews");
        getLocationWithHighestReview();

        System.out.println("Invalid locations");
        printInvalidEntry();

    }

    /*Criteria for validity
    1. ID must be present and not blank
    2. Every latitude is between -90 and +90
    3. Every longitude is between -180 and +180
    4. Type must be present and not blank
    * */
    private void smartValidityChecker() {
        Iterator<String> iterator = db.keySet().iterator();
        while(iterator.hasNext()){
            String key = iterator.next();
            if (!isValid(db.get(key))) {
                invalidDb.put(key, db.get(key));
                db.remove(key);
            }
        }
    }

    private boolean isValid(Model model) {
        if (model.getLatitude() == null || model.getLongitude() == null) return false;
        if (model.getLatitude() < -90 || model.getLatitude() > 90) return false;
        if (model.getLongitude() < -180 || model.getLongitude() > 180) return false;
        if (model.getType() == null || model.getType().isBlank()) return false;
        return true;
    }

    private void printInvalidEntry() {
        for (String key : invalidDb.keySet()) {
            Model data = invalidDb.get(key);
            System.out.println(data.getId());
        }
    }

    private void save(Model data) {
        if (data.getId().isBlank() || data.getId().isEmpty()) {
            logger.warn("Id is null for " + data);
            return;
        }
        if (db.containsKey(data.getId())) {
            Model dbModel = db.get(data.getId());

            //update existing data
            if (data.getLatitude() != null) {
                dbModel.setLatitude(data.getLatitude());
            }
            if (data.getLongitude() != null) {
                dbModel.setLongitude(data.getLongitude());
            }
            if (data.getType() != null) {
                dbModel.setType(data.getType());
            }
            if (data.getRating() != null) {
                dbModel.setRating(data.getRating());
            }
            if (data.getReviews() != null) {
                dbModel.setReviews(data.getReviews());
            }

            db.put(data.getId(), dbModel);
        } else {
            db.put(data.getId(), data);
        }
    }

    private void validPointsPerType() {
        Map<String, Integer> validPointsPerType = new HashMap<>();

        for (String key : db.keySet()) {
            Model data = db.get(key);
            if (validPointsPerType.containsKey(data.getType())) {
                validPointsPerType.put(data.getType(), validPointsPerType.get(data.getType()) + 1);
            } else {
                validPointsPerType.put(data.getType(), 1);
            }
        }

        validPointsPerType.forEach((k, v) -> System.out.println(k + ":: " + v));
    }


    private void avgRatingPerType() {
        Map<String, Double> sumOfRatingByType = new HashMap<>();
        Map<String, Integer> pointsPerType = new HashMap<>();

        for (String key : db.keySet()) {
            Model data = db.get(key);
            if (data.getRating() == null) {
                data.setRating(0.0);
            }
            if (sumOfRatingByType.containsKey(data.getType())) {
                sumOfRatingByType.put(data.getType(), sumOfRatingByType.get(data.getType()) + data.getRating());
                pointsPerType.put(data.getType(), pointsPerType.get(data.getType()) + 1);
            } else {
                sumOfRatingByType.put(data.getType(), data.getRating());
                pointsPerType.put(data.getType(), 1);
            }
        }

        for (String key : sumOfRatingByType.keySet()) {
            Double sum = sumOfRatingByType.get(key);
            Double cnt = Double.valueOf(pointsPerType.get(key));
            System.out.println(key + ":: " + sum / cnt);
        }
    }

    private void getLocationWithHighestReview() {
        Model res = db.get(db.keySet().iterator().next());
        for (String key : db.keySet()) {
            Model cur = db.get(key);
            if (cur.getReviews() > res.getReviews()) {
                res = cur;
            }
        }
        System.out.println(res.getId());
    }
}
