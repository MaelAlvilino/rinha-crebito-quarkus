package github.mael.dto;

import github.mael.model.TransacaoModel;
import java.util.List;

public class ExtratoResponse {

  private SaldoResponse saldo;
  private List<TransacaoModel> ultimasTransacoes;


  public ExtratoResponse() {
  }

  public ExtratoResponse(SaldoResponse saldo, List<TransacaoModel> ultimasTransacoes) {
    this.saldo = saldo;
    this.ultimasTransacoes = ultimasTransacoes;
  }

  public SaldoResponse getSaldo() {
    return saldo;
  }

  public void setSaldo(SaldoResponse saldo) {
    this.saldo = saldo;
  }

  public List<TransacaoModel> getUltimasTransacoes() {
    return ultimasTransacoes;
  }

  public void setUltimasTransacoes(List<TransacaoModel> ultimasTransacoes) {
    this.ultimasTransacoes = ultimasTransacoes;
  }
}
