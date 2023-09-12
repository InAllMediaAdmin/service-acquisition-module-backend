package com.iam.serviceacquisition.util;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SpecificationUtils {
    private static final String COMPARAND_CHAR = "%";

    public static Specification withWordsInField(String words, String field) {
        return (root, query, cb) -> {
            Predicate spec = null;
            for(String word : words.split(" ")){
                final String concat = COMPARAND_CHAR.concat(word.toUpperCase()).concat(COMPARAND_CHAR);
                Predicate withWord = cb.like(cb.upper(root.get(field)), concat);
                if (spec == null){
                    spec = withWord;
                } else {
                    spec = cb.or(spec, withWord);
                }
            }

            return spec;
        };
    }

    public static Specification combine(Specification ... specs) {
        List<Specification> notNullSpecs = Arrays.stream(specs).filter(Objects::nonNull).collect(Collectors.toList());
        if (notNullSpecs.size() == 1) return notNullSpecs.get(0);
        return notNullSpecs.stream().skip(1).reduce(notNullSpecs.get(0), (a, b) -> a.and(b));
    }
}
