package ru.emfataliev.request;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.util.ContentCachingRequestWrapper;

/**
 * @author e.fataliev
 * @since 25.11.2019
 */
@RequiredArgsConstructor
public class ContentCachedRequest {

    private final HttpServletRequest request;

    public ContentCachingRequestWrapper request() {
        if (request instanceof ContentCachingRequestWrapper) {
            return (ContentCachingRequestWrapper) request;
        }
        return new ContentCachingRequestWrapper(request);
    }
}
