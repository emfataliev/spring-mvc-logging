package com.github.emfataliev.response;

import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.util.ContentCachingResponseWrapper;

@RequiredArgsConstructor
public class ContentCachedResponse {

    private final HttpServletResponse response;

    public ContentCachingResponseWrapper response() {
        if (response instanceof ContentCachingResponseWrapper) {
            return (ContentCachingResponseWrapper) response;
        }
        return new ContentCachingResponseWrapper(response);
    }
}
