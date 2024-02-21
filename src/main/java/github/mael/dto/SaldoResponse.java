package github.mael.dto;

public class SaldoResponse {

  private Integer total;
  private String data_extrato;
  private Integer limite;

  public SaldoResponse() {
  }

  public SaldoResponse(Integer total, String data_extrato, Integer limite) {
    this.total = total;
    this.data_extrato = data_extrato;
    this.limite = limite;
  }

  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }

  public String getdata_extrato() {
    return data_extrato;
  }

  public void setdata_extrato(String data_extrato) {
    this.data_extrato = data_extrato;
  }

  public Integer getLimite() {
    return limite;
  }

  public void setLimite(Integer limite) {
    this.limite = limite;
  }
}
