package passin.rocketnlw.com.passin.domain.attendee;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import passin.rocketnlw.com.passin.domain.event.Event;

@Entity
@Table(name = "attendees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attendee {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String email;

	@CreatedDate
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JoinColumn(name = "event_id", nullable = false)
	private Event event;
}
