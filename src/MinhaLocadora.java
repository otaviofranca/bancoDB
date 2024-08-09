import java.util.*;

public class MinhaLocadora extends Locadora {
    private List<Veiculo> veiculos = new ArrayList<>();
    private Map<String, Aluguel> alugueis = new HashMap<>();
    private Map<Integer, Cliente> clientes = new HashMap<>();
    private Map<Integer, Double> faturamentoTotal = new HashMap<>();
    private Map<Integer, Integer> quantidadeTotalDiarias = new HashMap<>();

    public MinhaLocadora() {
        faturamentoTotal.put(1, 0.0);  // Faturamento total para motos
        faturamentoTotal.put(2, 0.0);  // Faturamento total para carros
        faturamentoTotal.put(3, 0.0);  // Faturamento total para caminhões
        faturamentoTotal.put(4, 0.0);  // Faturamento total para ônibus
        faturamentoTotal.put(0, 0.0);  // Faturamento total geral
    }

    @Override
    public void inserir(Veiculo veiculo) throws VeiculoJaCadastrado, DadosInvalidos {
        if (veiculo == null) {
            throw new DadosInvalidos("Dados do veículo são inválidos.");
        }
        if (veiculos.stream().anyMatch(v -> v.getPlaca().equals(veiculo.getPlaca()))) {
            throw new VeiculoJaCadastrado("Veículo com placa " + veiculo.getPlaca() + " já está cadastrado.");
        }
        veiculos.add(veiculo);
    }

    @Override
    public void inserir(Cliente cliente) throws ClienteJaCadastrado, DadosInvalidos {
        if (cliente == null) {
            throw new DadosInvalidos("Dados do cliente são inválidos.");
        }
        if (clientes.containsKey(cliente.getCpf())) {
            throw new ClienteJaCadastrado("Cliente com CPF " + cliente.getCpf() + " já está cadastrado.");
        }
        clientes.put(cliente.getCpf(), cliente);
    }

    @Override
    public Veiculo pesquisar(String placa) throws VeiculoInexistente {
        return veiculos.stream()
                .filter(veiculo -> veiculo.getPlaca().equals(placa))
                .findFirst()
                .orElseThrow(() -> new VeiculoInexistente("Veículo com placa " + placa + " não encontrado."));
    }

    @Override
    public Cliente pesquisarCliente(int cpf) throws ClienteInexistente {
        return clientes.getOrDefault(cpf, null);
    }

    @Override
    public ArrayList<Veiculo> pesquisarMoto(int cilindrada) throws DadosInvalidos {
        return null;
    }

    @Override
    public ArrayList<Veiculo> pesquisarCarro(int tipoCarro) throws DadosInvalidos {
        return null;
    }

    @Override
    public ArrayList<Veiculo> pesquisarCaminhao(int carga) throws DadosInvalidos {
        return null;
    }

    @Override
    public ArrayList<Veiculo> pesquisarOnibus(int capacidadeMinima) {
        ArrayList<Veiculo> resultado = new ArrayList<>();

        for (Veiculo veiculo : veiculos) {
            if (veiculo instanceof Onibus) {
                Onibus onibus = (Onibus) veiculo;
                if (onibus.getCapacidadePassageiros() >= capacidadeMinima) {
                    resultado.add(onibus);
                }
            }
        }

        return resultado;
    }


    @Override
    public boolean registrarAluguel(String placa, Date dataInicial, int quantidadeDias, int cpfCliente)
            throws VeiculoInexistente, ClienteInexistente, VeiculoJaAlugado {

        // Encontrar o veículo na lista com base na placa
        Veiculo veiculo = veiculos.stream()
                .filter(v -> v.getPlaca().equals(placa))
                .findFirst()
                .orElseThrow(() -> new VeiculoInexistente("Veículo com a placa " + placa + " não encontrado."));

        // Encontrar o cliente no mapa com base no CPF
        Cliente cliente = clientes.get(cpfCliente);
        if (cliente == null) {
            throw new ClienteInexistente("Cliente com CPF " + cpfCliente + " não encontrado.");
        }

        if (veiculo.isAlugado()) {
            throw new VeiculoJaAlugado("Veículo com a placa " + placa + " já está alugado.");
        }

        // Calcular o seguro diário com base no tipo de veículo
        double valorAvaliado = veiculo.getValorAvaliado();
        double seguroDiario;
        double valorDiaria = veiculo.getValorDiaria();

        if (veiculo instanceof Moto) {
            seguroDiario = (valorAvaliado * 0.11) / 365;
        } else if (veiculo instanceof Caminhao) {
            seguroDiario = (valorAvaliado * 0.08) / 365;
        } else if (veiculo instanceof Carro) {
            seguroDiario = (valorAvaliado * 0.03) / 365;
        } else if (veiculo instanceof Onibus) {
            seguroDiario = (valorAvaliado * 0.20) / 365;
        } else {
            throw new IllegalArgumentException("Tipo de veículo desconhecido.");
        }

        // Calcular o valor total do aluguel
        double valorTotal = (valorDiaria + seguroDiario) * quantidadeDias;

        // Registrar o aluguel
        veiculo.setAlugado(true);
        Aluguel aluguel = new Aluguel(veiculo, cliente, dataInicial, quantidadeDias, valorTotal);
        alugueis.put(placa, aluguel);  // Adiciona ao mapa de aluguéis

        // Atualizar o faturamento total
        int tipoVeiculo = veiculo instanceof Moto ? 1 :
                veiculo instanceof Carro ? 2 :
                        veiculo instanceof Caminhao ? 3 : 4;
        faturamentoTotal.put(tipoVeiculo, faturamentoTotal.get(tipoVeiculo) + valorTotal);
        faturamentoTotal.put(0, faturamentoTotal.get(0) + valorTotal);  // Atualiza o faturamento total geral

        quantidadeTotalDiarias.put(tipoVeiculo, quantidadeTotalDiarias.getOrDefault(tipoVeiculo, 0) + quantidadeDias);
        quantidadeTotalDiarias.put(0, quantidadeTotalDiarias.getOrDefault(0, 0) + quantidadeDias);  // Atualiza a quantidade total geral

        return true;  // Retorna true para indicar que o aluguel foi registrado com sucesso
    }



    public boolean registrarDevolucao(String placa) throws VeiculoNaoAlugado, VeiculoInexistente {
        // Verificar se o veículo com a placa fornecida existe na locadora
        Veiculo veiculo = veiculos.stream()
                .filter(v -> v.getPlaca().equals(placa))
                .findFirst()
                .orElseThrow(() -> new VeiculoInexistente("Veículo com a placa " + placa + " não encontrado na locadora."));

        // Encontrar o aluguel com base na placa
        Aluguel aluguel = alugueis.get(placa);
        if (aluguel == null) {
            throw new VeiculoNaoAlugado("Veículo com a placa " + placa + " não está alugado.");
        }

        // Verificar se o veículo está realmente alugado
        if (!veiculo.isAlugado()) {
            throw new VeiculoNaoAlugado("Veículo com a placa " + placa + " não está alugado.");
        }

        // Marcar o veículo como não alugado
        veiculo.setAlugado(false);

        // Remove o aluguel do mapa
        alugueis.remove(placa);

        return true;
    }

    @Override
    public void depreciarVeiculos(int tipo, double taxaDepreciacao) throws DadosInvalidos {

    }


    @Override
    public double faturamentoTotal(int tipoVeiculo, Date dataInicial, Date dataFinal) {
       return faturamentoTotal.get(tipoVeiculo);
    }




    @Override
    public int quantidadeTotalDeDiarias(int tipoVeiculo, Date dataInicial, Date dataFinal) {
       return quantidadeTotalDiarias.get(tipoVeiculo);
    }



    @Override
    public double calcularAluguel(String placa, int dias) throws VeiculoInexistente {
        Veiculo veiculo = pesquisar(placa);
        if (veiculo == null) {
            throw new VeiculoInexistente("Veículo com placa " + placa + " não encontrado.");
        }
        double valorAvaliado = veiculo.getValorAvaliado();
        double seguroDiario;
        double valorDiaria = veiculo.getValorDiaria();

        if (veiculo instanceof Moto) {
            seguroDiario = (valorAvaliado * 0.11) / 365;
        } else if (veiculo instanceof Caminhao) {
            seguroDiario = (valorAvaliado * 0.08) / 365;
        } else if (veiculo instanceof Carro) {
            seguroDiario = (valorAvaliado * 0.03) / 365;
        } else if (veiculo instanceof Onibus) {
            seguroDiario = (valorAvaliado * 0.20) / 365;
        } else {
            throw new IllegalArgumentException("Tipo de veículo desconhecido.");
        }

        // Calcular o valor total do aluguel
        double valorAluguel = (valorDiaria + seguroDiario) * dias;
        return valorAluguel;
    }

    @Override
    public void aumentarDiaria(int tipo, double percentual) {
        veiculos.stream()
                .filter(veiculo -> {
                    switch (tipo) {
                        case 1: return veiculo instanceof Moto;
                        case 2: return veiculo instanceof Carro;
                        case 3: return veiculo instanceof Caminhao;
                        case 4: return veiculo instanceof Onibus;
                        case 0: return true; // Todos os tipos
                        default: throw new IllegalArgumentException("Tipo de veículo inválido: " + tipo);
                    }
                })
                .forEach(veiculo -> {
                    double valorAtual = veiculo.getValorDiaria();
                    double aumento = valorAtual * percentual;
                    veiculo.setValorDiaria(valorAtual + aumento);
                });
    }


    @Override
    public void reduzirDiariaTipo(int tipo, double percentual) {

    }

    @Override
    public void aumentarValorAvaliadoTipo(int tipo, double percentual) {

    }

    @Override
    public void reduzirValorAvaliadoTipo(int tipo, double percentual) {

    }

}
