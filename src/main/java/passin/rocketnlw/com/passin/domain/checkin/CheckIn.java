package passin.rocketnlw.com.passin.domain.checkin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import passin.rocketnlw.com.passin.domain.attendee.Attendee;

import java.time.LocalDateTime;

@Entity
@Table(name = "check_ins")
@Getter
@Setter
public class CheckIn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "creeated_at")
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "attendee_id", nullable = false)
    private Attendee attendee;
}
