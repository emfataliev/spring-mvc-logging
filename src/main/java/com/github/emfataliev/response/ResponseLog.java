package com.github.emfataliev.response;

import com.github.emfataliev.RequestId;
import java.util.Set;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@RequiredArgsConstructor
public class ResponseLog {

    private final RequestId requestId;
    private final int status;
    private final ResponseHeaders responseHeaders;
    private final ResponsePayload responsePayload;

    public ResponseLog(RequestId requestId, ContentCachingResponseWrapper response, Set<String> excludeHeaders, int payloadMaxLength) {
        this(
                requestId,
                response.getStatus(),
                new ResponseHeaders(response, excludeHeaders),
                new ResponsePayload(response, payloadMaxLength)
        );
    }

    public void log() {
        ResponseLogMessage responseLogMessage = new ResponseLogMessage(
                requestId.asString(),
                status,
                responseHeaders.values(),
                responsePayload.asString()
        );
        log.info(responseLogMessage.toString());
    }

    @Data
    @RequiredArgsConstructor
    private static class ResponseLogMessage {

        private final String rid;
        private final int status;
        private final HttpHeaders headers;
        private final String payload;
    }
}
