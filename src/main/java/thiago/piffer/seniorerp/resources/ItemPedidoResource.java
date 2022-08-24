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
import thiago.piffer.seniorerp.services.ItemPedidoService;
import thiago.piffer.seniorerp.services.PedidoService;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(value = "/itemPedidos")
public class ItemPedidoResource {

    @Autowired
    private ItemPedidoService service;

    @GetMapping
    public ResponseEntity<Page<ItemPedido>> list(
            @RequestParam(value="page", defaultValue = "0") Integer page,
            @RequestParam(value="limit", defaultValue = "24") Integer limit,
            @RequestParam(value="direction", defaultValue = "DESC") String direction,
            @RequestParam(value="orderBy", defaultValue = "subTotal") String orderBy) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.Direction.valueOf(direction), orderBy);
        Page<ItemPedido> itemPedidos = service.list(pageRequest);
        return ResponseEntity.ok().body(itemPedidos);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ItemPedido> findByUuid(@PathVariable(value="id") UUID id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

}
