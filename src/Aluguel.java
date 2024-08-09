import java.util.Date;

public class Aluguel {
    private Veiculo veiculo;
    private Cliente cliente;
    private Date data;
    private int dias;
    private double valorTotal;

    public Aluguel(Veiculo veiculo, Cliente cliente, Date dataInicial, int quantidadeDias, double valorTotal) {
        this.veiculo = veiculo;
        this.cliente = cliente;
        this.data = dataInicial;
        this.dias = quantidadeDias;
        this.valorTotal = valorTotal;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public Date getData() {
        return data;
    }

    public int getDias() {
        return dias;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Date getDataDevolucao() {
        return getDataDevolucao();
    }

    public Date getDataInicial() {
        return data;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public long getQuantidadeDias() {
        return dias;
    }
}
