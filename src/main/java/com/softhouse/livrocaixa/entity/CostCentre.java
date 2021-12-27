package com.softhouse.livrocaixa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CostCentre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, length = 20, unique = true)
    private String classification;

    @Column(nullable = false)
    private Boolean analyticalAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    private CostCentre subaccount;

    @Transient
    public boolean isRoot() {
        return subaccount == null;
    }

    @Transient
    public boolean isNotRoot() {
        return !isRoot();
    }

}
