import java.lang.Math;

public class DamaBranca extends Peca {
  DamaBranca(Casa casa, int tipo) {
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
  void promover() {}

  @Override
  public boolean canMoveToCapture(Casa destino, Tabuleiro tabuleiro) {
    int posicaoXOrigem = this.casa.getPosicaoX();
    int posicaoYOrigem = this.casa.getPosicaoY();
    int posicaoXDestino = destino.getPosicaoX();
    int posicaoYDestino = destino.getPosicaoY();

    int deltaX = posicaoXDestino - posicaoXOrigem;
    int deltaY = posicaoYDestino - posicaoYOrigem;

    if (Math.abs(deltaX) != Math.abs(deltaY)) {
      return false;
    }

    int quantidadeDePecas = 0;
    Peca ultimaPeca = null;
    Casa casaDaUltimaPeca = null;

    for (int i = 1; i <= Math.abs(deltaX); i++) {
      int posicaoXverificar = deltaX > 0 ? posicaoXOrigem + i : posicaoXOrigem - i;
      int posicaoYverificar = deltaY > 0 ? posicaoYOrigem + i : posicaoYOrigem - i;

      Casa casa = tabuleiro.getCasa(posicaoXverificar, posicaoYverificar);
      if (casa.possuiPeca()) {
        ultimaPeca = casa.getPeca();
        casaDaUltimaPeca = casa;
        quantidadeDePecas++;
      }
    }

    if (quantidadeDePecas == 1 && !this.ehMesmaCor(ultimaPeca)) {
      casaDaUltimaPeca.removerPeca(tabuleiro);
      return true;
    }

    return false;
  }

}
