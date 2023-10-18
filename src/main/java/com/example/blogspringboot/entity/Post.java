package com.example.blogspringboot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="posts",uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}) // uniqueConstraints thêm các
// ràng buộc duy nhất cho thuộc tính title của post
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // khoá chính id sẽ được tự động tăng
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "content", nullable = false)
    private String content;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)  // một post sẽ có nhiều commnets
    // @OneToMany(mappedBy = "post") cho biết rằng trường comments trên entity Post sẽ được sử dụng để ánh xạ đến mối quan hệ. "post" ở đây là tên của trường post trong entity Comment.
    // cascade = CascadeType.ALL được thêm vào annotation @OneToMany. Điều này có nghĩa là nếu bạn thay đổi một Post (ví dụ: xóa một Post),
    // thì các thay đổi tương ứng sẽ được chuyển giao xuống các Comment liên quan và tất cả các thay đổi đó sẽ được thực hiện.
    // orphanRemoval = true được thêm vào annotation @OneToMany. Khi bạn xóa một Comment khỏi danh sách comments của một Post, nếu Comment đó không được tham chiếu từ bất kỳ Post nào khác, nó sẽ bị xóa khỏi cơ sở dữ liệu.
    private Set<Comment> comments = new HashSet<>();

}
