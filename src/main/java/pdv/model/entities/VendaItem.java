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
	
	private Double totalPedido;
	private Integer qntPedido;
	
	public VendaItem() {}

	public VendaItem(Item item, Vendas venda, Double totalPedido, Integer qntPedido) {
		id.setItem(item);
		id.setVendas(venda);
		this.totalPedido = totalPedido;
		this.qntPedido = qntPedido;
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
	
	public Double getTotalPedido() {
		return totalPedido;
	}

	public void setTotalPedido(Double totalPedido) {
		this.totalPedido = totalPedido;
	}

	public Integer getQntPedido() {
		return qntPedido;
	}

	public void setQntPedido(Integer qntPedido) {
		this.qntPedido = qntPedido;
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
