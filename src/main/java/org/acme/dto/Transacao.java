package org.acme.dto;

import java.util.Date;

public class Transacao {
  private double valor;
  private String tipo;
  private String descricao;
  private Date realizadaEm;


  public Transacao() {
  }

  public Transacao(double valor, String tipo, String descricao, Date realizadaEm) {
    this.valor = valor;
    this.tipo = tipo;
    this.descricao = descricao;
    this.realizadaEm = realizadaEm;
  }

  public double getValor() {
    return valor;
  }

  public void setValor(double valor) {
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

  public Date getRealizadaEm() {
    return realizadaEm;
  }

  public void setRealizadaEm(Date realizadaEm) {
    this.realizadaEm = realizadaEm;
  }
}

