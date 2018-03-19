package com.devsda.tools.awsswissknife.services;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

public class RekognitionOperationTest {

    private static AmazonRekognition amazonRekognition;
    private static RekognitionOperation rekognitionOperation;

    @BeforeClass
    public static void setUp() {
        rekognitionOperation = new RekognitionOperation();
        amazonRekognition = rekognitionOperation.createAmazonRekognitionClient(Regions.US_EAST_1);
    }

    @AfterClass
    public static void tearDown() {

    }

    @Test
    public void clientTest() {
        Assert.assertNotNull(amazonRekognition);
    }

    @Test
    public void detectLablesTestWithS3() {

        Map<String, Float> lablelsConfidenceMap = rekognitionOperation.detectLabels(
                amazonRekognition, "mdl_party.jpg"/*"android_app.png"*/, "hitesh-dev-testing", 40F);

        System.out.println(lablelsConfidenceMap);

    }

    @Test
    public void detectLablesTestWithBytes() throws Exception {

        Map<String, Float> lablelsConfidenceMap = rekognitionOperation.detectLabels(
                amazonRekognition, /*"/Users/hijhamb/Downloads/mdl_party.jpg"*/"/Users/hijhamb/Desktop/android_app.png", 40F);

        System.out.println(lablelsConfidenceMap);

    }

}
