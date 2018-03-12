package com.devsda.tools.awsswissknife.services;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PollyOperationTest {

    private static PollyOperation pollyOperation;

    @BeforeClass
    public static void setUp() {
        pollyOperation = new PollyOperation();
    }

    @AfterClass
    public static void tearDown() {

    }

    @Test
    public void convertToSpeechTest() {
        pollyOperation.convertToSpeech("/Users/hijhamb/testFile.mp3", "Hi, I am Hitesh. Currently, I am working on project Ehsaas.");
    }
}
