package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    // 회원이 저장소에 저장됨
    Member save(Member member);

    // Optional: Java 8부터 들어간 기능
    // 요즘은 null을 그대로 반환하는 것 대신, Optional로 감싸서 반환하는 방식을 선호
    // 회원 ID, NAME을 찾을 수 있음
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);

    // 현재까지 저장된 모든 회원 리스트들을 반환
    List<Member> findAll();
}
