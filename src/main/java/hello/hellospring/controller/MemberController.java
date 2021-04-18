package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller //Spring 컨테이너에서 인식하고 기능할 수 있도록 함
//MemberService를 통해 회원가입 및 회원정보 조회가 가능하도록 의존관계를 설정

public class MemberController {

    //private final MemberService memberService = new MemberService();
    //이렇게 적는 것 보다 컨테이너에 올려두고 간편하게 가져와 쓰는 것이 더 효율적임

    private final MemberService memberService;

    @Autowired //MemberController와 memberService의 연결(DI)
    public MemberController(MemberService memberService) {
        //memberService가 왜 인식이 안될까? 일반 java class라 인식할 방법이 없음.
        //MemberService 클래스 선언 위에 @Service 선언하자
        //+ Repository 파일에는 클래스 선언 위에 @Repository 선언!
        this.memberService = memberService;
        
        //21-04-18 AOP 적용 후 의존 관계 확인 -> 프록시로 생성된 memberService를 확인할 수 있음
        System.out.println("memberService = " + memberService.getClass());
        
    }

    //DI의 방식 3가지
    //1. 생성자 주입(위의 MemberController와 같은 방식): 현재 일반적으로 제일 많이 활용되고 있음
    //-> 생성자를 통한 데이터 생성 기능만 하고, 이외의 시점에는 변경을 막도록 함
    //2. 필드 주입: 존재하는 방식 중의 하나지만 권장되고 있지 않음, 유연하게 변경하기 어려움
    //3. Setter 주입: 호출이 들어올 때 set method가 public으로 열려있어야 함
    // (데이터 변경 시 문제가 발생할 수 있음) -> 보통 세팅되고 나면 바꾸는 일이 거의 없음

    @GetMapping("/members/new")
    public String createForm() { //해당 URL로 올 경우 구성된 form html이 출력됨
        //get은 주로 조회가 필요한 경우 사용
        return "members/createMemberForm";
    }

    @PostMapping("/members/new") //data를 등록할 때에는 보통 post를 사용함
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());
        //form에 있는 name을 가지고 와서 member의 name으로 설정해 줌

        //System.out.println("member = " + member.getName());
        //회원 가입 후 메인으로 redirect 될때 변수 내용을 확인할 수 있음(임시 테스트로)

        memberService.join(member);
        
        return "redirect:/"; //회원가입이 끝나면 홈으로 보내기
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
        //실행 중의 메모리에서 출력되는 것이므로 서버를 내리면 날아감
    }



}
