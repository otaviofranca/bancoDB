
public class VeiculoInexistente extends Exception {
		
		public VeiculoInexistente(String placa) {
			super("Veiculo inexistente. Placa: " + placa);
		}
}
