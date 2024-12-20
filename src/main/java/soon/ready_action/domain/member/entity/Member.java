package soon.ready_action.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false)
    private String refreshToken;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, unique = true)
    private String kakaoId;

    @Enumerated(EnumType.STRING)
    private CharacterType characterType;

    @Builder
    public Member(String kakaoId) {
        this.nickname = "";
        this.birthday = LocalDate.now();
        this.refreshToken = "";
        this.role = Role.ROLE_GUEST;
        this.kakaoId = kakaoId;
        this.characterType = CharacterType.COOL;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public boolean isEqualsRefreshToken(String refreshToken) {
        return this.refreshToken.equals(refreshToken);
    }

    public void updateAdditionalInfo(
        String nickname, LocalDate birthday, String refreshToken, Role role
    ) {
        this.nickname = nickname;
        this.birthday = birthday;
        this.refreshToken = refreshToken;
        this.role = role;
    }

    public void updateCharacterType(int score) {
        this.characterType = CharacterType.of(score);
    }
}
