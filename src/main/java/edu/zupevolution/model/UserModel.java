package edu.zupevolution.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "birthday", nullable = false)
    private Date birthday;
    @Column(name = "email", nullable = false)
    private String email;
    @OneToOne
    @JoinColumn(name = "id_access_type", referencedColumnName = "id", nullable = false)
    private AccessTypeModel access_type;

}
