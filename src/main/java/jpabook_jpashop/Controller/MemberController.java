package jpabook_jpashop.Controller;
import jpabook_jpashop.Service.MemberService;
import jpabook_jpashop.domain.Member;
import jpabook_jpashop.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    //회원가입
    @PostMapping
    public ResponseEntity<MemberDto> registerMember(@RequestBody MemberDto memberDto){
        Member member = new Member();
        member.setName(memberDto.getName());
        member.setEmail(memberDto.getEmail());

        Member registeredMember = memberService.registerMember(member);
        return ResponseEntity.ok(MemberDto.from(registeredMember));
    }

    //모든 회원 조회
    @GetMapping
    public ResponseEntity<List<MemberDto>> getAllMember(){
        List<Member> members = memberService.getAllMembers();
        List<MemberDto> memberDtos = new ArrayList<>();
        for (Member member : members){
            MemberDto dto = MemberDto.from(member);
            memberDtos.add(dto);
        }
        return ResponseEntity.ok(memberDtos);
    }

    //ID로 회원 조회
    @GetMapping
    public ResponseEntity<MemberDto> getMemberById(@PathVariable(name="id") Long id){
        Optional<Member> memberOptional = memberService.getMemberById(id);
        if (memberOptional.isPresent()){
            MemberDto dto = MemberDto.from(memberOptional.get());
            return ResponseEntity.ok(dto);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
