package jpabook_jpashop.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jpabook_jpashop.Service.PostService;
import jpabook_jpashop.Service.MemberService;
import jpabook_jpashop.domain.Member;
import jpabook_jpashop.domain.Post;
import jpabook_jpashop.dto.MemberDto;
import jpabook_jpashop.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final MemberService memberService;

    @Autowired
    public PostController(PostService postService, MemberService memberService){

        this.postService = postService;
        this.memberService = memberService;
    }
    // 게시물 작성 API
    @Operation(summary = "registerPost", description = "게시글 작성", tags = {"Post"}) // Swagger 설명 어노테이션
    @ApiResponses({ // Swagger 응답 어노테이션
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = MemberDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })

    //게시글 작성
    @PostMapping
    public ResponseEntity<PostDto> registerPost(@RequestBody PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        Optional<Member> memberOptional = memberService.getMemberById(postDto.getMember().getId());
        if (memberOptional.isPresent()){
            Member member = memberOptional.get();
            post.setMember(member);
        }else{
            throw new RuntimeException("해당 맴버가 존재하지 않습니다. : "+ postDto.getId());
        }

        Post registeredPost = postService.registerPost(post);
        return ResponseEntity.ok(PostDto.from(registeredPost));
    }

    //모든 게시글 조회
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPost(){
        List<Post> posts = postService.getAllPosts();
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts){
            PostDto dto = PostDto.from(post);
            postDtos.add(dto);
        }
        return ResponseEntity.ok(postDtos);
    }

    //id로 게시글 보기
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name="id")Long id){
        Optional<Post> postOptional = postService.getPostById(id);
        if (postOptional.isPresent()){
            PostDto dto = PostDto.from(postOptional.get());
            return ResponseEntity.ok(dto);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable(name="id") Long id){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    //게시글 수정
    @PatchMapping("/{id}")
    public ResponseEntity<PostDto> UpdatePost(@PathVariable(name="id") Long id, @RequestBody PostDto postDto) {
        try {
            Post updatePost = postService.updatePost(id, postDto);
            return ResponseEntity.ok(PostDto.from(updatePost));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }




}
