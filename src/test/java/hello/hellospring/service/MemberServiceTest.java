package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// win- 이전 디버그: shift + f10
class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    // 각 테스트를 실행하기 전
    @BeforeEach
    public void beforeEach() {
        // MemoryMemberRepository를 만들고
        memberRepository = new MemoryMemberRepository();
        // memberRepository를 memberService에 넣어준다.
        memberService = new MemberService(memberRepository);
    }

    /*
    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }
    */

    // 정상 플러그
    @Test
    void 회원가입() {
        //given (상황)
        Member member = new Member();
        member.setName("hello");

        //when (실행)
        Long saveId = memberService.join(member);

        //then (결과)
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    // 예외 플러그
    // 회원 이름이 중복되었을 때의 로직
    @Test
    public void 중복회원예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        /* 변수추출: Win- ctrl+alt+v */
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        //then

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}