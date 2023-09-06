package com.iam.serviceacquisition.highlights;

import com.iam.serviceacquisition.highlights.domain.HighLightResult;

@FunctionalInterface
public interface IHighlight <T> {
    HighLightResult calculate(T highlightRequest);
}
