package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacoes")
public class TransacaoModel extends PanacheEntity {

  private Long id;

  @ManyToOne
  @JoinColumn(name = "cliente_id", nullable = false)
  private ClienteModel cliente;

  @Column(name = "valor", nullable = false)
  private Integer valor;

  @Column(name = "tipo", nullable = false)
  private Character tipo;

  @Column(name = "descricao", nullable = false)
  private String descricao;

  @Column(name = "realizada_em", nullable = false)
  private LocalDateTime realizadaEm;
}
