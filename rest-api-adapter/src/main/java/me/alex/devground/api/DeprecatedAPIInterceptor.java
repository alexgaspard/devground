package me.alex.devground.api;

import org.springframework.core.*;
import org.springframework.http.*;
import org.springframework.http.converter.*;
import org.springframework.http.server.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.util.*;

@RestControllerAdvice
public class DeprecatedAPIInterceptor implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(final MethodParameter returnType, final Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(final Object body, final MethodParameter returnType, final MediaType selectedContentType,
                                  final Class<? extends HttpMessageConverter<?>> selectedConverterType, final ServerHttpRequest request,
                                  final ServerHttpResponse response) {
        final DeprecatedAPI methodAnnotation = returnType.getMethodAnnotation(DeprecatedAPI.class);
        Optional.ofNullable(methodAnnotation).ifPresent(ann -> response.getHeaders().add("Deprecated", ann.location()));
        return body;
    }
}