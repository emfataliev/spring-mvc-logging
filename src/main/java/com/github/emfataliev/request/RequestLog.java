package com.github.emfataliev.request;

import com.github.emfataliev.RequestId;
import java.util.Set;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Slf4j
@RequiredArgsConstructor
public class RequestLog {

    private final RequestId requestId;
    private final String method;
    private final URI uri;
    private final RequestHeaders requestHeaders;
    private final RequestPayload requestPayload;

    public RequestLog(RequestId requestId, ContentCachingRequestWrapper servletRequest, Set<String> excludeHeaders, int payloadMaxLength) {
        this(
                requestId,
                servletRequest.getMethod(),
                new URI(servletRequest),
                new RequestHeaders(servletRequest, excludeHeaders),
                new RequestPayload(servletRequest, payloadMaxLength)
        );
    }

    public void log() {
        RequestLogMessage requestLogMessage = new RequestLogMessage(
                requestId.asString(),
                method,
                uri.asString(),
                requestHeaders.values(),
                requestPayload.asString()
        );
        log.info(requestLogMessage.toString());
    }

    @Data
    @RequiredArgsConstructor
    private static class RequestLogMessage {

        private final String rid;
        private final String method;
        private final String uri;
        private final HttpHeaders headers;
        private final String payload;
    }
}
