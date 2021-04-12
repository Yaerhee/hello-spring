package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller //Spring 컨테이너에서 인식하고 기능할 수 있도록 함
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

}
