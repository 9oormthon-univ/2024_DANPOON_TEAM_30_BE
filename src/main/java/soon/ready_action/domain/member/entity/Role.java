package soon.ready_action.domain.member.entity;

public enum Role {

    ROLE_GUEST, ROLE_MEMBER;

    public static boolean isGuest(Role role) {
        return ROLE_GUEST.equals(role);
    }
}
