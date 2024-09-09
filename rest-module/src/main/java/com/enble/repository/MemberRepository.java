package com.enble.repository;

import com.enble.entity.Member;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {

    boolean existsByUsername(String username);
    Optional<Member> findByUsernameAndRemovedAtIsNull(String username);
    Optional<Member> findByIdAndRemovedAtIsNull(Long id);
}
