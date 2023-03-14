package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


// 컨트롤러는 스프링이 관리하는 것이기 때문에 컴포넌트 스캔 어노테이션 사용
@Controller
public class MemberController {
    private final MemberService memberService;

    // 생성자 호출
    // 스프링이 memberService를 스프링 컨테이너에 있는 memberService를 가져다 연결시켜줌
    // 어노테이션을 통해 의존성 주입
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    // 주로 조회할 때 사용
    @GetMapping("/members/new")
    public String creatForm() {
        return "members/creatMemberForm";
    }

    // 보통 데이터를 넣어서 전달할 때 주로 사용
    // HTML 코드를 보면 Post라고 적혀있는 것을 확인 가능
    @PostMapping("/members/new")
    public String creat(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}