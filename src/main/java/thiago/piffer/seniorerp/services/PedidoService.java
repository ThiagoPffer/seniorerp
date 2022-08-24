package thiago.piffer.seniorerp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thiago.piffer.seniorerp.domain.ItemPedido;
import thiago.piffer.seniorerp.domain.Pedido;
import thiago.piffer.seniorerp.domain.dto.ItemPedidoDto;
import thiago.piffer.seniorerp.domain.dto.PedidoDto;
import thiago.piffer.seniorerp.domain.enums.Situacao;
import thiago.piffer.seniorerp.exceptions.DataIntegrityException;
import thiago.piffer.seniorerp.exceptions.ObjectNotFoundException;
import thiago.piffer.seniorerp.repositories.PedidoRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ItemPedidoService itemPedidoService;

    public Page<Pedido> list(Pageable pageRequest) { return repository.findAll(pageRequest); }

    public Pedido findById(UUID id) {
        Optional<Pedido> pedido = repository.findById(id);
        return pedido.orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado! " + "["+id+"]"));
    }

    public void save(Pedido pedido) {
        if (pedido.getId() != null) { update(pedido); } else { insert(pedido); }
    }

    @Transactional
    public void insert(Pedido pedido) {
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setSituacao(Situacao.EM_ABERTO);
        repository.save(pedido);
    }

    @Transactional
    public void update(Pedido pedido) {
        Pedido pedidoSalvo = findById(pedido.getId());
        if (pedido.getSituacao() == null) { pedido.setSituacao(pedidoSalvo.getSituacao()); }
        pedido.setDataPedido(pedidoSalvo.getDataPedido());

        if (pedidoSalvo.getSituacao().equals(Situacao.FECHADO) && !pedido.getDesconto().equals(pedidoSalvo.getDesconto())) {
            throw new DataIntegrityException("Não é permitido aplicar desconto a um pedido fechado!");
        }

        pedidoSalvo.getItensPedido().forEach(item -> {
            if (item.getId() != null && !pedido.getItensPedido().contains(item)) {
                itemPedidoService.delete(item);
            }
        });

        repository.save(pedido);
    }

    public void delete(UUID id) { repository.deleteById(id); }

    public Pedido fromDto(PedidoDto pedidoDto) {
        List<ItemPedido> itemPedidoList = new ArrayList<>();
        Pedido pedido = Pedido.Builder.create()
                .id(pedidoDto.getId())
                .cliente(pedidoDto.getCliente())
                .desconto(pedidoDto.getDesconto())
                .build();

        for (ItemPedidoDto itemPedidoDto : pedidoDto.getItensPedido()) {
            ItemPedido itemPedido = itemPedidoService.fromDto(itemPedidoDto, pedido);
            itemPedidoList.add(itemPedido);
        }
        pedido.setItensPedido(itemPedidoList);
        return pedido;
    }

    public void updateSituacao(UUID id, Situacao situacao) {
        Pedido pedido = Pedido.Builder.from(findById(id)).situacao(situacao).build();
        repository.save(pedido);
    }
}
