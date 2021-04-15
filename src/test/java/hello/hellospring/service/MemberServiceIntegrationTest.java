package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//Spring과 연결하여 Test 설계하기
@SpringBootTest
@Transactional //테스트케이스에 설정 시 쿼리 실행 시 데이터를 넣어주고, 테스트 후 자동으로 삭제해 줌
class MemberServiceIntegrationTest {
    
    //단순 Test 절차이므로 필드 방식으로 Auto Injection 활용
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    //Spring과 연결한 Test여서 다른 방식을 활용하면 됨 (BeforeEach, AfterEach 삭제)

    @Test //Transactional 설정된 상태에서 실행 시 회원가입을 테스트 하고, 성공 시 테스트 이전의 컨디션으로 롤백함
    //@Commit 을 추가하면 Test 코드 실행 후에 커밋이 되어 데이터가 남게 됨
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
}