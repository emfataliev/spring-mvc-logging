package com.github.emfataliev.request;

import com.github.emfataliev.HttpServletContent;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.util.ContentCachingRequestWrapper;

@RequiredArgsConstructor
public class RequestPayload {

    private final ContentCachingRequestWrapper request;
    private final int payloadMaxLength;

    @SneakyThrows
    public String asString() {
        return Optional.ofNullable(request)
                .map(contentCachedRequest ->
                        new HttpServletContent(
                                contentCachedRequest.getContentAsByteArray(),
                                contentCachedRequest.getCharacterEncoding(),
                                payloadMaxLength
                        ).asString()
                ).orElse("");
    }
}
