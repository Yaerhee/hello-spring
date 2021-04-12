package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    //누적되는 데이터 값에 대한 초기화도 고려해야
    //MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    //위와 같이 new로 다른 객체를 또 만드는게 맞을까? static 변수인데.. 다른 리포지토리가 되어버리겠네;
    //아래 처럼 다시 만들어볼까?

    @BeforeEach //테스트 실행 전 마다 객체를 만들어 주고, memberRepository를 주입해 줌. (DI)
    public void beforeEach() {
        //AfterEach clearStore를 통해 DB의 값을 초기화 하려는 목적
        memberRepository = new MemoryMemberRepository();
        //위의 MemoryMemberRepository와 MemberService에 있는 MemoryMemberRepository는 다른 인스턴스임
        //구조적으로는 다른 Repository를 이용하고 있는 셈
        //-> MemberService 파일에서 memberRepository에 대해 의존 주입하도록 하면 됨
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void clearStore() {
        memberRepository.clearStore();
    }

    @Test
    public void 회원가입() throws Exception { //내부 테스트라 method명을 한글로 적어도 무방(실제 코드에 포함되지 않음)
        //given
        Member member = new Member();
        member.setName("hello");
        
        //when
        Long saveId = memberService.join(member); //Ctrl + Alt + V 변수 추출

        //then
        Member findMember = memberRepository.findById(saveId).get(); //optional로 변수 추출한 다음 다시 변수 추출
        assertEquals(member.getName(), findMember.getName()); //Alt + Enter로 static import 처리
    }
    
    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("spring");
        
        Member member2 = new Member();
        member2.setName("spring");
        
        /* try - catch 구문으로 적는 방법도 있지만, 아래와 같이 더 효율적인 방법으로 처리하는게 좋음
        try {
            memberService.join(member2);//여기 값도 spring이라 Exception이 뜰 것
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
            //이 메시지가 똑같이 뜨는지 test. member2가 중복이므로 같은 메시지가 송출되는 것이 확인되어야 함
            //메시지 부분을 수정해서 돌려보면 오류가 뜸.. 근데 이것 때문에 try catch를 써야 할까?
        }
        */
        
        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));
        //assertThrows에 해당하는 Exception class를 설정하고, 람다식으로 어떤 method로 해당 예외가 발생하는지 설정
        //다른 Exception을 조건으로 설정해 보면 Test가 실패함
        //Ctrl + Alt + V로 변수 추출

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