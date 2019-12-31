package com.github.emfataliev.request;

import static java.util.stream.Collectors.toMap;

import com.github.emfataliev.CroppedHttpHeaders;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;

@RequiredArgsConstructor
class RequestHeaders {

    private final HttpServletRequest request;
    private final Set<String> excludeHeaders;

    HttpHeaders values() {
        Map<String, List<String>> headersWithValues = Optional.ofNullable(request)
                .map(HttpServletRequest::getHeaderNames)
                .map(Collections::list)
                .map(HashSet::new)
                .orElseGet(HashSet::new)
                .stream()
                .filter(__ -> Objects.nonNull(request))
                .map(headerName -> new SimpleImmutableEntry<>(headerName, Collections.list(request.getHeaders(headerName))))
                .collect(toMap(Entry::getKey, Entry::getValue));
        return new CroppedHttpHeaders(headersWithValues, excludeHeaders);
    }
}
