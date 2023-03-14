package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// 회원 서비스 핵심 비즈니스 로직 구현
// 보통 비즈니스에 의존적으로 용어를 선택
// test 코드 만들려면 ctrl+shift+t 단축키


// JPA를 쓰려면 join 들어올 때 모든 데이터 변경이 트랜젝션 안에서 실행되어야 함
// 그래서 @Transactional 애노테이션 필수
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    // DI(Dependency Injection)- 내가 직접 new 하지 않고, 외부에서 memberRepository를 넣어준다.

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원 가입
    // 아이디만 반환하겠다는 설정
    public Long join(Member member) {
            validateDuplicateMember(member); // 중복 회원 검증
            memberRepository.save(member);
            return member.getId();

        // 같은 이름이 있는 중복 회원은 안됨
        // Optional<>로 객체를 감싸서 반환하기 때문에 여러 메서드를 사용할 수 있음
        // window- ctrl + alt + v : 변수 추출
        // Optional<Member> result = memberRepository.findByName(member.getName());
    }

    // window- Shift+Ctrl+Alt+T :  자동화 리팩토링
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }


    // test code에서 회원ID 값을 찾아주는 메서드
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
