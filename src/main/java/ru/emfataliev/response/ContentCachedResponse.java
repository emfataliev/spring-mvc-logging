package ru.emfataliev.response;

import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.util.ContentCachingResponseWrapper;

/**
 * @author e.fataliev
 * @since 25.11.2019
 */
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
