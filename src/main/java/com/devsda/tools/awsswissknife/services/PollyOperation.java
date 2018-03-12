package com.devsda.tools.awsswissknife.services;

import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.AmazonPollyClientBuilder;
import com.amazonaws.services.polly.model.*;
import com.devsda.tools.awsswissknife.exceptions.PollyInternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class PollyOperation {

    private static final Logger log = LoggerFactory.getLogger(PollyOperation.class);

    private AmazonPolly createPollyClient() {
        AmazonPolly client = AmazonPollyClientBuilder.defaultClient();
        return client;
    }

    public void convertToSpeech(String locationToStoreMp3File, String textToReadOut) {

        log.info(String.format("Converting text : %s into speech supported mp3 file", textToReadOut));

        AmazonPolly client = createPollyClient();

        SynthesizeSpeechRequest synthesizeSpeechRequest = new SynthesizeSpeechRequest()
                .withOutputFormat(OutputFormat.Mp3)
                .withVoiceId(VoiceId.Matthew)
                .withText(textToReadOut);

        try (FileOutputStream outputStream = new FileOutputStream(new
                File(locationToStoreMp3File))) {

            SynthesizeSpeechResult synthesizeSpeechResult =
                    client.synthesizeSpeech(synthesizeSpeechRequest);

            byte[] buffer = new byte[2 * 1024];
            int readBytes;

            try (InputStream in = synthesizeSpeechResult.getAudioStream()) {

                log.info(String.format("Saving speech file into : %s", locationToStoreMp3File));

                while ((readBytes = in.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, readBytes);
                }
            }

            log.debug(String.format("Successfully stored file into : %s for text : %s", locationToStoreMp3File, textToReadOut));

        } catch (Exception e) {

            log.error( String.format("Failed while processing text : %s", textToReadOut), e );
            throw new PollyInternalException( String.format("Failed while processing text : %s", textToReadOut), e );

        }
    }
}
