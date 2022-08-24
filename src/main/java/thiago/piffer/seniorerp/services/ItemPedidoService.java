package thiago.piffer.seniorerp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import thiago.piffer.seniorerp.domain.ItemPedido;
import thiago.piffer.seniorerp.domain.Pedido;
import thiago.piffer.seniorerp.domain.dto.ItemPedidoDto;
import thiago.piffer.seniorerp.exceptions.DataIntegrityException;
import thiago.piffer.seniorerp.exceptions.ObjectNotFoundException;
import thiago.piffer.seniorerp.repositories.ItemPedidoRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository repository;

    @Autowired
    private ProdutoService produtoService;

    public Page<ItemPedido> list(Pageable pageRequest) { return repository.findAll(pageRequest); }

    public ItemPedido findById(UUID id) {
        Optional<ItemPedido> itemPedido = repository.findById(id);
        return itemPedido.orElseThrow(() -> new ObjectNotFoundException("Item de pedido não encontrado! " + "["+id+"]"));
    }

    public void delete(ItemPedido itemPedido) {
        itemPedido.getProduto().getItensPedido().remove(itemPedido);
        repository.deleteById(itemPedido.getId());
    }

    public ItemPedido fromDto(ItemPedidoDto itemPedidoDto, Pedido pedido) {
        ItemPedido itemPedido = ItemPedido.Builder.create().id(itemPedidoDto.getId()).quantidade(itemPedidoDto.getQuantidade()).build();
        var produto = produtoService.findById(itemPedidoDto.getProdutoId());
        if (!produto.getAtivo()) { throw new DataIntegrityException("Não é possível adicionar produtos inativos a um pedido"); }
        return ItemPedido.Builder.from(itemPedido).produto(produto).pedido(pedido).build();
    }
}
