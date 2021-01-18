package SecurityTest.SecurityTest.controller;


import SecurityTest.SecurityTest.domain.member.Member;
import SecurityTest.SecurityTest.domain.role.MemberRole;
import SecurityTest.SecurityTest.service.MemberService;
import SecurityTest.SecurityTest.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/user")
@Log4j2
public class MemberController {

    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberService memberService;

    @PostMapping(value = "/signUp")
    public ResponseEntity signUp(@RequestBody Member member) {
        member.setPw(passwordEncoder.encode(member.getPw()));
        return memberService.findById(member.getId()).isPresent()
                ? ResponseEntity.badRequest().build()
                : ResponseEntity.ok(TokenUtils.generateJwtToken(memberService.signUp(member)));
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity findAll() {
        return ResponseEntity.ok(memberService.findAll());
    }

}
