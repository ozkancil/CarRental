package cars.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="t_user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50,nullable = false)
    private String firstName;
    @Column(length = 50,nullable = false)

    private String lastName;
    @Column(length = 80,nullable = false,unique = true)

    private String email;
    @Column(length = 150,nullable = false)
    private String password;
    @Column(length = 14,nullable = false)
    private String phoneNumber;
    @Column(length = 150,nullable = false)
    private String address;
    @Column(length = 15,nullable = false)
    private String zipCode;
    @Column(nullable = false)
    private Boolean builtIn;

    @ManyToMany
    @JoinTable(name="t_user_role",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    //SET type keeps us safe not to have the same role two times in a USER  roles property
    private Set<Role> roles=new HashSet<>();




}
