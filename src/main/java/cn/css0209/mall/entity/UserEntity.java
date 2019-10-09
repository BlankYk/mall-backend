package cn.css0209.mall.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author blankyk
 */
@Entity
@Data
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true,nullable = false,length = 10)
    private String username;
    @Column(unique = true,nullable = false,length = 10)
    private String nickname;
    @Column(nullable = false,length = 20)
    private String password;
}
