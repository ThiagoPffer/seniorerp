package thiago.piffer.seniorerp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import thiago.piffer.seniorerp.domain.enums.Tipo;
import thiago.piffer.seniorerp.domain.util.BaseBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "ITEM_PEDIDOS")
public class ItemPedido implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column
    private UUID id;

    private BigDecimal subTotal;

    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public ItemPedido() {}

    public ItemPedido(ItemPedido itemPedido) {
        this.id = itemPedido.id;
        this.subTotal = itemPedido.subTotal;
        this.quantidade = itemPedido.quantidade;
        this.produto = itemPedido.produto;
        this.pedido = itemPedido.pedido;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    private void setSubTotal() {
        if (this.quantidade != null && this.produto != null) { this.subTotal = produto.getValor().multiply(BigDecimal.valueOf(this.quantidade)); }
    }

    @JsonIgnore
    public Boolean getDescontoAplicavel() { return Tipo.toEnum(getProduto().getTipo().getCod()).equals(Tipo.PRODUTO); }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
        setSubTotal();
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPedido that = (ItemPedido) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class Builder extends BaseBuilder<ItemPedido> {

        private Builder(ItemPedido entity) { super(entity); }
        public static Builder create() { return new Builder(new ItemPedido()); }
        public static Builder from(ItemPedido itemPedido) { return new Builder(new ItemPedido(itemPedido)); }

        public Builder id(UUID id) {
            entity.id = id;
            return this;
        }

        public Builder quantidade(Integer quantidade) {
            entity.quantidade = quantidade;
            entity.setSubTotal();
            return this;
        }

        public Builder produto(Produto produto) {
            entity.produto = produto;
            entity.setSubTotal();
            return this;
        }

        public Builder pedido(Pedido pedido) {
            entity.pedido = pedido;
            return this;
        }

        @Override
        public void beforeBuild() {}
    }
}
