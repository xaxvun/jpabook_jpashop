package jpabook_jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    private String content;
    private LocalDate commentDate;
}
