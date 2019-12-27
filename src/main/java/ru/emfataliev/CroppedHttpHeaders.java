package ru.emfataliev;

import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

/**
 * @author e.fataliev
 * @since 27.11.2019
 */
public class CroppedHttpHeaders extends HttpHeaders {

    public CroppedHttpHeaders(MultiValueMap<String, String> headers) {
        super(headers);
        headers.entrySet().removeIf(headerWithValues -> {
            String header = headerWithValues.getKey();
            return header.equalsIgnoreCase(AUTHORIZATION) || header.equalsIgnoreCase(COOKIE);
        });
    }
}
