package ru.emfataliev.response;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.ContentCachingResponseWrapper;
import ru.emfataliev.RepresentableAsJson;
import ru.emfataliev.RepresentableAsString;
import ru.emfataliev.RequestId;

@RequiredArgsConstructor
public class ResponseLog implements RepresentableAsString {

    private final RequestId requestId;
    private final ContentCachingResponseWrapper response;
    private final ResponseHeaders responseHeaders;
    private final ResponsePayload responsePayload;

    public ResponseLog(RequestId requestId, ContentCachingResponseWrapper response) {
        this(
                requestId,
                response,
                new ResponseHeaders(response),
                new ResponsePayload(response)
        );
    }

    @Override
    public String asString() {
        return new ResponseLogMessage(
                requestId.asString(),
                response.getStatus(),
                responseHeaders.values(),
                responsePayload.asString()
        ).asString();
    }

    @Data
    @RequiredArgsConstructor
    private static class ResponseLogMessage implements RepresentableAsString, RepresentableAsJson {

        private final String rid;
        private final int status;
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
            result.addProperty("status", status);
            result.addProperty("headers", headers.toString());
            result.addProperty("payload", payload);
            return result;
        }
    }
}
