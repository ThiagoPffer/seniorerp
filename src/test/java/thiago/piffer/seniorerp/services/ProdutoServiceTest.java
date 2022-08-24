package thiago.piffer.seniorerp.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import thiago.piffer.seniorerp.ApplicationConfigTest;
import thiago.piffer.seniorerp.domain.Pedido;
import thiago.piffer.seniorerp.domain.Produto;
import thiago.piffer.seniorerp.repositories.ProdutoRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ProdutoServiceTest extends ApplicationConfigTest {

    @MockBean
    private ProdutoRepository repository;

    @Autowired
    private ProdutoService produtoService;

    private Produto configurarRepository(UUID id) {
        Produto produtoMock = mock(Produto.class);
        when(produtoMock.getId()).thenReturn(id);
        Optional<Produto> produtoOptional = Optional.of(produtoMock);
        when(repository.findById(id)).thenReturn(produtoOptional);
        return produtoMock;
    }

    @Test
    void testarList() {
        PageRequest pageRequest = PageRequest.of(0, 24, Sort.Direction.valueOf("ASC"), "descricao");
        produtoService.list(pageRequest);
        verify(repository, times(1)).findProdutosByAtivoTrue(any(PageRequest.class));
    }

    @Test
    void testarFindById() {
        UUID id = UUID.randomUUID();
        configurarRepository(id);
        produtoService.findById(id);
        verify(repository, times(1)).findById(any(UUID.class));
    }

    @Test
    void testarSave() {
        produtoService.save(mock(Produto.class));
        verify(repository, times(1)).save(any(Produto.class));
    }

    @Test
    void testarInsert() {
        produtoService.insert(mock(Produto.class));
        verify(repository, times(1)).save(any(Produto.class));
    }

    @Test
    void update() {
        UUID id = UUID.randomUUID();
        Produto produtoMock = configurarRepository(id);
        produtoService.update(produtoMock);
        verify(repository, times(1)).findById(any(UUID.class));
    }

    @Test
    void delete() {
        produtoService.delete(UUID.randomUUID());
        verify(repository, times(1)).deleteById(any(UUID.class));
    }
}