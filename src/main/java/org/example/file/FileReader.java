package org.example.file;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;
import java.util.Map;

public class FileReader {

    public FileReader() {
    }

    public static List<Map<String, Object>> readFile(String path) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Map<String, Object>> jsonMap = mapper.readValue(new File(path), mapper.getTypeFactory().constructCollectionType(List.class, Map.class));
            return jsonMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
