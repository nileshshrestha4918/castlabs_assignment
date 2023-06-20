package com.nilesh.castlab.controller;

import com.nilesh.castlab.model.Mp4Box;
import com.nilesh.castlab.model.Mp4Response;
import com.nilesh.castlab.utils.MP4AnalyzerHelper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

@RestController
public class AnalyzerController {
    @GetMapping(value = "/analyze", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mp4Response> analyzeMp4(@RequestParam("url") String url) {
        HttpURLConnection connection = null;
        try {
            URL mp4Url = new URL(url).toURI().toURL();
            connection = (HttpURLConnection) mp4Url.openConnection();
            InputStream inputStream = connection.getInputStream();

            List<Mp4Box> mp4Boxes = new ArrayList<>();

            int boxSize = MP4AnalyzerHelper.readBoxSize(inputStream);
            String boxType = MP4AnalyzerHelper.readBoxType(inputStream);

            Mp4Box startBox = new Mp4Box(boxType, boxSize);
            mp4Boxes.add(startBox);

            MP4AnalyzerHelper.parseBoxes(inputStream, startBox, mp4Boxes);

            Mp4Response successResponse = new Mp4Response("success", mp4Boxes);
            return ResponseEntity.ok(successResponse);

        } catch (IOException | URISyntaxException exception){
            if (connection != null) {
                connection.disconnect();
            }
            Mp4Response failureResponse = new Mp4Response("error", exception.getMessage());
            return ResponseEntity.badRequest().body(failureResponse);
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}








