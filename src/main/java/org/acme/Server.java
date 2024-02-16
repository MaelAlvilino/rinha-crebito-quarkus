package org.acme;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import org.acme.Exception.ErrorMessage;
import org.acme.dto.ExtratoResponse;
import org.acme.dto.SaldoResponse;
import org.acme.model.ClienteModel;
import org.acme.model.SaldoModel;
import org.acme.model.TransacaoModel;

@Path("/clientes")
public class Server {
    @Inject
    EntityManager entityManager;
    @GET
    @Transactional
    public Response getExtrato(@PathParam("id") Long clienteId) {
        try {
            ClienteModel cliente = entityManager.find(ClienteModel.class, clienteId);
            if (cliente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage("Cliente não encontrado"))
                    .build();
            }

            List<TransacaoModel> transacoes = entityManager.createQuery(
                    "SELECT t FROM Transacao t WHERE t.cliente.id = :clienteId ORDER BY t"
                        + ".realizadaEm DESC", TransacaoModel.class)
                .setParameter("clienteId", clienteId)
                .setMaxResults(10)
                .getResultList();

            SaldoModel saldoAtual = entityManager.createQuery(
                    "SELECT s FROM Saldos s WHERE s.cliente.id = :clienteId", SaldoModel.class)
                .setParameter("clienteId", clienteId)
                .getSingleResult();

            double saldo = (saldoAtual != null) ? saldoAtual.getValor() : 0.0;

            String dataExtrato = String.valueOf(new Date());

            ExtratoResponse extratoResponse = new ExtratoResponse();
            extratoResponse.setSaldo(new SaldoResponse(saldo, dataExtrato, cliente.getLimite()));
            extratoResponse.setUltimasTransacoes(transacoes);

            return Response.ok(extratoResponse).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorMessage("Erro ao processar a requisição: " + e.getMessage()))
                .build();
        }
    }
}
