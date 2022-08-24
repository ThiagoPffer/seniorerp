package thiago.piffer.seniorerp.domain;

import org.junit.jupiter.api.Test;
import thiago.piffer.seniorerp.ApplicationConfigTest;
import thiago.piffer.seniorerp.domain.enums.Tipo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;


class ProdutoTest extends ApplicationConfigTest {

    private final UUID ID = UUID.randomUUID();
    private final String DESCRICAO = "Lava e seca";
    private final Tipo TIPO = Tipo.PRODUTO;
    private final BigDecimal VALOR = BigDecimal.TEN;
    private final Boolean ATIVO = Boolean.TRUE;
    private final ItemPedido ITEM_PEDIDO = mock(ItemPedido.class);
    private final List<ItemPedido> LIST_ITEM_PEDIDO = new ArrayList<>(Arrays.asList(ITEM_PEDIDO));

    Produto getProduto() {
        return Produto.Builder.create()
                .id(ID)
                .descricao(DESCRICAO)
                .tipo(TIPO)
                .valor(VALOR)
                .ativo(ATIVO)
                .itensPedido(LIST_ITEM_PEDIDO)
                .build();
    }

    @Test
    void testarCreate() {
        Produto produto = getProduto();
        assertEquals(ID, produto.getId());
        assertEquals(DESCRICAO, produto.getDescricao());
        assertEquals(TIPO, produto.getTipo());
        assertEquals(VALOR, produto.getValor());
        assertEquals(ATIVO, produto.getAtivo());
        assertEquals(LIST_ITEM_PEDIDO, produto.getItensPedido());
    }

    @Test
    void testarFrom() {
        UUID newID = UUID.randomUUID();
        String newDescricao = "Instalação de ar-condicionado";
        Tipo newTipo = Tipo.SERVICO;
        BigDecimal newValor = BigDecimal.ZERO;
        Produto p1 = getProduto();
        Produto p2 = Produto.Builder.from(p1)
                .id(newID)
                .descricao(newDescricao)
                .tipo(newTipo)
                .valor(newValor)
                .build();
        assertEquals(newID, p2.getId());
        assertEquals(ID, p1.getId());
        assertEquals(newDescricao, p2.getDescricao());
        assertEquals(DESCRICAO, p1.getDescricao());
        assertEquals(newValor, p2.getValor());
        assertEquals(VALOR, p1.getValor());
        assertEquals(newTipo, p2.getTipo());
        assertEquals(TIPO, p1.getTipo());
    }
}