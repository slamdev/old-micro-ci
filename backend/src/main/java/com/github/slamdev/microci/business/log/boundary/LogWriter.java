package com.github.slamdev.microci.business.log.boundary;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class LogWriter {

    private static final String LOG_FILENAME = "build.log";

    public void write(OutputStream stream, Path path) {
        try {
            Files.createDirectories(path);
            Files.copy(path.resolve(LOG_FILENAME), stream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
