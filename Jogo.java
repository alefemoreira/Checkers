
/**
 * Armazena o tabuleiro e responsavel por posicionar as pecas.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
public class Jogo {

  private Tabuleiro tabuleiro;

  public Jogo() {
    tabuleiro = new Tabuleiro();
    criarPecas();
  }
  
  /**
   * Posiciona pe�as no tabuleiro.
   * Utilizado na inicializa�ao do jogo.
   */
  private void criarPecas() {
    this.criarPecasPretas();   
    this.criarPecasBrancas();

  }
  
  public void criarPecasPretas() {
    for(int i = 7; i > 4; i--) { // cria as 12 peças pretas
      for(int j = 0; j < 8; j += 2) {
        if (i % 2 == 1 && j == 0) {
          j++;
        }
        Casa casaPreta = tabuleiro.getCasa(j, i);
        Peca.criarNovaPeca(casaPreta, Peca.PRETA);
      }
      }
  }
  
  public void criarPecasBrancas() {    
    for(int i = 0; i < 3; i++) { // cria as 12 peças brancas
      for(int j = 0; j < 8; j += 2) {
      if (i % 2 == 1 && j == 0) {
        j++;
      }
      Casa casaBranca = tabuleiro.getCasa(j, i);
      Peca.criarNovaPeca(casaBranca, Peca.BRANCA);
      }
    }
  }
  
  /**
   * Comanda uma Pe�a na posicao (origemX, origemY) fazer um movimento 
   * para (destinoX, destinoY).
   * 
   * @param origemX linha da Casa de origem.
   * @param origemY coluna da Casa de origem.
   * @param destinoX linha da Casa de destino.
   * @param destinoY coluna da Casa de destino.
   */
  public void moverPeca(int origemX, int origemY, int destinoX, int destinoY) {
    Casa origem = tabuleiro.getCasa(origemX, origemY);
    Casa destino = tabuleiro.getCasa(destinoX, destinoY);
    Peca peca = origem.getPeca();
    peca.mover(destino);
  }
  
  /**
   * @return o Tabuleiro em jogo.
   */
  public Tabuleiro getTabuleiro() {
    return tabuleiro;
  }
}
