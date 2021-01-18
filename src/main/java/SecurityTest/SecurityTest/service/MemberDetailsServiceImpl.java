package SecurityTest.SecurityTest.service;

import SecurityTest.SecurityTest.domain.member.MyMemberDetails;
import SecurityTest.SecurityTest.exception.MemberNotFoundException;
import SecurityTest.SecurityTest.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@AllArgsConstructor
@Service("memberDetailsService")
public class MemberDetailsServiceImpl implements UserDetailsService {

    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return memberRepository.findById(id).
                map(u -> new MyMemberDetails(u, Collections.singleton(new SimpleGrantedAuthority(u.getRole().getValue()))))
                .orElseThrow(() -> new MemberNotFoundException(id));
    }
}
