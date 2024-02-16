package org.acme.dto;

import java.util.List;
import org.acme.model.TransacaoModel;

public class ExtratoResponse {

  private SaldoResponse saldo;
  private List<Transacao> ultimasTransacoes;


  public ExtratoResponse() {
  }

  public ExtratoResponse(SaldoResponse saldo, List<Transacao> ultimasTransacoes) {
    this.saldo = saldo;
    this.ultimasTransacoes = ultimasTransacoes;
  }

  public SaldoResponse getSaldo() {
    return saldo;
  }

  public void setSaldo(SaldoResponse saldo) {
    this.saldo = saldo;
  }

  public List<Transacao> getUltimasTransacoes() {
    return ultimasTransacoes;
  }

  public void setUltimasTransacoes(List<Transacao> ultimasTransacoes) {
    this.ultimasTransacoes = ultimasTransacoes;
  }
}
