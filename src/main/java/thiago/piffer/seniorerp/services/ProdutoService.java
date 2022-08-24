package thiago.piffer.seniorerp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thiago.piffer.seniorerp.domain.Produto;
import thiago.piffer.seniorerp.exceptions.ObjectNotFoundException;
import thiago.piffer.seniorerp.repositories.ProdutoRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public Page<Produto> list(Pageable pageRequest) {
        return repository.findProdutosByAtivoTrue(pageRequest);
    }

    public Produto findById(UUID id) {
        Optional<Produto> produto = repository.findById(id);
        return produto.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado! " + "["+id+"]"));
    }

    public void save(Produto produto) {
        if (produto.getId() != null) { update(produto); } else { insert(produto); };
    }

    @Transactional
    public void insert(Produto produto) { repository.save(produto); }

    @Transactional
    public void update(Produto produto) {
        findById(produto.getId());
        repository.save(produto);
    }

    public void delete(UUID id) throws DataIntegrityViolationException{
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            throw new DataIntegrityViolationException("Não é possível excluir um produto já associado a um pedido!");
        }
    }

}
