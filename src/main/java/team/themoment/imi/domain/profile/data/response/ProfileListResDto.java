package team.themoment.imi.domain.profile.data.response;

import java.util.List;

public record ProfileListResDto(
        int amount,
        List<ProfileResDto> profileList
) {
}