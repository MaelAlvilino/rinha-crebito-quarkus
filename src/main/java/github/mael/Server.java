package github.mael;

import github.mael.dto.SaldoResponse;
import github.mael.dto.TransacaoRequest;
import github.mael.dto.TransacaoResponse;
import github.mael.model.ClienteModel;
import github.mael.model.TransacaoModel;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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

            double saldo = (saldoAtual != null) ? saldoAtual.getValor() : 0.0;

            String dataExtrato = String.valueOf(new Date());

            ExtratoResponse extratoResponse = new ExtratoResponse();
            extratoResponse.setSaldo(new SaldoResponse(saldo, dataExtrato, cliente.getLimite()));
            extratoResponse.setUltimasTransacoes(transacoes);
            return RestResponse.ok(extratoResponse);
        } catch (Exception e) {
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
            Integer valor = request.getValor();
            String tipo = request.getTipo();
            String descricao = request.getDescricao();

            ClienteModel cliente = entityManager.find(ClienteModel.class, id);
            if (cliente == null) {
                return RestResponse.status(404);
            }

            if (!isValid(request)) {
                return RestResponse.status(404);
            }

            TypedQuery<SaldoModel> saldoQuery = entityManager.createQuery("SELECT s FROM SaldoModel s WHERE s.cliente = :cliente", SaldoModel.class);
            saldoQuery.setParameter("cliente", cliente);
            SaldoModel saldo = saldoQuery.getSingleResult();

            Integer novoSaldo = saldo.getValor() + (tipo.equals("c") ? valor : -valor);
            if (novoSaldo < -cliente.getLimite()) {
                return RestResponse.status(422);
            }

            saldo.setValor(novoSaldo);
            entityManager.merge(saldo);

            TransacaoModel transacao = new TransacaoModel();
            transacao.setCliente(cliente);
            transacao.setValor(valor);
            transacao.setTipo(tipo);
            transacao.setDescricao(descricao);
            transacao.setRealizadaEm(LocalDateTime.now());
            entityManager.merge(transacao);
            return RestResponse.ok(new TransacaoResponse(cliente.getLimite(), novoSaldo));
        } catch (Exception e) {
            System.out.println(e);
            return RestResponse.status(500);
        }
    }

    private boolean isValid(TransacaoRequest request) {
        return request != null
            && request.getValor() != null
            && request.getValor() > 0
            && (request.getTipo().equals("c") || request.getTipo().equals("d"))
            && request.getDescricao() != null
            && !request.getDescricao().isEmpty();
    }
}
