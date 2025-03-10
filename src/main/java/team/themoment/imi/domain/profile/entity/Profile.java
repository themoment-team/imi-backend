package team.themoment.imi.domain.profile.entity;

import jakarta.persistence.*;
import team.themoment.imi.global.utils.StringListToStringConverter;

import java.util.List;
@Entity
@Table(name = "profile")
public class Profile {
    @Id @GeneratedValue
    @JoinColumn(referencedColumnName = "profile_id")
    private long id;
    @Convert(converter = StringListToStringConverter.class)
    private List<String> wanted;
    private String major;
    private String content;
}