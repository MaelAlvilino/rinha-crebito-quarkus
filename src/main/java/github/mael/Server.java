package github.mael;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.mael.dto.SaldoResponse;
import github.mael.dto.TransacaoRequest;
import github.mael.dto.TransacaoResponse;
import github.mael.dto.UltimasTransacoesResponse;
import github.mael.model.ClienteModel;
import github.mael.model.TransacaoModel;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import github.mael.dto.ExtratoResponse;
import github.mael.model.SaldoModel;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jboss.resteasy.reactive.RestResponse;
@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Server {
  @Inject
  EntityManager entityManager;
  @GET
  @Path("/{clienteId}/extrato")
  @Transactional
  public RestResponse<ExtratoResponse> getExtrato(Long clienteId) {
    try {
      ClienteModel cliente = entityManager.find(ClienteModel.class, clienteId);
      if (cliente == null) {
        return RestResponse.status(404);
      }

      List<TransacaoModel> transacoes = entityManager.createQuery(
              "SELECT t FROM TransacaoModel t WHERE t.cliente.id = :clienteId ORDER BY t"
                  + ".realizadaEm DESC", TransacaoModel.class)
          .setParameter("clienteId", clienteId)
          .setMaxResults(10)
          .getResultList();


      SaldoModel saldoAtual = entityManager.createQuery(
              "SELECT s FROM SaldoModel s WHERE s.cliente.id = :clienteId", SaldoModel.class)
          .setParameter("clienteId", clienteId)
          .getSingleResult();

      Integer saldo = (saldoAtual != null) ? saldoAtual.getValor() : 0;

      String dataExtrato = String.valueOf(new Date());

      List<UltimasTransacoesResponse> convertTransacao =
          transacoes.stream().map(transacao  -> {
                UltimasTransacoesResponse ultimaTransacao = new UltimasTransacoesResponse();
                ultimaTransacao.setValor(transacao.getValor());
                ultimaTransacao.setTipo(transacao.getTipo());
                ultimaTransacao.setDescricao(transacao.getDescricao());
                ultimaTransacao.setRealizada_em(transacao.getRealizadaEm());
                return ultimaTransacao;
              } )
              .collect(Collectors.toList());

      ExtratoResponse extratoResponse = new ExtratoResponse();
      extratoResponse.setSaldo(new SaldoResponse(saldo, dataExtrato, cliente.getLimite()));
      extratoResponse.setUltimas_transacoes(convertTransacao);
      return RestResponse.ok(extratoResponse);
    } catch (Exception e) {
      e.printStackTrace();
      return RestResponse.status(404);
    }
  }

  @POST
  @Path("/{id}/transacoes")
  @Transactional
  public RestResponse<TransacaoResponse> createTransacao(
      Long id,
      TransacaoRequest request) {
    try {
      int valor = request.getValor().intValue();
      String tipo = request.getTipo();
      String descricao = request.getDescricao();


      if (!isValid(request)) {
        return RestResponse.status(422);
      }
      ClienteModel cliente = entityManager.find(ClienteModel.class, id);

      if (cliente == null) {
        return RestResponse.status(404);
      }

      Query saldoQuery = entityManager.createNativeQuery("SELECT * FROM public"
          + ".saldos WHERE "
          + "public.saldos.cliente_id = :clienteId FOR UPDATE", SaldoModel.class);
      assert cliente != null;
      saldoQuery.setParameter("clienteId", cliente.getId());

      SaldoModel saldo = (SaldoModel) saldoQuery.getSingleResult();

      Integer novoSaldo = saldo.getValor() + (tipo.equals("c") ? valor : -valor);
      if (novoSaldo < -cliente.getLimite()) {
        return RestResponse.status(422);
      }

      saldo.setValor(novoSaldo);

      TransacaoModel transacao = new TransacaoModel();
      transacao.setCliente(cliente);
      transacao.setValor(valor);
      transacao.setTipo(tipo);
      transacao.setDescricao(descricao);
      transacao.setRealizadaEm(LocalDateTime.now());

      entityManager.merge(saldo);
      entityManager.merge(transacao);
      return RestResponse.ok(new TransacaoResponse(cliente.getLimite(),novoSaldo));
    } catch (Exception e) {
      e.printStackTrace();
      return RestResponse.status(500);
    }
  }

  private boolean isValid(TransacaoRequest request) {
    return request != null
        && request.getValor() != null
        && request.getValor() > 0
        && (request.getTipo().equals("c") || request.getTipo().equals("d"))
        && request.getDescricao() != null
        && !request.getDescricao().isEmpty()
        && request.getDescricao().length() <= 10
        && validaValor(request.getValor());
  }
  private boolean validaValor(Double valor) {
    return valor % 1 == 0;
  }
}
