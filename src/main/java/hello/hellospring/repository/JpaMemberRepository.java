package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em; //JPA에서 모듈을 동작하기 위한 환경
    //스프링부트가 설정했던 JPA Library를 인식하여 EntityManager를 활용할 수 있도록 도움

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); //em을 통해 데이터를 영구저장(INSERT query + ID 생성 처리)
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);//em을 통해 데이터를 찾음(SELECT query + 조회할 type 및 식별자 PK)
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class) //JPQL Query
                .getResultList(); //Ctrl + Alt + N -> Inline
        //객체를 대상으로 Query 전송 -> SQL로 번역됨
        //JPQL도 쓰지 않는 library도 존재함
    }
}

