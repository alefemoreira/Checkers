import java.lang.Math;
import java.util.ArrayList;

/**
 * Representa uma Pe�a do jogo.
 * Possui uma casa e um tipo associado.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
public abstract class Peca {
  protected Casa casa;
  protected Casa casaDeCaptura;
  protected int jogadas;
  protected Cor cor;

  abstract boolean podePromover();
  abstract void promover();
  abstract public boolean ehPosicaoPermitida(Casa destino,  Tabuleiro tabuleiro);

  // # Substituir tipo por cor
  public Peca(Casa casa, Cor cor) {
    this.casa = casa;
    this.cor = cor;
    this.jogadas = 0;
    casa.colocarPeca(this);
  }
  /**
   * Movimenta a peca para uma nova casa.
   * @param destino nova casa que ira conter esta peca.
   */
  public void mover(Casa destino, Tabuleiro tabuleiro) {
    if(this.podeMover(destino, tabuleiro)) {     
      if (this.podeCapturar(destino, tabuleiro)) {
        this.capturar(destino, tabuleiro);
      }
      
      this.casa.removerPeca();
      destino.colocarPeca(this);
      this.casa = destino;
      this.jogadas += 1;


      if (this.cor == Cor.PRETA) {
        System.out.println("Peça vermelha foi movida");
      } else {
        System.out.println("Peça branca foi movida");
      }

      if (this.podePromover()) {
        this.promover();
      }

      // Verificando se a peça pode se mover novamente
      if (tabuleiro.getPecaQCapturou() == this) {
        boolean aindaPodeCapturar = this.estaEmPosicaoDeCapturar(tabuleiro);

        // Depois que a peça se move é analiádo se existe alguma possibilidade de captura
        if (!aindaPodeCapturar) {
          tabuleiro.removerPecaQCapturou();
          tabuleiro.inverteTurno(); 
        } 
        
        if ((this.cor == Cor.PRETA) && aindaPodeCapturar) {
          System.out.println("Peça branca pode se mover novamente");
        } else if(aindaPodeCapturar) {
          System.out.println("Peça branca pode se mover novamente");
        }            
      } else {
        tabuleiro.inverteTurno();        
      }

      // Só troca o turno se a peça não tiver capturado outra
      
    }
  }

  /**
   * @return a cor da peca.
   */
  public Cor getCor() {
    return this.cor;
  }

  public boolean ehBranca() {
    return this.cor == Cor.BRANCA;
  }

  public boolean ehMesmaCor(Peca peca) {
    return this.cor == peca.getCor();
  }

  public int getDeltaDeCaptura() {
    return 2;
  }

  public boolean equals(Peca peca) {
    int x = this.casa.getPosicaoX();
    int y = this.casa.getPosicaoY();
    int pecaX = peca.casa.getPosicaoX();
    int pecaY = peca.casa.getPosicaoY();

    return x == pecaX && y == pecaY;
  }

  public boolean podeMover(Casa destino,  Tabuleiro tabuleiro) {    
    boolean pecaIsBlack = !this.ehBranca();
    int posicaoXOrigem = this.casa.getPosicaoX();
    int posicaoYOrigem = this.casa.getPosicaoY();
    int posicaoXDestino = destino.getPosicaoX();
    int posicaoYDestino = destino.getPosicaoY();

    int deltaX = posicaoXDestino - posicaoXOrigem;
    int deltaY = posicaoYDestino - posicaoYOrigem;

    boolean moveAndCapture = tabuleiro.getPecaQCapturou() == this;

    if (Math.abs(deltaX) != Math.abs(deltaY)) return false;
    // Verificando se outra peça fez um movimento de captura e tem que se mover de novo
    if (!moveAndCapture) {
      if (pecaIsBlack && tabuleiro.isTurnoPreto()) {
        System.out.println("Apenas a peça vermelha que fez a captura pode se mover");
      } else if (!pecaIsBlack && !tabuleiro.isTurnoPreto()){
        System.out.println("Apenas a peça branca que fez a captura pode se mover");
      }
    }

    if (moveAndCapture) {
      if(Math.abs(deltaX) < this.getDeltaDeCaptura() && Math.abs(deltaY) < this.getDeltaDeCaptura()) {
        if (pecaIsBlack && tabuleiro.isTurnoPreto()) {
          System.out.println("A peça vermelha é obrigada a captura");
        } else if (!pecaIsBlack && !tabuleiro.isTurnoPreto()){
          System.out.println("A peça branca é obrigada a captura");
        }
        return false;
      }
    }

    // Só move a peça se o turno for o correto para a cor da peça
    if ((this.cor != tabuleiro.corTurno())) { 
      System.out.println("Turno errado");
      return false;
    } 

    ArrayList<Peca> pecasBrancasQPodemCapturar = tabuleiro.encontractPecasQuePodemCapturar(this.ehBranca());
    System.out.println(pecasBrancasQPodemCapturar.toString());

    boolean temPecasQPodeCapturar = pecasBrancasQPodemCapturar.size() > 0;
    boolean ehUmaDasQPodemCapturar = pecasBrancasQPodemCapturar.contains(this);

    if ((temPecasQPodeCapturar && !ehUmaDasQPodemCapturar) || ( ehUmaDasQPodemCapturar && Math.abs(deltaX) != 2)) {
      return false;
    }

    boolean capture = podeCapturar(destino, tabuleiro);
    boolean move = !destino.possuiPeca() && (capture || this.ehPosicaoPermitida(destino, tabuleiro));

    return move;
  }

  /**
   * Executa em 2 modos, pesquisa e não pesquisa 
   * Em modo pesquisa apenas verifica se há alguma peça para capturar sem remove-la.
   * Em modo não pesquisa além de verificar se há uma peça para capturar ainda remove essa peça
   * 
   * @param destino A casa para onde a peça vai depois que fazer a captura.
   * @param tabuleiro Tabuleiro do jogo.
   * @param search Quando true ativa o modo pesquisa.
   * 
   */
  public void capturar(Casa destino, Tabuleiro tabuleiro) {
    this.casaDeCaptura.removerPeca(tabuleiro);
    tabuleiro.setPecaQCapturou(this);
  }

  public boolean estaEmPosicaoDeCapturar(Tabuleiro tabuleiro) {
    ArrayList<Integer> posicoesProibidas = new ArrayList<Integer>();
    posicoesProibidas.add(-1);
    posicoesProibidas.add(8);

    for (int i = -1; i <= 1; i += 2) {
      for (int j = -1; j <= 1; j += 2) {
        int x = this.casa.getPosicaoX() + i;
        int y = this.casa.getPosicaoY() + j;

        if (posicoesProibidas.contains(x) || posicoesProibidas.contains(y)) {
          continue;
        }

        Casa casa = tabuleiro.getCasa(x, y);
        Peca peca = casa.getPeca();
        int xDeCaptura = x + i;
        int yDeCaptura = y + j;

        if (posicoesProibidas.contains(xDeCaptura) || posicoesProibidas.contains(yDeCaptura)) {
          continue;
        }

        Casa casaDeCaptura = tabuleiro.getCasa(xDeCaptura, yDeCaptura);
        boolean casaDeCapturaLivre = !casaDeCaptura.possuiPeca();
        
        if (casa.possuiPeca() && !this.ehMesmaCor(peca) && casaDeCapturaLivre) {
          return true;
        }
      }
    }

    return false;
  }


  public boolean podeCapturar(Casa destino, Tabuleiro tabuleiro) {
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
        this.casaDeCaptura = casaDeCaptura;       
        return true;
      }      
    }

     //Não move
     return false;
      
  }
}
