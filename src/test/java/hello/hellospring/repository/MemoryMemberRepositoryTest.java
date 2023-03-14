package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

// Window- static import: Alt + Enter
import static org.assertj.core.api.Assertions.*;

// 모든 테스트 코드는 순서와 상관 없이 에러가 뜨면 안됨
// 따라서 테스트가 끝나면 데이터를 깔끔하게 clear 해줘야 함 !
public class MemoryMemberRepositoryTest {

    MemberRepository repository = new MemoryMemberRepository();

    /*
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }
    */

    @Test
    public void save() {
        Member member = new Member();
        member.setName("Spring");
        repository.save(member);

        // Optional<>로 묶여있을 땐 .get()으로 값을 꺼내올 수 있음 (자주 쓰는 방법은 아님)
        Member result = repository.findById(member.getId()).get();
        System.out.println("result = " + (result == member));

        // 저장한 값과 DB에서 꺼낸 값이 똑같으면 true
        // 근데 이것을 시각적으로만 확인할 수는 없기 때문에 다음과 같은 방법을 사용
        // Assertions.assertEquals(member, result);

        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
        Member result2 = repository.findByName("spring2").get();

        assertThat(member1).isEqualTo(result);
        assertThat(member2).isEqualTo(result2);
    }


    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member1.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }

}
