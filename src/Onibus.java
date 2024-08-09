public class Onibus extends Veiculo {
    private int capacidadePassageiros;

    public Onibus(String marca, String modelo, int anoDeFabricacao, double valorAvaliado, double valorDiaria, String placa, int capacidadePassageiros) {
        super(marca, modelo, anoDeFabricacao, valorAvaliado, valorDiaria, placa);
        this.capacidadePassageiros = capacidadePassageiros;
    }

    public int getCapacidadePassageiros() {
        return capacidadePassageiros;
    }

    @Override
    public double calcularSeguro() {
        return (getValorAvaliado() * 0.20) / 365;
    }
}