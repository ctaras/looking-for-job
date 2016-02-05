package ua.chuchvaga.taras.lookingjob.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@lombok.Getter
@lombok.Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id", "version"})
@ToString(exclude = {"id", "version"})
@Entity
@Table(name = "sites_ids_cities")
public class SitesIDsCities implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Version
    @Column(name = "VERSION")
    private int version;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "SITE_ID", nullable = false, referencedColumnName = "ID")
    private Site site;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CITY_ID", nullable = false, referencedColumnName = "ID")
    private City city;

    @NotNull
    @Column(name = "ID_REGION")
    private int idRegion;
}
