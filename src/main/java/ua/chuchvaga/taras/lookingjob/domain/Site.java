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
@Table(name = "site")
public class Site implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Version
    @Column(name = "VERSION")
    private int version;

    @NotBlank(message = "{object.caption.notblank}")
    @Size(min = 2, max = 60, message = "{site.caption.size}")
    @Column(name = "CAPTION", nullable = false, unique = true)
    private String caption;

    @NotBlank(message = "{site.alias.notblank}")
    @Size(min = 2, max = 60, message = "{site.alias.size}")
    @Column(name = "ALIAS", nullable = false, unique = true)
    private String alias;

    @NotBlank(message = "{object.url.notblank}")
    @Size(min = 2, max = 100, message = "{site.url.size}")
    @Column(name = "URL", nullable = false, unique = true)
    private String url;

    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SitesIDsCities> idsRegions;
}
