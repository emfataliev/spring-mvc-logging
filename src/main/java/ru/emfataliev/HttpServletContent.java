package ru.emfataliev;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class HttpServletContent implements RepresentableAsString {

    private static final int PAYLOAD_MAX_LENGTH = 16384;

    private final byte[] content;
    private final String characterEncoding;

    @Override
    @SneakyThrows
    public String asString() {
        if (content.length > 0) {
            int length = Math.min(PAYLOAD_MAX_LENGTH, content.length);
            return new String(content, 0, length, characterEncoding);
        }
        return "";
    }
}
