package com.iam.serviceacquisition.highlights;

import com.iam.serviceacquisition.highlights.domain.HighLightDTO;
import com.iam.serviceacquisition.highlights.domain.HighLightRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HighlightHandler {
    private final List<IHighlight> highlight;

    public HighlightHandler() {
        this.highlight = new ArrayList<>();
    }

    public void addHighlight(IHighlight highlight) {
        this.highlight.add(highlight);
    }

    public HighLightDTO getHighLights(HighLightRequest highLightRequest) {
        HighLightDTO highLightDTO = new HighLightDTO();
        highlight.stream()
                 .map(h -> h.calculate(highLightRequest))
                 .collect(Collectors.toList())
                 .forEach(hr -> hr.getHighLightName().buildHighLightDTO(highLightDTO, hr.getHighLightData()));
        return highLightDTO;
    }
}
