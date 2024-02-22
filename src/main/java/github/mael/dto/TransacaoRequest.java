package github.mael.dto;

  public class TransacaoRequest {

  private Double valor;
  private String tipo;
  private String descricao;


  public TransacaoRequest() {
  }

  public TransacaoRequest(Double valor, String tipo, String descricao) {
    this.valor = valor;
    this.tipo = tipo;
    this.descricao = descricao;
  }

  public Double getValor() {
    return valor;
  }

  public void setValor(Double valor) {
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
}
