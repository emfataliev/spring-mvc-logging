package ru.emfataliev.response;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import ru.emfataliev.CroppedHttpHeaders;

@RequiredArgsConstructor
class ResponseHeaders {

    private final HttpServletResponse response;

    @SneakyThrows
    HttpHeaders values() {
        Map<String, List<String>> headersWithValues = Optional.ofNullable(response)
                .map(HttpServletResponse::getHeaderNames)
                .map(HashSet::new)
                .orElseGet(HashSet::new)
                .stream()
                .filter(__ -> response != null)
                .map(headerName -> new SimpleImmutableEntry<>(headerName, new ArrayList<>(response.getHeaders(headerName))))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        return new CroppedHttpHeaders(CollectionUtils.toMultiValueMap(headersWithValues));
    }
}
