package github.mael.dto;

public class SaldoResponse {

  private Integer total;
  private String dataExtrato;
  private Integer limite;

  public SaldoResponse() {
  }

  public SaldoResponse(Integer total, String dataExtrato, Integer limite) {
    this.total = total;
    this.dataExtrato = dataExtrato;
    this.limite = limite;
  }

  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }

  public String getDataExtrato() {
    return dataExtrato;
  }

  public void setDataExtrato(String dataExtrato) {
    this.dataExtrato = dataExtrato;
  }

  public Integer getLimite() {
    return limite;
  }

  public void setLimite(Integer limite) {
    this.limite = limite;
  }
}
