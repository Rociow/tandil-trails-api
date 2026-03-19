package tandil_trails.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.LineString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "senderos")
public class Sendero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private double longitud;
    @Enumerated(EnumType.STRING)
    private Dificultad dificultad;
    @ManyToOne
    @JoinColumn(name = "estado_id")
    private EstadoSendero estado;
    private LineString ruta;

    @OneToMany(mappedBy = "sendero", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Waypoint> waypoints = new ArrayList<>();

    @OneToMany(mappedBy = "sendero", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resena> resenas = new ArrayList<>();

    @OneToMany(mappedBy = "sendero", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagenSendero> imagenes = new ArrayList<>();
}
