package team.themoment.imi.domain.profile.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class ProfileResDto {
    private Long userId;
    private List<String> wanted;
    private String major;
    private String content;
}
