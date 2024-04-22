package passin.rocketnlw.com.passin.domain.event;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Table(name="events")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Event {
	
	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@Column(nullable = false, unique = true)
	private String title;
	
	@Column(nullable = false)
	private String details;
	
	@Column(nullable = false)
	private String slug;
	
	@Column(nullable = false)
	private Integer maximum_attendees;
}