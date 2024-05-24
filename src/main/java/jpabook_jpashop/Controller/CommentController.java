package jpabook_jpashop.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jpabook_jpashop.Service.CommentService;
import jpabook_jpashop.domain.Comment;
import jpabook_jpashop.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;


    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }
    // 댓글 작성 API
    @Operation(summary = "createComment", description = "댓글 작성", tags = {"Comment"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })

    //댓글작성
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto){
        Comment createdComment = commentService.registerComment(commentDto);
        return ResponseEntity.ok(CommentDto.from(createdComment));
//        Comment comment = new Comment();
//        comment.setContent(commentDto.getContent());
//        comment.setCommentDate(commentDto.getCommentDate());
//
//        Comment createdComment = commentService.registerComment(commentDto);
//        return ResponseEntity.ok(CommentDto.from(createdComment));
    }
    //모든 댓글 조회
    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComment() {
        List<Comment> comments = commentService.getAllComments();
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDto dto = CommentDto.from(comment);
            commentDtos.add(dto);
        }
        return ResponseEntity.ok(commentDtos);
    }

    //id로 댓글 조회
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(name = "id") Long id) {
        Optional<Comment> commentOptional = commentService.getCommentById(id);
        if (commentOptional.isPresent()) {
            CommentDto dto = CommentDto.from(commentOptional.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //댓글 수정
    @PatchMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(name = "id") Long id, @RequestBody CommentDto commentDto) {
        try {
            Comment updateComment = commentService.updateComment(id, commentDto);
            return ResponseEntity.ok(CommentDto.from(updateComment));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    //댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable(name = "id") Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }


}
