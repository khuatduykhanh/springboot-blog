package com.example.blogspringboot.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "content")
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)   // annotation này giúp chú thích là có nhiều comment trong 1 post và 1 comment chỉ của môt post
    // LAZY: Ngược lại, nếu bạn sử dụng FetchType.LAZY, điều này có nghĩa là dữ liệu liên quan sẽ được tải lười biếng, hoặc chỉ khi được yêu cầu
    @JoinColumn(name = "post_id", nullable = false) // đây là khoá ngoại của Post
    private Post post;
}
