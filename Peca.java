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
  public  boolean moveAndCapture = false;

  protected Casa casa;
  protected int jogadas;
  protected Cor cor;

  abstract boolean podePromover();
  abstract void promover();
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
      if (this.moveAndCapture) {
        Casa casaDireitaSuperior;
        Casa casaEsquerdaInferior;
        Casa casaDireitaInferior;
        Casa casaEsquerdaSuperior;

        if (moveAndCapture) {
          casaDireitaSuperior = this.casa.getPosicaoX() + 2 < 8 && this.casa.getPosicaoY() + 2 < 8 ? tabuleiro.getCasa(this.casa.getPosicaoX()+2, this.casa.getPosicaoY()+2) : this.casa;
          casaEsquerdaInferior = this.casa.getPosicaoX() - 2 >= 0 && this.casa.getPosicaoY() - 2 >= 0 ?  tabuleiro.getCasa(this.casa.getPosicaoX()-2, this.casa.getPosicaoY()-2) : this.casa;
          casaDireitaInferior = this.casa.getPosicaoX() + 2 < 8 && this.casa.getPosicaoY() - 2 >= 0 ?  tabuleiro.getCasa(this.casa.getPosicaoX()+2, this.casa.getPosicaoY()-2) : this.casa;
          casaEsquerdaSuperior =  this.casa.getPosicaoX() - 2 >= 0 && this.casa.getPosicaoY() + 2 < 8 ?  tabuleiro.getCasa(this.casa.getPosicaoX()-2, this.casa.getPosicaoY()+2) : this.casa;
  
          // Verificando se existe alguma casa possivel 
          Boolean move = (this.podeCapturarNovamente(casaDireitaSuperior, tabuleiro) && !casaDireitaSuperior.possuiPeca() )||
                         (this.podeCapturarNovamente(casaEsquerdaInferior, tabuleiro) && !casaEsquerdaInferior.possuiPeca() )||
                         (this.podeCapturarNovamente(casaDireitaInferior, tabuleiro) && !casaDireitaInferior.possuiPeca())||
                         (this.podeCapturarNovamente(casaEsquerdaSuperior, tabuleiro) && !casaEsquerdaSuperior.possuiPeca());  
  
          // Depois que a peça se move é analiádo se existe alguma possibilidade de captura
          if (!move) {
            this.setMoveAndCapture(false);
            tabuleiro.inverteTurno(); 
          } 
         
          if ((this.cor == Cor.PRETA) && move) {
            System.out.println("Peça branca pode se mover novamente");
          } else if(move) {
            System.out.println("Peça branca pode se mover novamente");
          }
          
          
        }       
      } else {
        tabuleiro.inverteTurno();        
      }

      // Só troca o turno se a peça não tiver capturado outra
      
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

    if (Math.abs(deltaX) != Math.abs(deltaY)) return false;
    // Verificando se outra peça fez um movimento de captura e tem que se mover de novo
    for (int i = 0; i <= 7; i++) {
      for (int j = 0; j <= 7; j++) {
        Casa casa = tabuleiro.getCasa(i, j);
        if(casa.possuiPeca()) {
          Peca peca = casa.getPeca();
          //System.out.println(casa != this.casa);
          if(peca.moveAndCapture && casa != this.casa) {
            if (pecaIsBlack && tabuleiro.isTurnoPreto()) {
              System.out.println("Apenas a peça vermelha que fez a captura pode se mover");
            } else if (!pecaIsBlack && !tabuleiro.isTurnoPreto()){
              System.out.println("Apenas a peça branca que fez a captura pode se mover");
            }
         
            return false;
          }
        }
      }
    }

    if (this.moveAndCapture) {
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

    boolean capture = capturar(destino, tabuleiro);
    boolean move = !destino.possuiPeca() && (capture || this.ehPosicaoPermitida(destino, tabuleiro));   
    if (move && capture) {
      this.setMoveAndCapture(true);
      return move;
    } 

    this.setMoveAndCapture(false);
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
  public boolean capturar(Casa destino, Tabuleiro tabuleiro) {
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
          casaDeCaptura.removerPeca(tabuleiro);     
        return true;
      }      
    }

     //Não move
     return false;
      
  }


  public boolean podeCapturar(Tabuleiro tabuleiro) {
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


  public boolean podeCapturarNovamente(Casa destino, Tabuleiro tabuleiro) {
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
        return true;
      }      
    }

     //Não move
     return false;
      
  }

  public boolean isMoveAndCapture() {
    return moveAndCapture;
  }
  
  public void setMoveAndCapture(boolean moveAndCapture) {
    this.moveAndCapture = moveAndCapture;
  }

}
