
public class VeiculoJaAlugado extends Exception {
	
	public VeiculoJaAlugado(String placa) {
		super("Veiculo ja alugado. Placa: " + placa);
	}
}