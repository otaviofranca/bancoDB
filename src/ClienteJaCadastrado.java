
public class ClienteJaCadastrado extends Exception {
	
	public ClienteJaCadastrado(int cpf) {
		super("Cliente ja cadastrado. CPF: " + cpf);
	}

	public ClienteJaCadastrado(String s) {
	}
}