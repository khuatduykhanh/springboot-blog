package com.example.blogspringboot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users",uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}), @UniqueConstraint(columnNames = {"email"})})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // Loại lấy dữ liệu này có nghĩa là dữ liệu liên quan sẽ được tải ngay lập tức, hoặc ngay cùng với thực thể chính.
    @JoinTable(name = "uesr_roles",
                joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"), // đánh dấu id là khoá ngoại của bảng User và có tên là user_roles
                inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id") // đánh dấu id của bảng role là khoá ngoại và có tên là role_id
    )
    private Set<Role> roles;
}
