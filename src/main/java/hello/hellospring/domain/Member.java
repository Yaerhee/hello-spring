package hello.hellospring.domain;

import javax.persistence.*;

@Entity //JPA가 관리하는 Entity로 설정됨
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    //DB가 ID를 자동으로 생성하는 것을 'identity 적용'이라고 함
    private Long id;

    //@Column(name = "username") DB 컬럼 이름이 다를 경우 컬럼명을 매핑
    private String name;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
