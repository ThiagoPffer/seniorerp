package thiago.piffer.seniorerp.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thiago.piffer.seniorerp.domain.ItemPedido;
import thiago.piffer.seniorerp.domain.Pedido;
import thiago.piffer.seniorerp.domain.dto.ItemPedidoDto;
import thiago.piffer.seniorerp.domain.dto.PedidoDto;
import thiago.piffer.seniorerp.domain.enums.Situacao;
import thiago.piffer.seniorerp.services.ItemPedidoService;
import thiago.piffer.seniorerp.services.PedidoService;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService service;

    @GetMapping
    public ResponseEntity<Page<Pedido>> list(
            @RequestParam(value="page", defaultValue = "0") Integer page,
            @RequestParam(value="limit", defaultValue = "24") Integer limit,
            @RequestParam(value="direction", defaultValue = "ASC") String direction,
            @RequestParam(value="orderBy", defaultValue = "cliente") String orderBy) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.Direction.valueOf(direction), orderBy);
        Page<Pedido> pedidos = service.list(pageRequest);
        return ResponseEntity.ok().body(pedidos);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Pedido> findByUuid(@PathVariable(value="id") UUID id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody PedidoDto dto) {
        Pedido pedido = service.fromDto(dto);
        service.save(pedido);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(pedido.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @Valid @RequestBody PedidoDto dto) {
        service.save(Pedido.Builder.from(service.fromDto(dto)).id(id).build());
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{id}/situacoes")
    public ResponseEntity<Void> update(@PathVariable UUID id, @Valid @RequestParam Situacao situacao) {
        service.updateSituacao(id, situacao);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteByUuid(@PathVariable(value="id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
