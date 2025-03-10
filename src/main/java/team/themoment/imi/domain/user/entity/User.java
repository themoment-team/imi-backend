package team.themoment.imi.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import team.themoment.imi.domain.profile.entity.Profile;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id @GeneratedValue
    private long id;
    private String name;
    private String email;
    private int studentId; // 학번
    private String password; // encrypted string
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;
}
