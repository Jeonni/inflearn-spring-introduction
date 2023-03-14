package hello.hellospring.domain;

import javax.persistence.*;

// DB에 저장 및 관리되는 비즈니스 도메인 객체

// 이제부터 JPA가 관리하는 Entity
@Entity
public class Member {

    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 쿼리에 id를 넣는 것이 아닌, db에 값을 넣으면 id를 자동으로 생성해주는 전략 (IDENTITY)
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
