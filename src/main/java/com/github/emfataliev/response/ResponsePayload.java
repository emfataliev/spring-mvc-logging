package com.github.emfataliev.response;

import com.github.emfataliev.HttpServletContent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.util.ContentCachingResponseWrapper;

@RequiredArgsConstructor
class ResponsePayload {

    private final ContentCachingResponseWrapper response;
    private final int payloadMaxLength;

    @SneakyThrows
    public String asString() {
        if (response != null) {
            String result = new HttpServletContent(
                    response.getContentAsByteArray(),
                    response.getCharacterEncoding(),
                    payloadMaxLength
            ).asString();
            response.copyBodyToResponse();
            return result;
        }

        return "";
    }
}
