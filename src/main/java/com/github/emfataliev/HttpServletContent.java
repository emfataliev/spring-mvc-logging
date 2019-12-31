package com.github.emfataliev;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class HttpServletContent {

    private final byte[] content;
    private final String characterEncoding;
    private final int payloadMaxLength;

    @SneakyThrows
    public String asString() {
        if (content.length > 0) {
            int length = Math.min(payloadMaxLength, content.length);
            return new String(content, 0, length, characterEncoding);
        }
        return "";
    }
}
