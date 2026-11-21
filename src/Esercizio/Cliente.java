package Esercizio;

public abstract class Cliente {
	private String id; 
	
	public abstract EPatente getConducentePatente();
	public abstract int getConducenteEta();
	public abstract double getConducenteLatitudine();
	public abstract double getConducenteLongitudine();
	public Cliente(String id) {
		super();
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
