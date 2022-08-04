package pdv.model.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import pdv.model.entities.pk.VendaItemPK;

@Entity
@Table(name = "tb_venda_item")
public class VendaItem implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private VendaItemPK id = new VendaItemPK();
	
	private Double total;
	private String descricao;
	private Integer qntPedido;
	private Double preco;
	
	public VendaItem() {}

	public VendaItem(Item item, Vendas venda, Double total, String descricao, Integer qntPedido, Double preco) {
		id.setItem(item);
		id.setVendas(venda);
		this.total = total;
		this.qntPedido = qntPedido;
		this.preco = preco;
		this.descricao = descricao;
	}
	
	public Item getItem() {
		return id.getItem();
	}
	
	public void setItem(Item item) {
		id.setItem(item);
	}
	
	public Vendas getVendas() {
		return id.getVendas();
	}
	
	public void setVendas(Vendas venda) {
		id.setVendas(venda);
	}
	
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Integer getQntPedido() {
		return qntPedido;
	}

	public void setQntPedido(Integer qntPedido) {
		this.qntPedido = qntPedido;
	}
	
	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VendaItem other = (VendaItem) obj;
		return Objects.equals(id, other.id);
	}

	
	
}
