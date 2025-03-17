package team.themoment.imi.domain.profile.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class UpdateProfileReqDto {
    private String major;
    private String content;
    private List<String> wanted;
}
