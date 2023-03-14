package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

// DB에 접근, 도메인 객체를 DB에 저장하고 관리

public class MemoryMemberRepository implements MemberRepository {

    // Map<key, member> key는 회원의 id, 값은 member
    // sequence:  key값 0,1,2 같은 것들을 생성해주는 친구
    private static Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member->member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

}


// 동작이 잘 되는지 확인하기 위해서는 test Case 를 작성해야 함
// 실무에서는 당연하게 사용되는 기능임!


