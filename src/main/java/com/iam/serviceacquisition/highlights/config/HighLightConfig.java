package com.iam.serviceacquisition.highlights.config;

import com.iam.serviceacquisition.highlights.HighlightHandler;
import com.iam.serviceacquisition.highlights.rules.HighLightEnglishLevelRule;
import com.iam.serviceacquisition.highlights.rules.HighLightIndustryRule;
import com.iam.serviceacquisition.highlights.rules.HighlightExperienceRule;
import com.iam.serviceacquisition.highlights.rules.HighlightStrategyRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HighLightConfig {

    @Bean
    public HighlightHandler highLightHandler(){
        HighlightHandler highlightHandler = new HighlightHandler();
        highlightHandler.addHighlight(HighLightEnglishLevelRule.builder().build());
        highlightHandler.addHighlight(HighlightStrategyRule.builder().build());
        highlightHandler.addHighlight(HighLightIndustryRule.builder().build());
        highlightHandler.addHighlight(HighlightExperienceRule.builder().build());

        return highlightHandler;
    }
}
