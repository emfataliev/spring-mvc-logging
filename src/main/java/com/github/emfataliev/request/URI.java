package com.github.emfataliev.request;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class URI {

    private final HttpServletRequest request;

    public String asString() {
        String queryString = request.getQueryString();
        if (queryString != null) {
            return request.getRequestURI() + "?" + queryString;
        }
        return request.getRequestURI();
    }
}
