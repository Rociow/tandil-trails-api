package tandil_trails.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private String rol;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private String avatarUrl;
}
