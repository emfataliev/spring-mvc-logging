package com.github.emfataliev;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

public class CroppedHttpHeaders extends HttpHeaders {

    public CroppedHttpHeaders(MultiValueMap<String, String> headers, Set<String> excludeHeaders) {
        super(headers);
        headers.entrySet().removeIf(headerWithValues -> excludeHeaders.contains(headerWithValues.getKey()));
    }

    public CroppedHttpHeaders(Map<String, List<String>> headers, Set<String> excludeHeaders) {
        this(CollectionUtils.toMultiValueMap(headers), excludeHeaders);
    }
}
