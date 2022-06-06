public class PecaPreta extends Peca {
  PecaPreta (Casa casa, int tipo) {
  super(casa, tipo);
  }

  public boolean ehPosicaoPermitida(Casa destino, Tabuleiro tabuleiro) {
    int posicaoXOrigem = this.casa.getPosicaoX();
    int posicaoYOrigem = this.casa.getPosicaoY();
    int posicaoXDestino = destino.getPosicaoX();
    int posicaoYDestino = destino.getPosicaoY();

    int deltaX = posicaoXDestino - posicaoXOrigem;
    int deltaY = posicaoYDestino - posicaoYOrigem;
    System.out.println("Mover de peça vermelha");

    return Math.abs(deltaX) == Math.abs(deltaY) && deltaY == -1;
  }
}
