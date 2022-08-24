package thiago.piffer.seniorerp.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import thiago.piffer.seniorerp.ApplicationConfigTest;
import thiago.piffer.seniorerp.domain.ItemPedido;
import thiago.piffer.seniorerp.domain.Pedido;
import thiago.piffer.seniorerp.domain.Produto;
import thiago.piffer.seniorerp.domain.dto.ItemPedidoDto;
import thiago.piffer.seniorerp.repositories.ItemPedidoRepository;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ItemPedidoServiceTest extends ApplicationConfigTest {

    @MockBean
    private ItemPedidoRepository repository;

    @MockBean
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoService itemPedidoService;

    @Test
    void testarList() {
        PageRequest pageRequest = PageRequest.of(0, 24, Sort.Direction.valueOf("ASC"), "subTotal");
        itemPedidoService.list(pageRequest);
        verify(repository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void testarFindById() {
        UUID id = UUID.randomUUID();
        ItemPedido itemPedidoMock = mock(ItemPedido.class);
        when(itemPedidoMock.getId()).thenReturn(id);
        Optional<ItemPedido> itemPedidoOptional = Optional.of(itemPedidoMock);
        when(repository.findById(id)).thenReturn(itemPedidoOptional);
        ItemPedido itemPedido = itemPedidoService.findById(id);
        verify(repository, times(1)).findById(any(UUID.class));
    }

    @Test
    void testarDelete() {
        List<ItemPedido> itemPedidoList = new ArrayList<>();
        Produto produto = mock(Produto.class);
        ItemPedido itemPedido = mock(ItemPedido.class);
        itemPedidoList.add(itemPedido);
        when(produto.getItensPedido()).thenReturn(itemPedidoList);
        when(itemPedido.getId()).thenReturn(UUID.randomUUID());
        when(itemPedido.getProduto()).thenReturn(produto);
        itemPedidoService.delete(itemPedido);
        assertEquals(produto.getItensPedido(), Collections.emptyList());
        verify(repository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    void testarFromDto() {
        UUID idProduto = UUID.randomUUID();
        Produto produto = mock(Produto.class);
        when(produto.getId()).thenReturn(idProduto);
        when(produto.getAtivo()).thenReturn(Boolean.TRUE);
        when(produto.getValor()).thenReturn(BigDecimal.TEN);

        ItemPedidoDto itemPedidoDto = mock(ItemPedidoDto.class);
        when(itemPedidoDto.getId()).thenReturn(UUID.randomUUID());
        when(itemPedidoDto.getProdutoId()).thenReturn(idProduto);
        when(itemPedidoDto.getQuantidade()).thenReturn(1);

        when(produtoService.findById(any(UUID.class))).thenReturn(produto);

        ItemPedido itemPedido = itemPedidoService.fromDto(itemPedidoDto, mock(Pedido.class));
        assertEquals(itemPedidoDto.getId(), itemPedido.getId());
        assertEquals(itemPedidoDto.getProdutoId(), itemPedido.getProduto().getId());
        assertEquals(itemPedidoDto.getQuantidade(), itemPedido.getQuantidade());
        assertEquals(BigDecimal.TEN, itemPedido.getSubTotal());

    }
}