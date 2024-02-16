package org.acme.dto;

public class SaldoResponse {

  private double total;
  private String dataExtrato;
  private double limite;

  public SaldoResponse() {
  }

  public SaldoResponse(double total, String dataExtrato, double limite) {
    this.total = total;
    this.dataExtrato = dataExtrato;
    this.limite = limite;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public String getDataExtrato() {
    return dataExtrato;
  }

  public void setDataExtrato(String dataExtrato) {
    this.dataExtrato = dataExtrato;
  }

  public double getLimite() {
    return limite;
  }

  public void setLimite(double limite) {
    this.limite = limite;
  }
}
