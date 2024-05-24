package jpabook_jpashop.Service;

import jpabook_jpashop.Repository.MemberRepository;
import jpabook_jpashop.Repository.PostRepository;
import jpabook_jpashop.Repository.CommentRepository;
import jpabook_jpashop.dto.CommentDto;
import jpabook_jpashop.domain.Comment;
import jpabook_jpashop.domain.Post;
import jpabook_jpashop.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, MemberRepository memberRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
    }


    //댓글 입력
    @Transactional
    public Comment registerComment(CommentDto commentDto) {
        Optional<Post> postOptional = postRepository.findById(commentDto.getPost().getId());
        Optional<Member> memberOptional = memberRepository.findById(commentDto.getMember().getId());

        //게시글과 회원이 존재하는지 확인
        if (postOptional.isPresent() && memberOptional.isPresent()) {
            Comment comment = new Comment();
            comment.setPost(postOptional.get());
            comment.setMember(memberOptional.get());
            comment.setContent(commentDto.getContent());
            comment.setCommentDate(commentDto.getCommentDate());
            return commentRepository.save(comment);
        } else {
            throw new RuntimeException("Not Found");
        }

    }

    //모든 댓글 조회
    public List<Comment> getAllComments() {

        return commentRepository.findAll();
    }

    //ID로 댓글 조회
    public Optional<Comment> getCommentById(Long id) {

        return commentRepository.findById(id);
    }

    //댓글 삭제
    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
    //댓글 수정
    @Transactional
    public Comment updateComment(Long id, CommentDto commentDto) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            comment.setContent(commentDto.getContent());
            comment.setCommentDate(commentDto.getCommentDate());

            Optional<Post> postOptional = postRepository.findById(commentDto.getPost().getId());
            Optional<Member> memberOptional = memberRepository.findById(commentDto.getMember().getId());

            if (postOptional.isPresent() && memberOptional.isPresent()) {
                comment.setPost(postOptional.get());
                comment.setMember(memberOptional.get());
            } else {
                throw new RuntimeException("Post or Member not found");
            }

            return commentRepository.save(comment);
        } else {
            throw new RuntimeException("Comment not found with id " + id);
        }
    }

//        Optional<Comment> commentOptional = commentRepository.findById(id);
//        if (commentOptional.isPresent()){
//            Comment comment = commentOptional.get();
//            comment.setContent(commentDto.getContent());
//            return commentRepository.save(comment);
//        }else{
//            throw new RuntimeException("Comment not found with id"+id);
//        }
}

