import java.lang.Math;

/**
 * Representa uma Pe�a do jogo.
 * Possui uma casa e um tipo associado.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
public abstract class Peca {

  public static final int PRETA = 0;
  public static final int BRANCA = 1;
  public static final int DAMA_PRETA = 2;
  public static final int DAMA_BRANCA = 3;

  protected Casa casa;
  protected int tipo;
  protected int jogadas;

  abstract boolean podePromover();
  abstract void promover();

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
  public void mover(Casa destino, Tabuleiro tabuleiro) {
    if(this.podeMover(destino, tabuleiro)) {
      this.casa.removerPeca();
      destino.colocarPeca(this);
      this.casa = destino;
      this.jogadas += 1;
      if (this.podePromover()) {
        this.promover();
      }

      tabuleiro.inverteTurno();
    }
  }

  /**
   * Valor  Tipo
   *   0   Preto (PECA)
   *   1   Branco (DAMA)
   *   2   Preto (PECA)
   *   3   Branco (DAMA)
   * @return o tipo da peca.
   */
  public int getTipo() {
    return tipo;
  }

  public boolean ehBranca() {
    return this.tipo == 1 || this.tipo == 3;
  }

  public boolean ehMesmaCor(Peca peca) {
    return peca.ehBranca() == this.ehBranca();
  }

  public boolean vaiMatar(Casa destino) {
    Peca pecaDestino = destino.getPeca();
    return destino.possuiPeca() && !this.ehMesmaCor(pecaDestino) && destino.estaNaDiagonalSuperiorDe(this.casa);
  }

  public boolean ehPosicaoPermitida(Casa destino,  Tabuleiro tabuleiro) {
    int posicaoXOrigem = this.casa.getPosicaoX();
    int posicaoYOrigem = this.casa.getPosicaoY();
    int posicaoXDestino = destino.getPosicaoX();
    int posicaoYDestino = destino.getPosicaoY();

    int deltaX = posicaoXDestino - posicaoXOrigem;
    int deltaY = posicaoYDestino - posicaoYOrigem;

    return Math.abs(deltaX) == Math.abs(deltaY);   
  }

  public boolean podeMover(Casa destino,  Tabuleiro tabuleiro) {
    boolean pecaIsBlack = this.tipo == 0 || this.tipo == 2;

    // So move a peça se o turno for o correto para a cor da peça
    if ((pecaIsBlack && !tabuleiro.isTurnoPreto()) || (!pecaIsBlack && tabuleiro.isTurnoPreto())) { 
      System.out.println("Turno errado");
      return false;
    } 

    //return (!destino.possuiPeca() || this.ehBranca() != destino.getPeca().ehBranca()) && this.ehPosicaoPermitida(destino, tabuleiro);
    return !destino.possuiPeca() && (canMoveToCapture(destino, tabuleiro) || this.ehPosicaoPermitida(destino, tabuleiro));
  }

  public boolean canMoveToCapture(Casa destino, Tabuleiro tabuleiro) {
    int posicaoXOrigem = this.casa.getPosicaoX();
    int posicaoYOrigem = this.casa.getPosicaoY();
    int posicaoXDestino = destino.getPosicaoX();
    int posicaoYDestino = destino.getPosicaoY();

    int deltaX = posicaoXDestino - posicaoXOrigem;
    int deltaY = posicaoYDestino - posicaoYOrigem;
    if(Math.abs(deltaX) == Math.abs(deltaY) && Math.abs(deltaX) == 2) {
      // Determinando a possição intermediria da pessa que será capturada
      int xCapture = deltaX < 0 ? posicaoXDestino + 1 :  posicaoXDestino - 1;
      int yCapture = deltaY < 0 ? posicaoYDestino + 1 :  posicaoYDestino - 1;

      // Só faz a captura e o movimento se houver um peça na casa intermetiaria do movimento de 2 casas
      Casa casaDeCaptura = tabuleiro.getCasa(xCapture, yCapture);
      Peca pecaDeCaptura = casaDeCaptura.getPeca();

      if (casaDeCaptura.possuiPeca() && !this.ehMesmaCor(pecaDeCaptura)) {
        // Captura e move
        casaDeCaptura.removerPeca(tabuleiro);
        return true;
      }      
    }

     //Não move
     return false;
      
  }
}
