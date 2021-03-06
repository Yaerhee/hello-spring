package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

//@Service //@Component의 일종으로 선언된 것

@Transactional //JPA는 join이 실행될 때 모두 Transactional 안에 있어야 함
public class MemberService {
    //Ctrl + Shift + T로 Test 클래스를 그대로 만들 수 있음!

    /*
    private final MemberRepository memberRepository = new MemoryMemberRepository();
    로 선언되었던 부분을 아래와 같이 고침
    -> static 인스턴스의 구조를 바꿈으로써 외부에서 memberRepository가 주입되도록 설정
    */

    private final MemberRepository memberRepository;

    //@Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //회원 가입
    public Long join(Member member) {
        //같은 이름이 있는 중복 회원 가입X
        //Ctrl + Alt + V 단축키로 변수 추출이 가능함

        long start = System.currentTimeMillis();
        
        //21-04-18 AOP 개념 이해 전, method 소요시간 코딩 (코딩은 삭제함, pdf 파일로 참고하기)
        //매번 method 내부에 시간을 측정하는 로직을 만들어 두어야 함
        //-> 특정 method 내부에 필요한 method ex. 시간 측정 같은 것은 "공통 관심 사항"이라고 칭함

        validateDuplicateMember(member);
        //Ctrl + Alt + M 으로 위의 method extract 하기 (validateDuplicateMember)

        //하단의 유저 등록 및 ID데이터 추출은 해당 method가 꼭 필요로 하는 고유 기능이므로 "핵심 관심 사항"이라고 칭함
        memberRepository.save(member);
        return member.getId();

        //optional을 감싸게 되면 null일 경우에 대한 코딩을 번거롭게 따로 하지 않아도 된다
        //직접 변수를 꺼내려면
        //Member member1 = result.get(); 과 같이 코딩하면 됨(권장하진 않음)
        //result.orElseGet() 처럼 변수 데이터 존재여부에 따라 값을 꺼낼 수도 있음

    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()) //Optional<Member> result = 부분 생략함
                .ifPresent(m -> { //result에 값이 존재할 경우 아래의 로직이 실행됨 (result.ifPresent 에서 문장 줄임)
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    //전체 회원 조회 기능 만들기
    public List<Member> findMembers() {
            return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
    
    //만들어진 위의 method를 테스트 하기? -> Service 테스트 클래스를 만들면 됨

}
