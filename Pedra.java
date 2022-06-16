public class Pedra extends Peca {
  public int deltaMovimento;
  public int deltaDeCaptura; // ATENÇÃO WEVERTON
  public int posicaoYPromover; // ATENÇÃO WEVERTON

  Pedra(Casa casa, Cor cor) {
    super(casa, cor);
    this.deltaDeCaptura = 2;
    if (cor == Cor.BRANCA) {
      this.deltaMovimento = 1;
      this.posicaoYPromover = 7;
    } else {
      this.posicaoYPromover = 0;
      this.deltaMovimento = -1;
    }
  }

  public boolean ehPosicaoPermitida(Casa destino,  Tabuleiro tabuleiro) {
    int posicaoXOrigem = this.casa.getPosicaoX();
    int posicaoYOrigem = this.casa.getPosicaoY();
    int posicaoXDestino = destino.getPosicaoX();
    int posicaoYDestino = destino.getPosicaoY();

    int deltaX = posicaoXDestino - posicaoXOrigem;
    int deltaY = posicaoYDestino - posicaoYOrigem;


    return Math.abs(deltaX) == Math.abs(deltaY) && deltaY == deltaMovimento;
  }

  public boolean podePromover() {
    return this.casa.getPosicaoY() == posicaoYPromover;
  }

  @Override
  void promover() {
    this.casa.removerPeca();
    Peca dama = new Dama(casa, this.cor);
    this.casa.colocarPeca(dama);
  }
}
