package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    //JpaRepository가 구현체를 자동으로 생성해 줌

    //JPQL Query로 select m from Member m where m.name = ?에
    //아래와 같이 적은 method 이름 만으로도 조회 기능을 제공함
    @Override
    Optional<Member> findByName(String name);
}
