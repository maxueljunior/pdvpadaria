package pdv.model.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tb_item")
public class Item implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;
	private String name;
	private Integer quantidade;
	private Double preco;
	
	@OneToMany(mappedBy = "id.itens")
	private Set<VendaItem> items = new HashSet<>();
	
	public Item() {}

	public Item(Integer id, String name, Integer quantidade, Double preco) {
		this.id = id;
		this.name = name;
		this.quantidade = quantidade;
		this.preco = preco;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	public Set<Vendas> getVendas(){
		Set<Vendas> set = new HashSet<>();
		for(VendaItem x : items) {
			set.add(x.getVendas());
		}
		return set;
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
		Item other = (Item) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "" + id;
	}

}
