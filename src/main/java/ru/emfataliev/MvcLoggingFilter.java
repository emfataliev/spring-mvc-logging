package ru.emfataliev;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import ru.emfataliev.request.ContentCachedRequest;
import ru.emfataliev.request.RequestLog;
import ru.emfataliev.response.ContentCachedResponse;
import ru.emfataliev.response.ResponseLog;

@Slf4j
@Component
@RequiredArgsConstructor
public class MvcLoggingFilter extends OncePerRequestFilter {

    private final RequestId requestId;

    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain) throws ServletException, IOException {
        if (!isAsyncDispatch(request) && request.getRequestURI().startsWith("/api")) {
            doFilterWithLogging(
                    new ContentCachedRequest(request).request(),
                    new ContentCachedResponse(response).response(),
                    filterChain
            );
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private void doFilterWithLogging(
            ContentCachingRequestWrapper request,
            ContentCachingResponseWrapper response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            requestId.generate();
            filterChain.doFilter(request, response);
        } finally {
            RequestLog requestLog = new RequestLog(requestId, request);
            log.info(requestLog.asString());
            ResponseLog responseLog = new ResponseLog(requestId, response);
            log.info(responseLog.asString());
        }
    }
}
