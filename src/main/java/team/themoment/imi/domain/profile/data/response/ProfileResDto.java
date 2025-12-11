package team.themoment.imi.domain.profile.data.response;

import java.util.List;

public record ProfileResDto(
        // User
        String name,
        String email,
        int studentId,

        // Profile
        Long id,
        List<String> wanted,
        String major,
        String content,
        Boolean isLinked
) {
}