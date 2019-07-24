package com.devsda.tools.awsswissknife.services;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class S3OperationTest {

    private static S3Operation s3Operation;
    private static AmazonS3 amazonS3;

    @BeforeClass
    public static void setup() {
        s3Operation = new S3Operation();
        amazonS3 = s3Operation.createAmazonS3Client(Regions.US_EAST_1);
    }

    @Test
    public void uploadTest() {
        s3Operation.upload(amazonS3, "/Users/hijhamb/Downloads/mdl_party.jpg", "hitesh-dev-testing", "api_testing.jpg");
    }

    @Test
    public void listObjects() {
        List<String> objects = s3Operation.listObjects(amazonS3, "ehsaas-directory");
        System.out.println(objects);
    }
}
