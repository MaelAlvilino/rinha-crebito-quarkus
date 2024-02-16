package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class SaldoModel extends PanacheEntity {
  private Long id;
  @ManyToOne
  @JoinColumn(name = "cliente_id", nullable = false)
  private ClienteModel cliente;
  @Column(name = "valor", nullable = false)
  private Integer valor;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ClienteModel getCliente() {
    return cliente;
  }

  public void setCliente(ClienteModel cliente) {
    this.cliente = cliente;
  }

  public Integer getValor() {
    return valor;
  }

  public void setValor(Integer valor) {
    this.valor = valor;
  }
}
