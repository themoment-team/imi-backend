package team.themoment.imi.domain.profile.data.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdateProfileReqDto(
        @NotNull String major,
        @NotNull String content,
        @NotEmpty List<String> wanted
) {
}