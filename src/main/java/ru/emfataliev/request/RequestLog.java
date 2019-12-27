package ru.emfataliev.request;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.ContentCachingRequestWrapper;
import ru.emfataliev.RepresentableAsJson;
import ru.emfataliev.RepresentableAsString;
import ru.emfataliev.RequestId;

@RequiredArgsConstructor
public class RequestLog implements RepresentableAsString {

    private final RequestId requestId;
    private final String method;
    private final URI uri;
    private final RequestHeaders requestHeaders;
    private final RequestPayload requestPayload;

    public RequestLog(RequestId requestId, ContentCachingRequestWrapper servletRequest) {
        this(
                requestId,
                servletRequest.getMethod(),
                new URI(servletRequest),
                new RequestHeaders(servletRequest),
                new RequestPayload(servletRequest)
        );
    }

    @Override
    public String asString() {
        return new RequestLogMessage(
                requestId.asString(),
                method,
                uri.asString(),
                requestHeaders.values(),
                requestPayload.asString()
        ).asString();
    }

    @Data
    @RequiredArgsConstructor
    private static class RequestLogMessage implements RepresentableAsString, RepresentableAsJson {

        private final String rid;
        private final String method;
        private final String uri;
        private final HttpHeaders headers;
        private final String payload;

        @Override
        public String asString() {
            return asJson().toString();
        }

        @Override
        public JsonElement asJson() {
            JsonObject result = new JsonObject();
            result.addProperty("rid", rid);
            result.addProperty("method", method);
            result.addProperty("uri", uri);
            result.addProperty("headers", headers.toString());
            if (!method.equals("GET")) {
                result.addProperty("payload", payload);
            }
            return result;
        }
    }
}
