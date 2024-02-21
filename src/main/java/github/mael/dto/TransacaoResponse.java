package github.mael.dto;

public class TransacaoResponse {
  private Integer limite;
  private Integer saldo;

  public TransacaoResponse(Integer limite, Integer saldo) {
    this.limite = limite;
    this.saldo = saldo;
  }
  public Integer getLimite() {
    return limite;
  }
  public void setLimite(Integer limite) {
    this.limite = limite;
  }
  public Integer getsaldo() {
    return saldo;
  }

  public void setsaldo(Integer saldo) {
    this.saldo = saldo;
  }
}