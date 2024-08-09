
public class DadosInvalidos extends Exception {
	
	public DadosInvalidos(String s) {
		super("Dados inválidos. Números não podem ser negativos e strings não podem ser nulas ou vazias.");
	}
}
