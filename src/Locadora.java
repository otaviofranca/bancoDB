
import java.util.ArrayList;

import java.util.Date;

public abstract class Locadora {

	ArrayList<Veiculo> veiculos = new ArrayList<Veiculo>();
	ArrayList<Cliente> clientes = new ArrayList<Cliente>();
	ArrayList<Aluguel> alugueis = new ArrayList<Aluguel>();

	// A placa identifica um veículo unicamente. Nenhum dado numérico pode ser menor
	// que zero. Nenhum dado do tipo string pode ser vazio ou nulo.
	public abstract void inserir(Veiculo v) throws VeiculoJaCadastrado, DadosInvalidos;

	public void inserir(Cliente c) throws ClienteJaCadastrado, DadosInvalidos {
		Cliente cliente;
		try {
			cliente = pesquisarCliente(c.getCpf());
		} catch (ClienteInexistente e) {
			clientes.add(c);
			return;
		}
		throw new ClienteJaCadastrado(c.getCpf());
	}

	public abstract Veiculo pesquisar(String placa) throws VeiculoInexistente;

	protected Cliente pesquisarCliente(int cpf) throws ClienteInexistente, DadosInvalidos {
		for (Cliente c : clientes) {
			if (c.getCpf() == cpf) {
				return c;
			}
		}
		throw new ClienteInexistente(cpf);
	}

	// Retorna as motos com cilindrada maior ou igual a pesquisada.
	public abstract ArrayList<Veiculo> pesquisarMoto(int cilindrada) throws DadosInvalidos;

	// tipo de carro
	// 0 (todos), 1 (passeio), 2 (SUV), 3 (pickup). Qualquer outro número é
	// inválido!
	public abstract ArrayList<Veiculo> pesquisarCarro(int tipoCarro) throws DadosInvalidos;

	// Retorna os caminhões com capacidade de carga maior ou igual a pesquisada.
	public abstract ArrayList<Veiculo> pesquisarCaminhao(int carga) throws DadosInvalidos;

	// Retorna os ônibus com capacidade de passageiros maior ou igual a pesquisada.
	public abstract ArrayList<Veiculo> pesquisarOnibus(int passageiros) throws DadosInvalidos;

	// Seguro Moto = (valor do bem * 11%)/365
	// Seguro Carro = (valor do bem * 3%)/365
	// Seguro Caminhão = (valor do bem * 8%)/365
	// Seguro Ônibus = (valor do bem * 20%)/365
	// Aluguel = (valor da diária + seguro) * quantidade de dias
	public abstract double calcularAluguel(String placa, int dias) throws VeiculoInexistente, DadosInvalidos;

	// Retorna exceção se veiculo não existir ou se estiver alugado.
	// Deve registrar que o veículo está alugado. Não é permitido usar datas menores
	// que a atual.
	public abstract boolean registrarAluguel(String placa, Date data, int dias, int cpf)
			throws VeiculoInexistente, ClienteInexistente, DadosInvalidos, VeiculoJaAlugado;

	// Retorna exceção se veiculo não existir ou se não estiver alugado.
	// Deve registrar a devolução do veiculo e permitir nova locação.
	public abstract boolean registrarDevolucao(String placa)
			throws VeiculoInexistente, VeiculoNaoAlugado, DadosInvalidos;

	// Tipo de veiculo a ser usado:
	// 0 (todos), 1 (moto), 2 (carro), 3 (caminhão), 4 (ônibus)
	// Não pode deixar ter taxa de depreciação negativa. Ela sempre será positiva e
	// será subtraído do valor do veiculo.
	public abstract void depreciarVeiculos(int tipo, double taxaDepreciacao) throws DadosInvalidos;

	// Tipo de veiculo a ser usado:
	// 0 (todos), 1 (moto), 2 (carro), 3 (caminhão), 4 (ônibus)
	// Não pode deixar ter taxa de aumento negativa. Ela sempre será positiva e será
	// acrescida ao valor da diária.
	public abstract void aumentarDiaria(int tipo, double taxaAumento) throws DadosInvalidos;

	// Retorna o valor total de faturamento para um tipo de veículo, durante um
	// período.
	// Os alugueis devem começar e terminar dentro do período.
	public abstract double faturamentoTotal(int tipo, Date inicio, Date fim) throws DadosInvalidos;

	// Retorna a quantidade total de diárias de aluguel para um tipo de veículo,
	// durante um período.
	// Os alugueis devem começar e terminar dentro do período.
	public abstract int quantidadeTotalDeDiarias(int tipo, Date inicio, Date fim) throws DadosInvalidos;

	public abstract void reduzirDiariaTipo(int tipo, double percentual);

	public abstract void aumentarValorAvaliadoTipo(int tipo, double percentual);

	public abstract void reduzirValorAvaliadoTipo(int tipo, double percentual);
}
