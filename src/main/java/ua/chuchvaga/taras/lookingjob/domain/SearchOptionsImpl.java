package ua.chuchvaga.taras.lookingjob.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@lombok.Getter
@lombok.Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Scope("prototype")
@Component
public class SearchOptionsImpl implements SearchOptions {
    @NotNull
    @Size(min = 3, max = 1000)
    private String query;

    @NotNull
    private City city;

    @NotEmpty(message = "{search_options.error.empty_sites_list}")
    private Set<Site> sites;

    @PostConstruct
    void init() {
        sites = new HashSet<Site>();
    }
}
