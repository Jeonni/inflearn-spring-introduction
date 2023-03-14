package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    // 참고로 생성자가 딱 하나가 있으면
    // 스프링 빈으로 등록되어 있으면 Autowired 애노테이션 생략해도 됨
    // 스프링이 dataSource 자동으로 injection 해줌
    @Autowired
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Member save(Member member) {
        // SimpleJdbcInsert: Jdbc 템플릿을 넘겨서 만듬, 테이블명:member, key값: id을 이렇게 해서 넣으면 쿼리를 짤 필요가 없음
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    // 줄이고 줄인 Jdbc 템플릿 라이브러리
    @Override
    public Optional<Member> findById(Long id) {
        // jdbc Template에서 쿼리를 날리고, memberRowMapper()를 통해 매핑 한 뒤, 해당 값을 리스트로 받아서 Optional<>로 바꾼 뒤 반환
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper(){
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId((rs.getLong("id")));
            member.setName(rs.getString("name"));
            return member;
        };
    }

}
