package SecurityTest.SecurityTest.service;

import SecurityTest.SecurityTest.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    Member signUp(Member user);

    Optional<Member> findById(String id);

    List<Member> findAll();
}
