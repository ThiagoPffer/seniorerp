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
import thiago.piffer.seniorerp.domain.dto.PedidoDto;
import thiago.piffer.seniorerp.domain.enums.Situacao;
import thiago.piffer.seniorerp.domain.enums.Tipo;
import thiago.piffer.seniorerp.repositories.PedidoRepository;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class PedidoServiceTest extends ApplicationConfigTest {

    @MockBean
    private PedidoRepository repository;

    @MockBean
    private ItemPedidoService itemPedidoService;

    @Autowired
    private PedidoService pedidoService;

    private void configurarRepository(UUID id) {
        Pedido pedidoMock = mock(Pedido.class);
        when(pedidoMock.getId()).thenReturn(id);
        Optional<Pedido> pedidoOptional = Optional.of(pedidoMock);
        when(repository.findById(id)).thenReturn(pedidoOptional);
    }

    private Pedido configurarRepositoryUpdate(UUID id) {
        Pedido pedidoMock = mock(Pedido.class);
        when(pedidoMock.getId()).thenReturn(id);
        when(pedidoMock.getSituacao()).thenReturn(Situacao.EM_ABERTO);
        when(pedidoMock.getItensPedido()).thenReturn(Collections.singletonList(mock(ItemPedido.class)));
        Optional<Pedido> pedidoOptional = Optional.of(pedidoMock);
        when(repository.findById(id)).thenReturn(pedidoOptional);
        return pedidoMock;
    }

    @Test
    void testarList() {
        PageRequest pageRequest = PageRequest.of(0, 24, Sort.Direction.valueOf("ASC"), "cliente");
        pedidoService.list(pageRequest);
        verify(repository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void testarFindById() {
        UUID id = UUID.randomUUID();
        configurarRepository(id);
        pedidoService.findById(id);
        verify(repository, times(1)).findById(any(UUID.class));
    }

    @Test
    void testarSave() {
        pedidoService.save(mock(Pedido.class));
        verify(repository, times(1)).save(any(Pedido.class));
    }

    @Test
    void testarInsert() {
        pedidoService.insert(mock(Pedido.class));
        verify(repository, times(1)).save(any(Pedido.class));
    }

    @Test
    void testarUpdate() {
        UUID id = UUID.randomUUID();
        Pedido pedidoMock = configurarRepositoryUpdate(id);
        pedidoService.update(pedidoMock);
        verify(repository, times(1)).save(any(Pedido.class));
    }

    @Test
    void testarDelete() {
        pedidoService.delete(UUID.randomUUID());
        verify(repository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    void testarFromDto() {
        ItemPedido itemPedido = mock(ItemPedido.class);
        Produto produto = mock(Produto.class);
        PedidoDto pedidoDto = mock(PedidoDto.class);

        when(pedidoDto.getId()).thenReturn(UUID.randomUUID());
        when(pedidoDto.getCliente()).thenReturn("Thiago Piffer");
        when(pedidoDto.getDesconto()).thenReturn(0.5);
        when(pedidoDto.getItensPedido()).thenReturn(Collections.singletonList(mock(ItemPedidoDto.class)));
        when(itemPedido.getProduto()).thenReturn(mock(Produto.class));
        when(itemPedido.getSubTotal()).thenReturn(BigDecimal.TEN);
        when(itemPedido.getDescontoAplicavel()).thenReturn(Boolean.TRUE);
        when(itemPedidoService.fromDto(any(ItemPedidoDto.class), any(Pedido.class))).thenReturn(itemPedido);

        Pedido pedido = pedidoService.fromDto(pedidoDto);
        assertEquals(pedidoDto.getId(), pedido.getId());
        assertEquals(pedidoDto.getCliente(), pedido.getCliente());
        assertEquals(pedidoDto.getDesconto(), pedido.getDesconto());
        assertEquals(itemPedido, pedido.getItensPedido().get(0));
    }

    @Test
    void updateSituacao() {
        UUID id = UUID.randomUUID();
        configurarRepository(id);
        pedidoService.updateSituacao(id, Situacao.FECHADO);
        verify(repository, times(1)).save(any(Pedido.class));
    }
}