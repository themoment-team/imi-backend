package team.themoment.imi.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import team.themoment.imi.domain.profile.entity.Profile;

@Entity
@Getter
@Setter
public class User {
    @Id @GeneratedValue
    private long id;
    private String name;
    private String email;
    private int studentId; // 학번
    private String password; // encrypted string
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="profile_id")
    private Profile profile;
}
