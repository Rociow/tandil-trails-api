package tandil_trails.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Esta clase representa a un usuario en el sistema. Implementa UserDetails para integrarse con Spring Security.
 * Contiene campos para el nombre de usuario, correo electrónico, contraseña, rol, fecha de creación y URL del avatar.
 * Proporciona los detalles necesarios para la autenticación y autorización de usuarios en la aplicación.
 * UserDetails (Usuario): Es el "¿Qué es?" (los datos del usuario adaptados para el framework).
 * Spring Security no sabe cómo llamaste a tus atributos (si pass, password o clave).
 * Al implementar la interfaz, le das métodos estándar como getPassword(), getUsername() y getAuthorities().
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {
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

    @ManyToMany
    @JoinTable(
            name = "usuario_senderos_favoritos",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "sendero_id")
    )
    private List<Sendero> favoritos = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "usuario_senderos_visitados",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "sendero_id")
    )
    private List<Sendero> visitados = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol));
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    @Override
    public String getUsername() { return username; }

    @Override
    public String getPassword() { return password; }

}
