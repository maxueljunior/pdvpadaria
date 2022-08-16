package pdv.model.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import pdv.model.entities.enums.VendaStatus;

@Entity
@Table(name="tb_vendas")
public class Vendas implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy = "id.vendas")
	private Set<VendaItem> items = new HashSet<>();
	
	private Double totalVenda;
	
	private Integer vendaStatus;
	
	private Instant data;
	
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Cliente cliente;
	
	public Vendas() {
	}

	public Vendas(Long id, Double totalVenda, VendaStatus vendaStatus,Instant data, Cliente cliente) {
		this.id = id;
		this.totalVenda = totalVenda;
		setVendaStatus(vendaStatus);
		this.data = data;
		this.cliente = cliente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Set<VendaItem> getItems(){
		return items;
	}
	
	public Double getTotalVenda() {
		return totalVenda;
	}

	public void setTotalVenda(Double totalVenda) {
		this.totalVenda = totalVenda;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public VendaStatus getVendaStatus() {
		return VendaStatus.valueOf(vendaStatus);
	}

	public void setVendaStatus(VendaStatus vendaStatus) {
		if(vendaStatus != null) {
		this.vendaStatus = vendaStatus.getCode();
		}
	}
	
	public Instant getData() {
		return data;
	}

	public void setData(Instant data) {
		this.data = data;
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
		Vendas other = (Vendas) obj;
		return Objects.equals(id, other.id);
	}

}
