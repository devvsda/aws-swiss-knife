package com.devsda.tools.awsswissknife.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.devsda.tools.awsswissknife.utils.ImageUtil;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RekognitionOperation {

    public AmazonRekognition createAmazonRekognitionClient(Regions region) {

        AWSCredentials credentials;
        try {
            //credentials = new ProfileCredentialsProvider("default").getCredentials();
            credentials = new BasicAWSCredentials("AKIA54ANOPPACPD7A67G", "YbRFSUudIzO63736lThuRGc/9CujQBK1YIPnHbxK");
        } catch(Exception e) {
            throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
                    + "Please make sure that your credentials file is at the correct "
                    + "location (/Users/userid/.aws/credentials), and is in a valid format.",
                    e);
        }

        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();

        return rekognitionClient;
    }

    public Map<String, Float> detectLabels(AmazonRekognition amazonRekognition, String photoName, String bucketName, Float minimumConfidence) {

        DetectLabelsRequest request = new DetectLabelsRequest()
                .withImage(new Image()
                        .withS3Object(new S3Object()
                                .withName(photoName).withBucket(bucketName)))
                .withMaxLabels(20)
                .withMinConfidence(minimumConfidence);

        Map<String, Float> relevantLabels = new HashMap<>();

        try {
            DetectLabelsResult result = amazonRekognition.detectLabels(request);
            List<Label> labels = result.getLabels();

            System.out.println("Detected labels for " + photoName);

            for (Label label: labels) {
                System.out.println(label.getName() + ": " +
                        label.getConfidence().toString());
                relevantLabels.put(label.getName(), label.getConfidence());
            }
        } catch(AmazonRekognitionException e) {
            e.printStackTrace();
        }

        return relevantLabels;
    }

    public Map<String, Float> detectLabels(AmazonRekognition amazonRekognition, String photoLocation, Float minimumConfidence) throws Exception{

        ByteBuffer byteBuffer = ImageUtil.convertToByteBuffer(photoLocation);

        DetectLabelsRequest request = new DetectLabelsRequest()
                .withImage(new Image().withBytes(byteBuffer))
                .withMaxLabels(20)
                .withMinConfidence(minimumConfidence);

        Map<String, Float> relevantLabels = new HashMap<>();

        try {
            DetectLabelsResult result = amazonRekognition.detectLabels(request);
            List<Label> labels = result.getLabels();

            System.out.println("Detected labels for " + photoLocation);

            for (Label label: labels) {
                System.out.println(label.getName() + ": " +
                        label.getConfidence().toString());
                relevantLabels.put(label.getName(), label.getConfidence());
            }
        } catch(AmazonRekognitionException e) {
            e.printStackTrace();
        }

        return relevantLabels;
    }

    public Float findOutSimilarFaces(AmazonRekognition amazonRekognition, String sourceBucketName, String sourceImageLocation, String targetBucketName, String targetImageLocation) {

        CompareFacesRequest compareFacesRequest = new CompareFacesRequest();

        compareFacesRequest.withSourceImage(new Image().
                withS3Object(
                        new S3Object()
                                .withBucket(sourceBucketName)
                                .withName(sourceImageLocation)))
                .withTargetImage(new Image()
                        .withS3Object(
                                new S3Object()
                                        .withBucket(targetBucketName)
                                        .withName(targetImageLocation)))
                .withSimilarityThreshold(90.0F);


        CompareFacesResult compareFacesResult = amazonRekognition.compareFaces(compareFacesRequest);

        List<CompareFacesMatch> compareFacesMatches = compareFacesResult.getFaceMatches();

        if(compareFacesMatches == null || compareFacesMatches.isEmpty()) {
            return null;
        }

        return compareFacesMatches.get(0).getSimilarity();
    }

    public List<FaceDetail> getEmotions(AmazonRekognition amazonRekognition, String sourceBucketName, String sourceImageLocation) {

        DetectFacesRequest detectFacesRequest = new DetectFacesRequest()
                .withImage(
                    new Image()
                        .withS3Object(
                                new S3Object()
                                        .withBucket(sourceBucketName)
                                        .withName(sourceImageLocation)
                        )

        )
                .withAttributes(Attribute.ALL);

        DetectFacesResult detectFacesResult = amazonRekognition.detectFaces(detectFacesRequest);

        List<FaceDetail> faceDetails = detectFacesResult.getFaceDetails();

        return faceDetails;
    }
}
