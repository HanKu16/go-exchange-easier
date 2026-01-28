package com.go_exchange_easier.backend.domain.user.specification;

import com.go_exchange_easier.backend.domain.user.User;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> hasNick(String nick) {
        return (root, query, cb) -> {
            Path<String> nickPath = root.get("nick");
            return cb.equal(nickPath, nick);
        };
    }

    public static Specification<User> fetchCountryOfOrigin() {
        return (root, query, cb) -> {
            if ((query.getResultType() != Long.class) &&
                    (query.getResultType() != long.class)) {
                root.fetch("countryOfOrigin");
            }
            return null;
        };
    }

    public static Specification<User> fetchHomeUniversity() {
        return (root, query, cb) -> {
            if ((query.getResultType() != Long.class) &&
                    (query.getResultType() != long.class)) {
                root.fetch("homeUniversity");
            }
            return null;
        };
    }

}
