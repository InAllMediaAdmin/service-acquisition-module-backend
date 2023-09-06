package com.iam.serviceacquisition.highlights.domain;



public enum HighLightNames {

    ENGLISH_LEVEL {
        @Override
        public void buildHighLightDTO(HighLightDTO highLightDTO, HighLightData highLightData) {
            highLightDTO.setHighLightEnglishLevelDTO(highLightData);
        }

    },
    STRATEGY_LEVEL {
        @Override
        public void buildHighLightDTO(HighLightDTO highLightDTO, HighLightData highLightData) {
            highLightDTO.setHighlightStrategyDTO(highLightData);
        }
    },
    INDUSTRY {
        @Override
        public void buildHighLightDTO(HighLightDTO highLightDTO, HighLightData highLightData) {
            highLightDTO.setHighlightIndustryDTO(highLightData);
        }
    },
    EXPERIENCE {
        @Override
        public void buildHighLightDTO(HighLightDTO highLightDTO, HighLightData highLightData) {
            highLightDTO.setHighlightExperienceDTO(highLightData);
        }
    };

    public abstract void buildHighLightDTO(HighLightDTO highLightDTO, HighLightData highLightData);
}
