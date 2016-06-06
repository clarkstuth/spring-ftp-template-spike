package com.clarkstuth.spike.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Launcher {

    public static void main(String[] args) {
        File testFile = new File("src/test/resources/temp/some-file.txt");
        if (!testFile.exists()) {
            try {
                testFile.getParentFile().mkdirs();
                testFile.createNewFile();

                try (FileWriter writer = new FileWriter(testFile)) {
                    writer.write("File Content!");
                }
            } catch (IOException ignored) {
                System.out.println("Unable to write test file.");
                System.out.println(ignored.getMessage());
            }
        }

        FtpRemoteFileTemplate template = new AnnotationConfigApplicationContext(AppConfig.class).getBean(FtpRemoteFileTemplate.class);

        System.out.println(template.exists(testFile.getName()));
        System.out.println(template.exists("/something-else"));

        final Message<File> message = MessageBuilder.withPayload(testFile).build();

        template.send(message, FileExistsMode.FAIL); // this will always fail

        template.send(message, FileExistsMode.REPLACE); // this works
    }


}
