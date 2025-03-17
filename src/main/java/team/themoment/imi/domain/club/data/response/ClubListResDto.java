package team.themoment.imi.domain.club.data.response;

import team.themoment.imi.domain.club.enitity.Club;

import java.util.List;

public record ClubListResDto(
        int length,
        List<Club> clubs
) {
}