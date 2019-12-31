package com.github.emfataliev;

import static java.util.stream.Collectors.toSet;

import com.github.emfataliev.request.ContentCachedRequest;
import com.github.emfataliev.request.RequestLog;
import com.github.emfataliev.response.ContentCachedResponse;
import com.github.emfataliev.response.ResponseLog;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.regex.Pattern;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@RequiredArgsConstructor
public class MvcLoggingFilter extends OncePerRequestFilter {

    private final Pattern endpointsPattern;
    private final Set<String> excludeHeaders;
    private final RequestId requestId;
    private final int payloadMaxLength;

    public MvcLoggingFilter(RequestId requestId, int payloadMaxLength, String endpointsPattern, String... excludeHeaders) {
        this(Pattern.compile(endpointsPattern), Arrays.stream(excludeHeaders).collect(toSet()), requestId, payloadMaxLength);
    }

    public MvcLoggingFilter(RequestId requestId, String endpointsPattern) {
        this(requestId, 16384, endpointsPattern);
    }

    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain) throws ServletException, IOException {
        if (!isAsyncDispatch(request) && endpointsPattern.matcher(request.getRequestURI()).matches()) {
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
            new RequestLog(requestId, request, excludeHeaders, payloadMaxLength).log();
            new ResponseLog(requestId, response, excludeHeaders, payloadMaxLength).log();
        }
    }
}
