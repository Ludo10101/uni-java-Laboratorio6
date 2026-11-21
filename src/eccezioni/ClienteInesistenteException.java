package eccezioni;
public class ClienteInesistenteException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String m;
	public ClienteInesistenteException(String msg,String m) {
		super(msg);
		this.m=m;
	}
	public String getM() {
		return m;
	}
	public void setM(String m) {
		this.m = m;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
