
import java.util.Date;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class TesteLocadoraResumido {

    @Test
    public void testeInserirVeiculo() throws VeiculoJaCadastrado, DadosInvalidos, VeiculoInexistente {
        MinhaLocadora locadora = new MinhaLocadora();
        Veiculo carro1 = new Carro("Ford", "F-1000", 1980, 10000, 50, "LVF-1000", 3);
        locadora.inserir(carro1);
        try {
            locadora.inserir(carro1);
            fail("Não era para ter adicionado veículo já cadastrado");
        } catch(VeiculoJaCadastrado e) {
            // ok, era para ter evitado!
        }
        Veiculo recuperado = locadora.pesquisar("LVF-1000");
        assertEquals("Ford", recuperado.getMarca());
        assertEquals("F-1000", recuperado.getModelo());
        assertEquals(1980, recuperado.getAnoDeFabricacao());
        assertEquals(10000, recuperado.getValorAvaliado(), 0.0001);
        assertEquals(50, recuperado.getValorDiaria(), 0.001);
        assertEquals(3, ((Carro) recuperado).getTipo());
    }

    @Test
    public void testeInserirCliente() throws DadosInvalidos, ClienteJaCadastrado, ClienteInexistente {
        MinhaLocadora locadora = new MinhaLocadora();
        Cliente cli1 = new Cliente(1234, "Zé Carlos");
        locadora.inserir(cli1);

        try {
            locadora.inserir(cli1);
            fail("Não era para inserir novamente cliente já cadastrado");
        } catch (ClienteJaCadastrado e) {
            // ok, era para ter dado erro!
        }

        Cliente cli2 = locadora.pesquisarCliente(1234);
        assertEquals("Zé Carlos", cli2.getNome());
    }

    @Test
    public void testePesquisarVeiculo() throws VeiculoJaCadastrado, DadosInvalidos, VeiculoInexistente {
        MinhaLocadora locadora = new MinhaLocadora();
        Veiculo carro1 = new Carro("Ford", "F-1000", 1980, 10000, 50, "LVF-1000", 3);
        Veiculo carro2 = new Carro("Ford", "KA", 2010, 30000, 100, "LVF-3000", 1);

        locadora.inserir(carro1);
        locadora.inserir(carro2);
        Veiculo pesquisa = locadora.pesquisar("LVF-3000");
        // Teste para saber se a pesquisa deu certo
        assertEquals("KA", pesquisa.getModelo());
        try {
            Veiculo pesquisa2 = locadora.pesquisar("LVF-1111");
            fail("Veículo inexistente");
        } catch(VeiculoInexistente e) {
            // ok, era para ter dado erro!
        }
    }

    @Test
    public void testePesquisarOnibus() throws VeiculoJaCadastrado, DadosInvalidos {
        MinhaLocadora locadora = new MinhaLocadora();
        Veiculo onibus1 = new Onibus("Estrela", "Aldebarã", 1975, 30000, 60, "X-911", 49);
        Veiculo onibus2 = new Onibus("Joca Motores", "Kall'anggo", 1978, 40000, 70, "Q-123", 50);
        Veiculo onibus3 = new Onibus("Cálcio Motores", "Bicusp", 1985, 50000, 85, "W-321", 70);

        locadora.inserir(onibus1);
        locadora.inserir(onibus2);
        locadora.inserir(onibus3);

        ArrayList<Veiculo> onibus50p = locadora.pesquisarOnibus(50);

        // Confirmando numero de onibus com 50 passageiros
        assertEquals(2, onibus50p.size());
    }

    @Test
    public void testeCalcularAluguel() throws VeiculoJaCadastrado, DadosInvalidos, VeiculoInexistente{
        MinhaLocadora locadora = new MinhaLocadora();
        Veiculo moto1 = new Moto("Estrela", "Andromeda", 1975, 15000, 40, "X-911", 50);
        Veiculo carro1 = new Carro("Estrela", "Antares", 1980, 20000, 50, "A-100", 1);
        Veiculo caminhao1 = new Caminhao("Estrela", "Betelgeuse", 1975, 30000, 70, "S-123", 200);
        Veiculo onibus1 = new Onibus("Estrela", "Aldebarã", 1975, 30000, 60, "I-412", 50);
        locadora.inserir(moto1);
        locadora.inserir(carro1);
        locadora.inserir(caminhao1);
        locadora.inserir(onibus1);

        double aluguelMoto = locadora.calcularAluguel("X-911", 5);
        double aluguelCarro = locadora.calcularAluguel("A-100", 5);
        double aluguelCaminhao = locadora.calcularAluguel("S-123", 5);
        double aluguelOnibus = locadora.calcularAluguel("I-412", 5);

        // Confirmando valor do aluguel da moto: (40(diaria) + 4.52(seguro diario)) * 5 dias = 222.6
        assertEquals(222.6, aluguelMoto, 0.01);
        // Confirmando valor do aluguel do carro: (50(diária) + 1.64(seguro diario)) * 5 dias = 258.22
        assertEquals(258.22, aluguelCarro, 0.01);
        // Confirmando valor do aluguel do caminhao: (70(diaria) + 6.58(seguro diario)) * 5 dias = 382.88
        assertEquals(382.88, aluguelCaminhao, 0.01);
        // Confirmando valor do aluguel do onibus: (60(diaria) + 16.44(seguro diario)) * 5 dias = 382.19
        assertEquals(382.19, aluguelOnibus, 0.01);

        try {
            // Testando calcular aluguel para placa inexistente
            locadora.calcularAluguel("X-999", 10);
            fail("Não existe veículo com a placa informada");
        } catch(VeiculoInexistente e) {
            // ok, era para ter dado erro!
        }
    }

    @Test
    public void testeRegistrarAluguel() throws VeiculoJaCadastrado, DadosInvalidos, VeiculoInexistente, ClienteInexistente, VeiculoJaAlugado, ClienteJaCadastrado {
        MinhaLocadora locadora = new MinhaLocadora();
        Veiculo carro1 = new Carro("Estrela", "Antares", 1980, 20000, 50, "A-100", 1);
        Cliente cli1 = new Cliente(1234, "Zé Carlos");
        locadora.inserir(carro1);
        locadora.inserir(cli1);

        Date hoje = new Date();
        locadora.registrarAluguel("A-100", hoje, 5, 1234);

        try {
            // Registrar aluguel de veiculo já registrado
            locadora.registrarAluguel("A-100", hoje, 5, 1234);
            fail("Tentou registrar aluguel de veículo já alugado");
        } catch(VeiculoJaAlugado e) {
            // ok, era para ter dado erro!
        }

        try {
            // Registrar aluguel de veiculo inexistente
            locadora.registrarAluguel("A-111", hoje, 5, 1234);
            fail("Tentou registrar aluguel de veículo inexistente");
        } catch(VeiculoInexistente e) {
            // ok, era para ter dado erro!
        }

        try {
            // Registrar aluguel de cliente inexistente
            locadora.registrarAluguel("A-100", hoje, 5, 1111);
            fail("Tentou registrar aluguel para cliente inexistente");
        } catch(ClienteInexistente e) {
            // ok, era para ter dado erro!
        }
    }

    @Test
    public void testeRegistrarDevolucao() throws VeiculoJaCadastrado, DadosInvalidos, ClienteJaCadastrado, VeiculoInexistente, ClienteInexistente, VeiculoJaAlugado, VeiculoNaoAlugado {
        MinhaLocadora locadora = new MinhaLocadora();
        Veiculo carro1 = new Carro("Estrela", "Antares", 1980, 20000, 50, "A-100", 1);
        Cliente cli1 = new Cliente(1234, "Zé Carlos");
        locadora.inserir(carro1);
        locadora.inserir(cli1);

        Date hoje = new Date();
        assertTrue(locadora.registrarAluguel("A-100", hoje, 5, 1234));
        assertTrue(locadora.registrarDevolucao("A-100"));

        try {
            // Tentar devolução de veiculo não alugado
            locadora.registrarDevolucao("A-100");
            fail("Registra devolução de veículo que já foi devolvido");
        } catch(VeiculoNaoAlugado e) {
            // ok, era para ter dado erro!
        }

        try {
            // Tentar devolução de veiculo de veiculo não existente
            locadora.registrarDevolucao("A-101");
            fail("Devolução de veículo que não existe no sistema");
        } catch(VeiculoInexistente e) {
            // ok, era para ter dado erro!
        }
    }

    @Test
    public void testeAumentarDiaria() throws VeiculoJaCadastrado, DadosInvalidos, VeiculoInexistente {
        MinhaLocadora locadora = new MinhaLocadora();
        Veiculo moto1 = new Moto("Estrela", "Andromeda", 1975, 15000, 40, "X-911", 50);
        Veiculo carro1 = new Carro("Estrela", "Antares", 1980, 20000, 50, "A-100", 1);
        Veiculo caminhao1 = new Caminhao("Estrela", "Betelgeuse", 1975, 30000, 70, "S-123", 200);
        Veiculo onibus1 = new Onibus("Estrela", "Aldebarã", 1975, 30000, 60, "I-412", 50);
        locadora.inserir(moto1);
        locadora.inserir(carro1);
        locadora.inserir(caminhao1);
        locadora.inserir(onibus1);

        locadora.aumentarDiaria(1, 0.1);	// Aumentando diária de motos em 10%
        locadora.aumentarDiaria(2, 0.2);	// Aumentando diária de carros em 20%
        locadora.aumentarDiaria(3, 0.05);	// Aumentando diária de caminhões em 5%
        locadora.aumentarDiaria(4, 0.15);	// Aumentando diária de ônibus em 15%

        assertEquals(44, locadora.pesquisar("X-911").getValorDiaria(), 0.01);
        assertEquals(60, locadora.pesquisar("A-100").getValorDiaria(), 0.01);
        assertEquals(73.5, locadora.pesquisar("S-123").getValorDiaria(), 0.01);
        assertEquals(69, locadora.pesquisar("I-412").getValorDiaria(), 0.01);

        locadora.aumentarDiaria(0, 0.1);	// Aumentando diária de todos veículos em 10%

        assertEquals(48.4, locadora.pesquisar("X-911").getValorDiaria(), 0.01);
        assertEquals(66, locadora.pesquisar("A-100").getValorDiaria(), 0.01);
    }

    @Test
    public void testeFaturamentoTotal() throws VeiculoJaCadastrado, DadosInvalidos, VeiculoInexistente, VeiculoNaoAlugado, ClienteInexistente, VeiculoJaAlugado, ClienteJaCadastrado  {
        MinhaLocadora locadora = new MinhaLocadora();
        Veiculo moto1 = new Moto("Estrela", "Andromeda", 1975, 15000, 40, "X-911", 50);
        Veiculo carro1 = new Carro("Estrela", "Antares", 1980, 20000, 50, "A-100", 1);
        Veiculo caminhao1 = new Caminhao("Estrela", "Betelgeuse", 1975, 30000, 70, "S-123", 200);
        Veiculo onibus1 = new Onibus("Estrela", "Aldebarã", 1975, 30000, 60, "I-412", 50);
        locadora.inserir(moto1);
        locadora.inserir(carro1);
        locadora.inserir(caminhao1);
        locadora.inserir(onibus1);

        Cliente cli1 = new Cliente(1234, "Zé Carlos");
        locadora.inserir(cli1);
        Date hoje = new Date();
        Date ontem = new Date(hoje.getTime() - 1);
        Date amanha = new Date(hoje.getTime() + 1);

        locadora.registrarAluguel("X-911", hoje, 5, 1234);	// Valor do aluguel = 222.6  (moto)
        locadora.registrarDevolucao("X-911");
        locadora.registrarAluguel("A-100", hoje, 5, 1234);	// Valor do aluguel = 258.22 (carro)
        locadora.registrarDevolucao("A-100");
        locadora.registrarAluguel("S-123", hoje, 5, 1234);	// Valor do aluguel = 382.88 (caminhão)
        locadora.registrarDevolucao("S-123");
        locadora.registrarAluguel("I-412", hoje, 5, 1234);	// Valor do aluguel = 382.19 (ônibus)
        locadora.registrarDevolucao("I-412");

        assertEquals(222.6, locadora.faturamentoTotal(1, ontem, amanha), 0.01);	// Faturamento total de motos
        assertEquals(258.22, locadora.faturamentoTotal(2, ontem, amanha), 0.01);	// Faturamento total de carros
        assertEquals(382.88, locadora.faturamentoTotal(3, ontem, amanha), 0.01);	// Faturamento total de caminhões
        assertEquals(382.19, locadora.faturamentoTotal(4, ontem, amanha), 0.01);	// Faturamento total de ônibus
        assertEquals(1245.89, locadora.faturamentoTotal(0, ontem, amanha), 0.01);	// Faturamento total
    }

    @Test
    public void testeQuantidadeTotalDeDiarias() throws VeiculoJaCadastrado, DadosInvalidos, ClienteJaCadastrado, VeiculoInexistente, ClienteInexistente, VeiculoJaAlugado, VeiculoNaoAlugado {
        MinhaLocadora locadora = new MinhaLocadora();
        Veiculo moto1 = new Moto("Estrela", "Andromeda", 1975, 15000, 40, "X-911", 50);
        Veiculo carro1 = new Carro("Estrela", "Antares", 1980, 20000, 50, "A-100", 1);
        Veiculo caminhao1 = new Caminhao("Estrela", "Betelgeuse", 1975, 30000, 70, "S-123", 200);
        Veiculo onibus1 = new Onibus("Estrela", "Aldebarã", 1975, 30000, 60, "I-412", 50);
        locadora.inserir(moto1);
        locadora.inserir(carro1);
        locadora.inserir(caminhao1);
        locadora.inserir(onibus1);

        Cliente cli1 = new Cliente(1234, "Zé Carlos");
        locadora.inserir(cli1);

        Date hoje = new Date();
        Date ontem = new Date(hoje.getTime() - 1);
        Date amanha = new Date(hoje.getTime() + 1);

        locadora.registrarAluguel("X-911", hoje, 5, 1234);	// 5 diárias de moto
        locadora.registrarAluguel("A-100", hoje, 10, 1234);	// 10 diárias de carro
        locadora.registrarAluguel("S-123", hoje, 7, 1234);	// 7 diárias de caminhão
        locadora.registrarAluguel("I-412", hoje, 2, 1234);	// 2 diárias de ônibus
        locadora.registrarDevolucao("X-911");
        locadora.registrarDevolucao("A-100");
        locadora.registrarDevolucao("S-123");
        locadora.registrarDevolucao("I-412");

        assertEquals(5, locadora.quantidadeTotalDeDiarias(1, ontem, amanha));	// Quantidade de diárias de moto
        assertEquals(10, locadora.quantidadeTotalDeDiarias(2, ontem, amanha));	// Quantidade de diárias de carro
        assertEquals(7, locadora.quantidadeTotalDeDiarias(3, ontem, amanha));	// Quantidade de diárias de caminhão
        assertEquals(2, locadora.quantidadeTotalDeDiarias(4, ontem, amanha));	// Quantidade de diárias de ônibus
        assertEquals(24, locadora.quantidadeTotalDeDiarias(0, ontem, amanha));	// Quantidade de diárias total
    }

}
