package pdv.model.entities.pk;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import pdv.model.entities.Item;
import pdv.model.entities.Vendas;

@Embeddable
public class VendaItemPK implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;
	
	@ManyToOne
	@JoinColumn(name = "venda_id")
	private Vendas vendas;
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Vendas getVendas() {
		return vendas;
	}
	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}
	@Override
	public int hashCode() {
		return Objects.hash(item, vendas);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VendaItemPK other = (VendaItemPK) obj;
		return Objects.equals(item, other.item) && Objects.equals(vendas, other.vendas);
	}
	
	
}
