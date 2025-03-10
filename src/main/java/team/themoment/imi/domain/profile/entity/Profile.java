package team.themoment.imi.domain.profile.entity;

import jakarta.persistence.*;
import lombok.*;
import team.themoment.imi.domain.user.entity.User;
import team.themoment.imi.global.utils.StringListToStringConverter;

import java.util.List;
@Entity
@Table(name = "profile")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    @Id @GeneratedValue
    @JoinColumn(referencedColumnName = "profile_id")
    private long id;
    @Convert(converter = StringListToStringConverter.class)
    private List<String> wanted;
    private String major;
    private String content;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
}