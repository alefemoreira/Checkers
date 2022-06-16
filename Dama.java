import java.lang.Math;
import java.util.ArrayList;

import javax.xml.transform.Source;

public class Dama extends Peca {
  // Setar parametros com base na cor e remover especiazações de cores
  Dama(Casa casa, Cor cor) {
    super(casa, cor);
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
  public boolean capturar(Casa destino, Tabuleiro tabuleiro) {
    int posicaoXOrigem = this.casa.getPosicaoX();
    int posicaoYOrigem = this.casa.getPosicaoY();
    int posicaoXDestino = destino.getPosicaoX();
    int posicaoYDestino = destino.getPosicaoY();

    int deltaX = posicaoXDestino - posicaoXOrigem;
    int deltaY = posicaoYDestino - posicaoYOrigem;

    if (Math.abs(deltaX) != Math.abs(deltaY)) {
      return false;
    }

    if (Math.abs(deltaX) == 1) return false;

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

  @Override
  public boolean podeCapturar(Tabuleiro tabuleiro) {

    for (int i = -1; i <= 1; i += 2) {
      for (int j = -1; j <= 1; j += 2) {
        for (int k = 1; k < 8; k++) {
          int x = this.casa.getPosicaoX() + k * i;
          int y = this.casa.getPosicaoY() + k * j;
          boolean posicaoInvalida = x < 0 || x > 7 || y < 0 || y > 7;
          if (posicaoInvalida) continue;
          
          Casa casa = tabuleiro.getCasa(x, y);
          Peca peca = casa.getPeca();
          int xDeCaptura = x + i;
          int yDeCaptura = y + j;
          posicaoInvalida = xDeCaptura < 0 || xDeCaptura > 7 || yDeCaptura < 0 || yDeCaptura > 7;
          if (posicaoInvalida) {
            continue;
          }

          Casa casaDeCaptura = tabuleiro.getCasa(xDeCaptura, yDeCaptura);
          boolean casaDeCapturaLivre = !casaDeCaptura.possuiPeca();
          
          if (casa.possuiPeca() && !this.ehMesmaCor(peca) && casaDeCapturaLivre) {
            return true;
          } 
          if (casa.possuiPeca() && !this.ehMesmaCor(peca) && !casaDeCapturaLivre) k = 8;
        }
      }
    }

    return false;
  }

  public ArrayList<int[]> encontrarCasasParaCapturar(Tabuleiro tabuleiro) {
    ArrayList<int[]> casas = new ArrayList<int[]>();

    for (int i = -1; i <= 1; i += 2) {
      for (int j = -1; j <= 1; j += 2) {
        for (int k = 1; k < 8; k++) {
          int x = this.casa.getPosicaoX() + k * i;
          int y = this.casa.getPosicaoY() + k * j;
          boolean posicaoInvalida = x < 0 || x > 7 || y < 0 || y > 7;
          if (posicaoInvalida) continue;
          
          Casa casa = tabuleiro.getCasa(x, y);
          Peca peca = casa.getPeca();
          int xDeCaptura = x + i;
          int yDeCaptura = y + j;
          posicaoInvalida = xDeCaptura < 0 || xDeCaptura > 7 || yDeCaptura < 0 || yDeCaptura > 7;
          if (posicaoInvalida) {
            continue;
          }

          Casa casaDeCaptura = tabuleiro.getCasa(xDeCaptura, yDeCaptura);
          boolean casaDeCapturaLivre = !casaDeCaptura.possuiPeca();
          
          if (casa.possuiPeca() && !this.ehMesmaCor(peca) && casaDeCapturaLivre) {
            int[] deltas = {k*i+i, k*j+j};
            casas.add(deltas);
          }
        }
      }
    }
    
    return casas;
  }

  private boolean mesmaDirecao(int delta1, int delta2){
    int direcao1 = delta1 / Math.abs(delta1);
    int direcao2 = delta2 / Math.abs(delta2);

    return direcao1 == direcao2;
  }

  @Override
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
      ArrayList<int[]> deltas = this.encontrarCasasParaCapturar(tabuleiro);
      boolean vaiCapturar = false;
      for (int[] delta : deltas) {
        boolean deltaXvalido = Math.abs(deltaX) >= Math.abs(delta[0]) && mesmaDirecao(deltaX, delta[0]);
        boolean deltaYvalido = Math.abs(deltaY) >= Math.abs(delta[1]) && mesmaDirecao(deltaY, delta[1]);
        if (deltaXvalido && deltaYvalido) {
          vaiCapturar = true;
        }
      }
      if (!vaiCapturar) return false;
    }

    // Só move a peça se o turno for o correto para a cor da peça
    if (this.cor != tabuleiro.corTurno()) { 
      System.out.println("Turno errado");
      System.out.println(this.cor);
      return false;
    } 

    ArrayList<Peca> pecasBrancasQPodemCapturar = tabuleiro.encontractPecasQuePodemCapturar(this.ehBranca());
    System.out.println(pecasBrancasQPodemCapturar.toString());

    boolean temPecasQPodeCapturar = pecasBrancasQPodemCapturar.size() > 0;
    boolean ehUmaDasQPodemCapturar = pecasBrancasQPodemCapturar.contains(this);

    if (temPecasQPodeCapturar && !ehUmaDasQPodemCapturar) {
      return false;
    } else if (ehUmaDasQPodemCapturar) {
      ArrayList<int[]> deltas = this.encontrarCasasParaCapturar(tabuleiro);
      boolean vaiCapturar = false;
      for (int[] delta : deltas) {
        boolean deltaXvalido = Math.abs(deltaX) >= Math.abs(delta[0]) && mesmaDirecao(deltaX, delta[0]);
        boolean deltaYvalido = Math.abs(deltaY) >= Math.abs(delta[1]) && mesmaDirecao(deltaY, delta[1]);
        if (deltaXvalido && deltaYvalido) {
          vaiCapturar = true;
        }
      }
      if (!vaiCapturar) return false;
    }



    //  Se a peça for se mover e capturar outra peça a flag de movimento e captura é setada True(para realizar outro movimento)
    boolean capture = capturar(destino, tabuleiro);
    boolean move = !destino.possuiPeca() && (capture || this.ehPosicaoPermitida(destino, tabuleiro));   
    if (move && capture) {
      this.setMoveAndCapture(true);
      return move;
    } 

    this.setMoveAndCapture(false);
    return move;
  }
}
