package team.themoment.imi.domain.club.enitity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Club {
    @Id @GeneratedValue
    private long id;
    private String name;
    private String leader;
    private String mainContent;
    private String iconUrl;
}
