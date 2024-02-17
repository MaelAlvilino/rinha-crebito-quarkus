package github.mael.dto;

public class TransacaoResponse {
  private Integer limite;
  private Integer novoSaldo;

  public TransacaoResponse(Integer limite, Integer novoSaldo) {
    this.limite = limite;
    this.novoSaldo = novoSaldo;
  }
  public Integer getLimite() {
    return limite;
  }
  public void setLimite(Integer limite) {
    this.limite = limite;
  }
  public Integer getNovoSaldo() {
    return novoSaldo;
  }

  public void setNovoSaldo(Integer novoSaldo) {
    this.novoSaldo = novoSaldo;
  }
}