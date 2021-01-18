package SecurityTest.SecurityTest.domain.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberRole {
//  0 - 'N', 1 - 'Y'
    ROLE_NORMAL("ROLE_NORMAL"),
    ROLE_BIRD("ROLE_BIRD");

    private String value;
}
