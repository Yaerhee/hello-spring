package hello.hellospring.service;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@Service, @Autowired, @Repository 컴포넌트를 주석처리 한 후
//직접 Java를 통해 Bean을 주입할 수 있도록 설정 변경
public class SpringConfig {
    
    //아래의 Bean 두 개를 등록해준 후, 해당 Bean을 MemberService에 넣어주는 그림
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
        //이 부분을 이후 교체할 예정(직접 Bean을 활용하는 이유)
        // -> 상황에 따라 구현 클래스만 간단히 교체하여 기능을 활용할 수 있기 때문
    }




}
