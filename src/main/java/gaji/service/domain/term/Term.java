package gaji.service.domain.term;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Term {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Boolean isRequired;

    @OneToMany(mappedBy = "termAgree", cascade = CascadeType.ALL)
    private List<TermAgree> termAgreeList = new ArrayList<>();
}
