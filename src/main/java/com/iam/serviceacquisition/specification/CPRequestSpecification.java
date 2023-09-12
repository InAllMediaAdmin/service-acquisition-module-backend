package com.iam.serviceacquisition.specification;

import com.iam.serviceacquisition.common.enums.CPRequestStatus;
import com.iam.serviceacquisition.domain.Industry;
import com.iam.serviceacquisition.domain.teamrequest.CPRequest;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;


import java.util.List;
import java.util.Optional;

public class CPRequestSpecification {

    public static Specification<CPRequest> withIndustry(Optional<Industry> industry) {
        return (root, query, cb) -> industry.map(i -> cb.equal(root.get("industry"), i)).orElse(null);
    }

    public static Specification<CPRequest> withCPIds(List<Long> cpIds) {
        if (CollectionUtils.isEmpty(cpIds)) return null;
        return (root, query, cb) -> {
            Expression<Long> expression = root.get("clientPartner");
            return expression.in(cpIds);
        };
    }

    public static Specification<CPRequest> withStatuses(List<CPRequestStatus> statuses) {
        if(CollectionUtils.isEmpty(statuses)) return null;
        return (root, query, cb) -> {
            Expression<CPRequestStatus> expression = root.get("status");
            return expression.in(statuses);
        };
    }

    public static Specification<CPRequest> withTitle(Optional<String> title) {
        return (root, query, cb) -> title.map(i -> cb.like(root.get("title"), "%" + i + "%")).orElse(null);
    }
}
