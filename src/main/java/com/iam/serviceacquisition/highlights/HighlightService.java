package com.iam.serviceacquisition.highlights;

import com.iam.serviceacquisition.highlights.domain.HighLightDTO;
import com.iam.serviceacquisition.highlights.domain.HighLightRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
public class HighlightService {

    private final HighlightHandler highlightHandler;

    @Autowired
    public HighlightService(HighlightHandler highlightHandler) {
        this.highlightHandler = highlightHandler;
    }

    public HighLightDTO getHighLights (@NotNull @Valid HighLightRequest highLightRequest){
        return highlightHandler.getHighLights(highLightRequest);
    }
}
