package pdv.model.entities.enums;

public enum VendaStatus{
	
	AGUARDANDO_PAGAMENTO(1),
	PAGO(2),
	CANCELADO(3);

	private int code;
	
	private VendaStatus(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static VendaStatus valueOf(int code) {
		for(VendaStatus value : VendaStatus.values()) {
			if(value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Codigo invalido");
	}
}
