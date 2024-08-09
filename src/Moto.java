public class Moto extends Veiculo {
    private int cilindrada;

    public Moto(String marca, String modelo, int anoDeFabricacao, double valorAvaliado, double valorDiaria, String placa, int cilindrada) {
        super(marca, modelo, anoDeFabricacao, valorAvaliado, valorDiaria, placa);
        this.cilindrada = cilindrada;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    @Override
    public double calcularSeguro() {
        return (getValorAvaliado() * 0.11) / 365;
    }
}