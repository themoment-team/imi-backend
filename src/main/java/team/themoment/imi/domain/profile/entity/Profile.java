package team.themoment.imi.domain.profile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.themoment.imi.domain.user.entity.User;
import team.themoment.imi.global.utils.StringListToStringConverter;

import java.util.List;

@Entity
@Table(name = "profile")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(referencedColumnName = "profile_id")
    private long id;
    @Convert(converter = StringListToStringConverter.class)
    private List<String> wanted;
    private String major;
    private String content;

    @OneToOne(mappedBy = "profile", fetch = FetchType.EAGER)
    private User user;
}