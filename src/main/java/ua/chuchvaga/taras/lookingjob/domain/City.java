package ua.chuchvaga.taras.lookingjob.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@lombok.Getter
@lombok.Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id", "version", "idsRegions"})
@ToString(exclude = {"id", "version", "idsRegions"})
@Entity
@Table(name = "city")
public class City implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Version
    @Column(name = "VERSION")
    private int version;

    @NotBlank(message = "{object.caption.notblank}")
    @Size(min = 2, max = 60, message = "{city.caption.size}")
    @Column(name = "CAPTION", nullable = false, unique = true)
    private String caption;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<SitesIDsCities> idsRegions;
}
