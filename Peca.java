import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;

/**
 * Representa uma Pe�a do jogo.
 * Possui uma casa e um tipo associado.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
public class Peca {

  public static final int PRETA = 0;
  public static final int BRANCA = 1;
  public static final int DAMA_PRETA = 2;
  public static final int DAMA_BRANCA = 3;

  protected Casa casa;
  protected int tipo;
  protected int jogadas;

  public Peca(Casa casa, int tipo) {
    this.casa = casa;
    this.tipo = tipo;
    this.jogadas = 0;
    casa.colocarPeca(this);
  }

  public static Peca criarNovaPeca(Casa casa, int tipo) {
    if (tipo > 3) {
      throw new Error("Tipo invalido");
    }
    if (Peca.PRETA == tipo) {
      return new PecaPreta(casa, tipo);
    }
    if (Peca.BRANCA == tipo) {
      return new PecaBranca(casa, tipo);
    }
    if (Peca.DAMA_PRETA == tipo) {
      return new DamaPreta(casa, tipo);
    }
    if (Peca.DAMA_BRANCA == tipo) {
      return new DamaBranca(casa, tipo);
    }
    return null;
  }
  
  /**
   * Movimenta a peca para uma nova casa.
   * @param destino nova casa que ira conter esta peca.
   */
  public void mover(Casa destino) {
    if(this.podeMover(destino)) {
      this.casa.removerPeca();
      destino.colocarPeca(this);
      this.casa = destino;
      this.jogadas += 1;
    }
  }

  /**
   * Valor  Tipo
   *   0   Branco (PECA)
   *   1   Branco (DAMA)
   *   2   Preto (PECA)
   *   3   Preto (DAMA)
   * @return o tipo da peca.
   */
  public int getTipo() {
    return tipo;
  }

  public boolean ehBranca() {
    return this.tipo >= 6 && this.tipo <= 11;
  }

  public boolean ehMesmaCor(Peca peca) {
    return peca.ehBranca() == this.ehBranca();
  }

  public boolean vaiMatar(Casa destino) {
    Peca pecaDestino = destino.getPeca();
    return destino.possuiPeca() && !this.ehMesmaCor(pecaDestino) && destino.estaNaDiagonalSuperiorDe(this.casa);
  }

  public boolean ehPosicaoPermitida(Casa destino) {
    int posicaoXOrigem = this.casa.getPosicaoX();
    int posicaoYOrigem = this.casa.getPosicaoY();
    int posicaoXDestino = destino.getPosicaoX();
    int posicaoYDestino = destino.getPosicaoY();

    int deltaX = posicaoXDestino - posicaoXOrigem;
    int deltaY = posicaoYDestino - posicaoYOrigem;

    return Math.abs(deltaX) == Math.abs(deltaY) && Math.abs(deltaX) == 1;   
  }

  public boolean podeMover(Casa destino) {
    return (!destino.possuiPeca() || this.ehBranca() != destino.getPeca().ehBranca()) && this.ehPosicaoPermitida(destino);
  }
}
