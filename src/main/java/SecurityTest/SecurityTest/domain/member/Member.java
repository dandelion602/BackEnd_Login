package SecurityTest.SecurityTest.domain.member;


import SecurityTest.SecurityTest.domain.role.MemberRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "MEMBER")
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NUMBER", nullable = false)
    private int number;

    @Column(name = "ID", nullable = false, unique = true, length = 45)
    private String id;

    @Column(name = "PW", nullable = false, length = 100)
    private String pw;

    @Column(name = "BIRTHDAY", nullable = false)
    private LocalDateTime birthday;

    @Column(name = "NAME", nullable = false, length = 45)
    private String name;

    @Column(name= "PHONE", nullable = false, length = 45)
    private String phone;

    @Column(name = "ADDRESS", nullable = false, length = 45)
    private String address;

    @Column(name = "BIRD", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private MemberRole role;

    @Column(name = "POINT", nullable = false)
    private int point;


}
