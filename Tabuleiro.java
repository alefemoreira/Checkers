/**
 * O Tabuleiro do jogo. 
 * Respons�vel por armazenar as 64 casas.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
public class Tabuleiro {

  private Casa[][] casas;
  private boolean TurnoPreto;
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
    return TurnoPreto;
  }
  public void setTurnoPreto(boolean turnoPreto) {
      TurnoPreto = turnoPreto;
  }

  public void inverteTurno() {
    TurnoPreto = !TurnoPreto;
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
