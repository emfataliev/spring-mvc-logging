package ru.emfataliev.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import ru.emfataliev.RepresentableAsString;

@RequiredArgsConstructor
class URI implements RepresentableAsString {

    private final ContentCachingRequestWrapper request;

    @Override
    public String asString() {
        String queryString = request.getQueryString();
        if (queryString != null) {
            return request.getRequestURI() + "?" + queryString;
        }
        return request.getRequestURI();
    }
}
