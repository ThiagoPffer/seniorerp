package thiago.piffer.seniorerp.domain;

import org.junit.jupiter.api.Test;
import thiago.piffer.seniorerp.ApplicationConfigTest;
import thiago.piffer.seniorerp.domain.enums.Tipo;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ItemPedidoTest extends ApplicationConfigTest {
    private final UUID ID = UUID.randomUUID();
    private final BigDecimal SUB_TOTAL = BigDecimal.TEN;
    private final Integer QUANTIDADE = 2;
    private final Produto PRODUTO = mock(Produto.class);
    private final Pedido PEDIDO = mock(Pedido.class);

    ItemPedido getItemPedido() {
        configurarProduto();
        return ItemPedido.Builder.create()
                .id(ID)
                .quantidade(QUANTIDADE)
                .produto(PRODUTO)
                .pedido(PEDIDO)
                .build();
    }

    private void configurarProduto(){
        when(PRODUTO.getValor()).thenReturn(BigDecimal.TEN);
        when(PRODUTO.getTipo()).thenReturn(Tipo.PRODUTO);
        when(PRODUTO.getAtivo()).thenReturn(Boolean.TRUE);
    }

    @Test
    void testarCreate() {
        ItemPedido itemPedido = getItemPedido();
        assertEquals(ID, itemPedido.getId());
        assertEquals(QUANTIDADE, itemPedido.getQuantidade());
        assertEquals(PRODUTO, itemPedido.getProduto());
        assertEquals(PEDIDO, itemPedido.getPedido());
    }

    @Test
    void testarFrom() {
        UUID newID = UUID.randomUUID();
        ItemPedido ip1 = getItemPedido();
        ItemPedido ip2 = ItemPedido.Builder.from(ip1)
                .id(newID)
                .build();
        assertEquals(newID, ip2.getId());
        assertEquals(ID, ip1.getId());
    }

    @Test
    void testarDescontoAplicavel() {
        ItemPedido itemPedido = getItemPedido();
        assertEquals(Boolean.TRUE, itemPedido.getDescontoAplicavel());
        when(PRODUTO.getTipo()).thenReturn(Tipo.SERVICO);
        assertEquals(Boolean.FALSE, itemPedido.getDescontoAplicavel());
    }

    @Test
    void testarSubTotal() {
        ItemPedido itemPedido = getItemPedido();
        assertEquals(BigDecimal.valueOf(20), itemPedido.getSubTotal());
    }
}