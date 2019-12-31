package com.github.emfataliev.response;

import com.github.emfataliev.CroppedHttpHeaders;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;

@RequiredArgsConstructor
class ResponseHeaders {

    private final HttpServletResponse response;
    private final Set<String> excludeHeaders;

    @SneakyThrows
    HttpHeaders values() {
        Map<String, List<String>> headersWithValues = Optional.ofNullable(response)
                .map(HttpServletResponse::getHeaderNames)
                .map(HashSet::new)
                .orElseGet(HashSet::new)
                .stream()
                .filter(__ -> Objects.nonNull(response))
                .map(headerName -> new SimpleImmutableEntry<>(headerName, new ArrayList<>(response.getHeaders(headerName))))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        return new CroppedHttpHeaders(headersWithValues, excludeHeaders);
    }
}
