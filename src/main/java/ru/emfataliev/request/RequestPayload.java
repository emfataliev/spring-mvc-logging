package ru.emfataliev.request;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.util.ContentCachingRequestWrapper;
import ru.emfataliev.HttpServletContent;
import ru.emfataliev.RepresentableAsString;

/**
 * @author e.fataliev
 * @since 25.11.2019
 */
@RequiredArgsConstructor
public class RequestPayload implements RepresentableAsString {

    private final ContentCachingRequestWrapper request;

    @Override
    @SneakyThrows
    public String asString() {
        return Optional.ofNullable(request)
                .map(contentCachedRequest ->
                        new HttpServletContent(
                                contentCachedRequest.getContentAsByteArray(),
                                contentCachedRequest.getCharacterEncoding()
                        ).asString()
                )
                .orElse("");
    }
}
