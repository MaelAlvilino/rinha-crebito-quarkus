package github.mael.dto;

import java.time.LocalDateTime;

public class UltimasTransacoesResponse {

    private Integer valor;
    private String tipo;
    private String descricao;
    private LocalDateTime realizada_em;

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

    public LocalDateTime getRealizada_em() {
        return realizada_em;
    }

    public void setRealizada_em(LocalDateTime realizada_em) {
        this.realizada_em = realizada_em;
    }
}
