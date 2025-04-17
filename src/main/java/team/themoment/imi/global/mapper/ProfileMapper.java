package team.themoment.imi.global.mapper;

import org.springframework.stereotype.Component;
import team.themoment.imi.domain.profile.data.response.ProfileListResDto;
import team.themoment.imi.domain.profile.data.response.ProfileResDto;
import team.themoment.imi.domain.profile.entity.Profile;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfileMapper {
    public ProfileResDto toProfileResDto(Profile profile){
        return new ProfileResDto(
                profile.getUser().getName(),
                profile.getUser().getEmail(),
                profile.getUser().getStudentId(),
                profile.getId(),
                profile.getWanted(),
                profile.getMajor(),
                profile.getContent()
        );
    }

    public ProfileListResDto toProfileListResDto(List<Profile> profiles){
        return new ProfileListResDto(
                profiles.size(),
                profiles.stream().map(this::toProfileResDto).collect(Collectors.toList())
        );
    }
}