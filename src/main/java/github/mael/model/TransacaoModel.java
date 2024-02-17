package github.mael.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
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
public class TransacaoModel extends PanacheEntityBase {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "cliente_id", nullable = false)
  private ClienteModel cliente;

  @Column(name = "valor", nullable = false)
  private Integer valor;

  @Column(name = "tipo", nullable = false)
  private String tipo;

  @Column(name = "descricao", nullable = false)
  private String descricao;

  @Column(name = "realizada_em", nullable = false)
  private LocalDateTime realizadaEm;

  public TransacaoModel() {
  }

  public TransacaoModel(ClienteModel cliente, Integer valor, String tipo, String descricao,
      LocalDateTime realizadaEm) {
    this.cliente = cliente;
    this.valor = valor;
    this.tipo = tipo;
    this.descricao = descricao;
    this.realizadaEm = realizadaEm;
  }

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

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public LocalDateTime getRealizadaEm() {
    return realizadaEm;
  }

  public void setRealizadaEm(LocalDateTime realizadaEm) {
    this.realizadaEm = realizadaEm;
  }
}
