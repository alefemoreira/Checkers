import java.util.ArrayList;

/**
 * O Tabuleiro do jogo. 
 * Respons�vel por armazenar as 64 casas.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
public class Tabuleiro {

  private Casa[][] casas;
  private boolean turnoPreto;
  private int totalBrancas = 12;
  private int totalPretas = 12;  

  public Tabuleiro() {
    casas = new Casa[8][8];
    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        Casa casa = new Casa(x, y);
        casas[x][y] = casa;
      }
    }
  }
  /**
   * @param x linha
   * @param y coluna
   * @return Casa na posicao (x,y)
   */
  public Casa getCasa(int x, int y) {
    return casas[x][y];
  }

  public boolean isTurnoPreto() {
    return turnoPreto;
  }

  public Cor corTurno() {
    return turnoPreto ? Cor.PRETA : Cor.BRANCA;
  }

  public void setTurnoPreto(boolean turnoPreto) {
      turnoPreto = turnoPreto;
  }

  public void inverteTurno() {
    turnoPreto = !turnoPreto;
  }

  public ArrayList<Peca> encontractPecasQuePodemCapturar(boolean pecaBranca) {
    if (pecaBranca) {
      return this.encontractPecasQuePodemCapturarBrancas();
    } 
    return this.encontractPecasQuePodemCapturarPretas();
  }

  public ArrayList<Peca> encontractPecasQuePodemCapturarBrancas() {
    ArrayList<Peca> pecasQPodemCapturar = new ArrayList<Peca>();
    
    for(int i = 0; i < 8; i++) {
      for(int j = 0; j < 8; j++) {
        Casa casa = casas[i][j];
        Peca peca = casa.getPeca();
        if(casa.possuiPeca() && peca.ehBranca() && peca.podeCapturar(this)) {
          pecasQPodemCapturar.add(peca);
        }
      }
    }

    return pecasQPodemCapturar;
  }
  
  public ArrayList<Peca> encontractPecasQuePodemCapturarPretas() {
    ArrayList<Peca> pecasQPodemCapturar = new ArrayList<Peca>();
    
    for(int i = 0; i < 8; i++) {
      for(int j = 0; j < 8; j++) {
        Casa casa = casas[i][j];
        Peca peca = casa.getPeca();
        if(casa.possuiPeca() && !peca.ehBranca() && peca.podeCapturar(this)) {
          System.out.println("É dama? vv");
          System.out.println(peca instanceof Dama);
          pecasQPodemCapturar.add(peca);
        }
      }
    }

    return pecasQPodemCapturar;
  }
  
  public int getTotalBrancas() {
    return totalBrancas;
  }

  public void setTotalBrancas(int totalBrancas) {
    this.totalBrancas = totalBrancas;
  }

  public int getTotalPretas() {
    return totalPretas;
  }

  public void setTotalPretas(int totalPretas) {
    this.totalPretas = totalPretas;
  }

}
