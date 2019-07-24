package com.devsda.tools.awsswissknife.services;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.s3.AmazonS3;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class RekognitionOperationTest {

    private static AmazonRekognition amazonRekognition;
    private static RekognitionOperation rekognitionOperation;

    private static S3Operation s3Operation;
    private static AmazonS3 amazonS3;

    @BeforeClass
    public static void setUp() {
        rekognitionOperation = new RekognitionOperation();
        amazonRekognition = rekognitionOperation.createAmazonRekognitionClient(Regions.US_EAST_1);

        s3Operation = new S3Operation();
        amazonS3 = s3Operation.createAmazonS3Client(Regions.US_EAST_1);
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

    @Test
    public void findAllFacesInFraneTest() {

        List<String> objects = s3Operation.listObjects(amazonS3, "ehsaas-directory");

        for(String employeeName : objects) {
            Float isPresent = rekognitionOperation.findOutSimilarFaces(amazonRekognition,
                    "ehsaas-directory",
                    employeeName,
                    "ehsaas-internals",
                "mdl_party.jpg" /*"mohit_party.jpg"*/);

            if(isPresent == null) {
                System.out.println(String.format("Employee -> %s not present.", employeeName));
            } else {
                System.out.println(String.format("Employee -> %s present with similarity : %s.", employeeName, isPresent));
            }
        }

    }

}
