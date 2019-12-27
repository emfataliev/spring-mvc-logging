package ru.emfataliev.response;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.util.ContentCachingResponseWrapper;
import ru.emfataliev.HttpServletContent;
import ru.emfataliev.RepresentableAsString;

@RequiredArgsConstructor
class ResponsePayload implements RepresentableAsString {

    private final ContentCachingResponseWrapper response;

    @Override
    @SneakyThrows
    public String asString() {
        if (response != null) {
            String result = new HttpServletContent(
                    response.getContentAsByteArray(),
                    response.getCharacterEncoding()
            ).asString();
            response.copyBodyToResponse();
            return result;
        }

        return "";
    }
}
