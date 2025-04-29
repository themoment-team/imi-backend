package team.themoment.imi.domain.profile.data.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateProfileReqDto(
        @NotNull String major,
        @NotNull @Size(max = 2400) String content,
        @NotEmpty List<String> wanted
) {
}