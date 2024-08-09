public abstract class Veiculo {
    private String marca;
    private String modelo;
    private int anoDeFabricacao;
    private double valorAvaliado;
    private double valorDiaria;
    private String placa;
    private boolean alugado;

    public Veiculo(String marca, String modelo, int anoDeFabricacao, double valorAvaliado, double valorDiaria, String placa) {
        this.marca = marca;
        this.modelo = modelo;
        this.anoDeFabricacao = anoDeFabricacao;
        this.valorAvaliado = valorAvaliado;
        this.valorDiaria = valorDiaria;
        this.placa = placa;
    }

    public boolean isAlugado() {
        return alugado;
    }

    public void setAlugado(boolean alugado) {
        this.alugado = alugado;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAnoDeFabricacao() {
        return anoDeFabricacao;
    }

    public double getValorAvaliado() {
        return valorAvaliado;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public String getPlaca() {
        return placa;
    }

    public void aumentarValorDiaria(double percentual) {
        this.valorDiaria += this.valorDiaria * percentual;
    }

    public void reduzirValorDiaria(double percentual) {
        this.valorDiaria -= this.valorDiaria * percentual;
    }

    public void aumentarValorAvaliado(double percentual) {
        this.valorAvaliado += this.valorAvaliado * percentual;
    }

    public void reduzirValorAvaliado(double percentual) {
        this.valorAvaliado -= this.valorAvaliado * percentual;
    }

    public abstract double calcularSeguro();
}
