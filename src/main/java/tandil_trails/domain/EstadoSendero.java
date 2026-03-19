package tandil_trails.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "estados_sendero")
public class EstadoSendero {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column (nullable = false)
    String nombre;
}
