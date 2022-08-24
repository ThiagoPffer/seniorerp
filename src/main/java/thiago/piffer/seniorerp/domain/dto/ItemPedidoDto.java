package thiago.piffer.seniorerp.domain.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

public class ItemPedidoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;

    @NotNull(message = "É obrigatório informar a quantidade")
    @Min(value = 1, message = "Quantidade deve ser maior que 0")
    private Integer quantidade;

    @NotNull(message = "É obrigatório informar o produto")
    private UUID produtoId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public UUID getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(UUID produtoId) {
        this.produtoId = produtoId;
    }
}
