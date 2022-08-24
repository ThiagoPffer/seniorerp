package thiago.piffer.seniorerp.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thiago.piffer.seniorerp.domain.Produto;
import thiago.piffer.seniorerp.services.ProdutoService;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService service;

    @GetMapping
    public ResponseEntity<Page<Produto>> list(
            @RequestParam(value="page", defaultValue = "0") Integer page,
            @RequestParam(value="limit", defaultValue = "24") Integer limit,
            @RequestParam(value="direction", defaultValue = "ASC") String direction,
            @RequestParam(value="orderBy", defaultValue = "descricao") String orderBy) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.Direction.valueOf(direction), orderBy);
        Page<Produto> produtos = service.list(pageRequest);
        return ResponseEntity.ok().body(produtos);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Produto> findByUuid(@PathVariable(value="id") UUID id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody Produto produto) {
        service.save(produto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @Valid @RequestBody Produto produto) {
        service.save(Produto.Builder.from(produto).id(id).build());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteByUuid(@PathVariable(value="id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
