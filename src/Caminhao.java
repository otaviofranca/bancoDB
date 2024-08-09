public class Caminhao extends Veiculo {
    private double capacidadeCarga;

    public Caminhao(String marca, String modelo, int anoDeFabricacao, double valorAvaliado, double valorDiaria, String placa, double capacidadeCarga) {
        super(marca, modelo, anoDeFabricacao, valorAvaliado, valorDiaria, placa);
        this.capacidadeCarga = capacidadeCarga;
    }

    public double getCapacidadeCarga() {
        return capacidadeCarga;
    }

    @Override
    public double calcularSeguro() {
        return (getValorAvaliado() * 0.08) / 365;
    }
}