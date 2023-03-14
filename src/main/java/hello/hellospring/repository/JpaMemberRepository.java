package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


public class JpaMemberRepository implements MemberRepository{

    // build.gradle에서 jpa 라이브러리를 받았기 때문에 스프링 부트는 자동으로 EntityManager를 생성
    // 우리는 만들어진 EntityManager를 Injeection 하면 됨
    // 스프링은 EntityManager로 모든 것이 동작함
    // JPA를 쓰려면 EntityManager를 주입받아야 함
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    // 저장
    @Override
    public Member save(Member member) {
        em.persist(member); // member 를 저장
        return member;
    }


    // 조회
    @Override
    public Optional<Member> findById(Long id) {
        // em.find(조회 할 타입, 식별자 pk값)
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }


    // 여러 개의 리스트를 가지고 찾아내는 것은
    // 스프링 기반이 아닌 나머지들은 jpql을 사용해야 함
    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // 인라인화 : ctrl + alt + n
        // jpql 쿼리언어: 보통 테이블 대상으로 쿼리를 날리지만, 이것은 객체(Entity)를 대상으로 쿼리를 날림
        // Member 클래스의 객체 m(Entity) 자체를 조회해서 select 한다.
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

}
