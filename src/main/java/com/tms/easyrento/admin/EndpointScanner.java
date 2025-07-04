package com.tms.easyrento.admin;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-06-28 13:07
 */

@Component
public class EndpointScanner {

    private final RequestMappingHandlerMapping handlerMapping;

    public EndpointScanner(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    public List<String> getAllEndpoints() {
        return handlerMapping.getHandlerMethods().entrySet().stream()
                .flatMap(entry -> {
                    assert entry.getKey().getPatternsCondition() != null;
                    Set<String> patterns = entry.getKey().getPatternsCondition().getPatterns();
                    Set<RequestMethod> methods = entry.getKey().getMethodsCondition().getMethods();
                    return patterns.stream()
                            .flatMap(pattern -> methods.isEmpty()
                                    ? Stream.of(pattern)
                                    : methods.stream().map(m -> m.name() + " " + pattern));
                })
                .sorted()
                .toList();
    }

    /**
     * AuthController: {GET: [api/v1/logged-user-info, api/v1/time], POST: [api/v1/login, api/v1/register]}
     * @return
     */

    public Map<String, Map<String, List<String>>> getGroupedEndpoints() {
        Map<String, Map<String, List<String>>> grouped = new TreeMap<>();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMapping.getHandlerMethods().entrySet()) {
            RequestMappingInfo mappingInfo = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();

            String controllerName = handlerMethod.getBeanType().getSimpleName(); // e.g., UserAccountController

            Set<String> patterns = extractPatterns(mappingInfo);
            Set<RequestMethod> methods = extractMethods(mappingInfo);

            if (patterns.isEmpty()) continue;

            /**
             * Skip mapping that does not have any method mapping
             * <pre>@RequestMapping("/error")</pre>
             */
            if (methods.isEmpty())
                continue;

            // Initialize nested maps
            grouped.putIfAbsent(controllerName, new TreeMap<>());

            for (RequestMethod method : methods) {
                grouped.get(controllerName).putIfAbsent(method.name(), new ArrayList<>());
                grouped.get(controllerName).get(method.name()).addAll(patterns);
            }
        }

        System.out.println("Grouped endpoints: " + grouped);

        return grouped;
    }


    public Set<String> extractPatterns(RequestMappingInfo mappingInfo) {
        PatternsRequestCondition patternsCondition = mappingInfo.getPatternsCondition();
        if (patternsCondition != null && !patternsCondition.getPatterns().isEmpty()) {
            return patternsCondition.getPatterns();
        }

        PathPatternsRequestCondition pathPatternsCondition = mappingInfo.getPathPatternsCondition();
        if (pathPatternsCondition != null && !pathPatternsCondition.getPatterns().isEmpty()) {
            return pathPatternsCondition.getPatterns().stream().map(PathPattern::getPatternString).collect(Collectors.toSet());
        }

        return Collections.emptySet();
    }

    public Set<RequestMethod> extractMethods(RequestMappingInfo mappingInfo) {
        RequestMethodsRequestCondition methodsCondition = mappingInfo.getMethodsCondition();
        if (!methodsCondition.getMethods().isEmpty()) {
            return methodsCondition.getMethods();
        }
        return Collections.emptySet();
    }


}

