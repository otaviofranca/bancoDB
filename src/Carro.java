public class Carro extends Veiculo {
    private int tipo;

    public Carro(String marca, String modelo, int anoDeFabricacao, double valorAvaliado, double valorDiaria, String placa, int tipo) {
        super(marca, modelo, anoDeFabricacao, valorAvaliado, valorDiaria, placa);
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }

    @Override
    public double calcularSeguro() {
        return (getValorAvaliado() * 0.03) / 365;
    }
}