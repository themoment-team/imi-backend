package team.themoment.imi.domain.profile.data.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateProfileReqDto(
        @NotNull(message = "희망전공은 필수입니다.")
        @Size(max = 50, message = "희망전공은 최대 50자까지 입력할 수 있습니다.")
        String major,
        @NotNull(message = "자기소개는 필수입니다.")
        @Size(max = 2400, message = "자기소개는 최대 2400자까지 입력할 수 있습니다.")
        String content,
        @NotEmpty(message = "희망 동아리는 필수입니다.")
        List<String> wanted
) {
}