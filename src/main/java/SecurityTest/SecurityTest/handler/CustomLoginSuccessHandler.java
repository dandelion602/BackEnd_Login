package SecurityTest.SecurityTest.handler;

import SecurityTest.SecurityTest.constants.AuthConstants;
import SecurityTest.SecurityTest.domain.member.Member;
import SecurityTest.SecurityTest.domain.member.MyMemberDetails;
import SecurityTest.SecurityTest.utils.TokenUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        Member user = ((MyMemberDetails) authentication.getPrincipal()).getMember();
        String token = TokenUtils.generateJwtToken(user);
        response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + token);
    }

}