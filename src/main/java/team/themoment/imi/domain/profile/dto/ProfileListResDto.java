package team.themoment.imi.domain.profile.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProfileListResDto {
    private int amount;
    private List<ProfileResDto> profileList;
}
