package com.softhouse.livrocaixa.entity;

import com.softhouse.livrocaixa.dto.response.CostCentreMapDto;
import com.softhouse.livrocaixa.enums.TransactionType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@NamedNativeQuery(name = "Transaction.getCostCentreMap",
   query = """
        WITH sub AS (
            SELECT c.classification, c.description, SUM(t.value) AS total
                FROM cost_centre c
                LEFT JOIN (
                    SELECT date, account_id, cost_centre_id, value
                        FROM transaction
                        WHERE account_id = :id AND date BETWEEN :startDate AND :endDate
                ) AS t ON t.cost_centre_id = c.id
                GROUP BY c.classification
                ORDER BY c.classification
        )
        SELECT sub.classification, sub.description,
            COALESCE(
                SELECT SUM(x.total) FROM sub x
                    WHERE x.classification LIKE CONCAT(sub.classification, '%')
                , 0.00
            ) AS total
            FROM sub
    """,
        resultSetMapping = "Mapping.CostCentreMapDto"
)

@SqlResultSetMapping(name = "Mapping.CostCentreMapDto",
    classes = @ConstructorResult(targetClass = CostCentreMapDto.class,
                columns = {
                        @ColumnResult(name = "classification"),
                        @ColumnResult(name = "description"),
                        @ColumnResult(name = "total")
                }
    )
)

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String narration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    private CostCentre costCentre;

    @Column(nullable = false)
    private BigDecimal value;

}
