package hello.hellospring.service;

import hello.hellospring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
//@Service, @Autowired, @Repository 컴포넌트를 주석처리 한 후
//직접 Java를 통해 Bean을 주입할 수 있도록 설정 변경
public class SpringConfig {

    //JPA 설정 시 em도 함께 세팅해주어야 함
    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    //JDBC에서 설정한 dataSource 활용, 아래와 같이 생성자를 통한 DI 설계
    //private DataSource dataSource;

    /*
    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    */
    
    //아래의 Bean 두 개를 등록해준 후, 해당 Bean을 MemberService에 넣어주는 그림
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        //return new MemoryMemberRepository();
        //이 부분을 이후 교체할 예정(직접 Bean을 활용하는 이유)
        // -> 상황에 따라 구현 클래스만 간단히 교체하여 기능을 활용할 수 있기 때문

        //아래와 같이 생성해 둔 JDBC 리포지토리 파일로 교체
        //return new JdbcMemberRepository(dataSource); //위에 코딩한 DI를 활용

        //Spring을 활용한 JDBC 리포지토리 파일로 교체
        //return new JdbcTemplateMemberRepository(dataSource);
        
        //JPA를 활용한 리포지토리 파일로 교체
        return new JpaMemberRepository(em);

    }




}
