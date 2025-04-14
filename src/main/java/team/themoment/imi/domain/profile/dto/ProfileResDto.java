package team.themoment.imi.domain.profile.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class ProfileResDto {
    //User
    private String name;
    private String email;
    private int studentId; // 학번
    //Profile
    private Long id;
    private List<String> wanted;
    private String major;
    private String content;
}
