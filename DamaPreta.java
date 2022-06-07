public class DamaPreta extends Peca {
  DamaPreta(Casa casa, int tipo) {
  super(casa, tipo);
  }

  @Override
  public boolean ehPosicaoPermitida(Casa destino, Tabuleiro tabuleiro) {
    int posicaoXOrigem = this.casa.getPosicaoX();
    int posicaoYOrigem = this.casa.getPosicaoY();
    int posicaoXDestino = destino.getPosicaoX();
    int posicaoYDestino = destino.getPosicaoY();

    int deltaX = posicaoXDestino - posicaoXOrigem;
    int deltaY = posicaoYDestino - posicaoYOrigem;

    if (Math.abs(deltaX) != Math.abs(deltaY)) {
      return false;
    }

    for (int i = 1; i <= Math.abs(deltaX); i++) {
      int posicaoXverificar = deltaX > 0 ? posicaoXOrigem + i : posicaoXOrigem - i;
      int posicaoYverificar = deltaY > 0 ? posicaoYOrigem + i : posicaoYOrigem - i;

      Casa casa = tabuleiro.getCasa(posicaoXverificar, posicaoYverificar);
      if (casa.possuiPeca()) return false;
    }

    return true;
  }

  @Override
  boolean podePromover() {
    return false;
  }

  @Override
  void promover() {
  }
}
