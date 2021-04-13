package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
    }

    //DI의 방식 3가지
    //1. 생성자 주입(위의 MemberController와 같은 방식): 현재 일반적으로 제일 많이 활용되고 있음
    //-> 생성자를 통한 데이터 생성 기능만 하고, 이외의 시점에는 변경을 막도록 함
    //2. 필드 주입: 존재하는 방식 중의 하나지만 권장되고 있지 않음, 유연하게 변경하기 어려움
    //3. Setter 주입: 호출이 들어올 때 set method가 public으로 열려있어야 함
    // (데이터 변경 시 문제가 발생할 수 있음) -> 보통 세팅되고 나면 바꾸는 일이 거의 없음

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

}
