package ru.emfataliev.request;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import ru.emfataliev.CroppedHttpHeaders;

@RequiredArgsConstructor
class RequestHeaders {

    private final ContentCachingRequestWrapper request;

    HttpHeaders values() {
        Map<String, List<String>> headersWithValues = Optional.ofNullable(request)
                .map(HttpServletRequest::getHeaderNames)
                .map(Collections::list)
                .map(HashSet::new)
                .orElseGet(HashSet::new)
                .stream()
                .filter(__ -> request != null)
                .map(headerName -> new SimpleImmutableEntry<>(headerName, Collections.list(request.getHeaders(headerName))))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        return new CroppedHttpHeaders(CollectionUtils.toMultiValueMap(headersWithValues));
    }
}
