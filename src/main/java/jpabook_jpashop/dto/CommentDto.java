package jpabook_jpashop.dto;

import jpabook_jpashop.domain.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private String content;
    private LocalDate commentDate;
    private MemberDto member; //MemberDto 객체 포함
    private PostDto post; //Post 객체 포함

    public static CommentDto from(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setCommentDate(comment.getCommentDate());
        if (comment.getMember() != null) {
            commentDto.setMember(MemberDto.from(comment.getMember()));
        }
        if (comment.getPost() != null) {
            commentDto.setPost(PostDto.from(comment.getPost()));
        }
        return commentDto;
    }
}