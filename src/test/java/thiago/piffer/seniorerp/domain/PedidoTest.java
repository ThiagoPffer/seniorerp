package thiago.piffer.seniorerp.domain;

import org.junit.jupiter.api.Test;
import thiago.piffer.seniorerp.ApplicationConfigTest;
import thiago.piffer.seniorerp.domain.enums.Situacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PedidoTest extends ApplicationConfigTest {

    private final UUID ID = UUID.randomUUID();
    private final String CLIENTE = "Thiago Piffer";
    private final BigDecimal TOTAL = BigDecimal.TEN;
    private final Double DESCONTO = 0.5D;
    private final Situacao SITUACAO = Situacao.EM_ABERTO;
    private final LocalDateTime DATA_PEDIDO = LocalDateTime.now();
    private final ItemPedido ITEM_PEDIDO_PRODUTO = mock(ItemPedido.class);
    private final ItemPedido ITEM_PEDIDO_SERVICO = mock(ItemPedido.class);
    private final List<ItemPedido> LIST_ITEM_PEDIDO = new ArrayList<>(Arrays.asList(ITEM_PEDIDO_PRODUTO, ITEM_PEDIDO_SERVICO));

    Pedido getPedido() {
        return Pedido.Builder.create()
                .id(ID)
                .cliente(CLIENTE)
                .desconto(DESCONTO)
                .situacao(SITUACAO)
                .dataPedido(DATA_PEDIDO)
                .itensPedido(LIST_ITEM_PEDIDO)
                .build();
    }

    private void configurarItensPedido(){
        when(ITEM_PEDIDO_PRODUTO.getDescontoAplicavel()).thenReturn(Boolean.TRUE);
        when(ITEM_PEDIDO_PRODUTO.getSubTotal()).thenReturn(BigDecimal.TEN);
        when(ITEM_PEDIDO_SERVICO.getDescontoAplicavel()).thenReturn(Boolean.FALSE);
        when(ITEM_PEDIDO_SERVICO.getSubTotal()).thenReturn(BigDecimal.valueOf(5));
    }

    @Test
    void testarCreate() {
        configurarItensPedido();
        Pedido pedido = getPedido();
        assertEquals(ID, pedido.getId());
        assertEquals(CLIENTE, pedido.getCliente());
        assertEquals(DESCONTO, pedido.getDesconto());
        assertEquals(SITUACAO, pedido.getSituacao());
        assertEquals(DATA_PEDIDO, pedido.getDataPedido());
        assertEquals(LIST_ITEM_PEDIDO, pedido.getItensPedido());
    }

    @Test
    void testarFrom() {
        configurarItensPedido();
        UUID newID = UUID.randomUUID();
        Pedido p1 = getPedido();
        Pedido p2 = Pedido.Builder.from(p1)
                .id(newID)
                .build();
        assertEquals(newID, p2.getId());
        assertEquals(ID, p1.getId());
    }

    @Test
    void testarCalculoTotal() {
        configurarItensPedido();
        Pedido pedido = getPedido();
        assertEquals(BigDecimal.valueOf(10.0), pedido.getTotal());
    }
}