package com.github.slamdev.microci.integration;

import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

public final class HeaderUtils {

    private HeaderUtils() {
        // Utility class
    }

    public static void setHeaders(HttpServletResponse response, HttpHeaders headers) {
        headers.forEach((name, values) -> values.forEach(value -> response.setHeader(name, value)));
    }

    public static HttpHeaders attachment(String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        return headers;
    }
}
