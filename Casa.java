
/**
 * Representa uma Casa do tabuleiro.
 * Possui uma posi�ao (i,j) e pode conter uma Pe�a.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
public class Casa {

    private int x;
    private int y;
    private Peca peca;

    public Casa(int x, int y) {
        this.x = x;
        this.y = y;
        this.peca = null;
    }
    
    /**
     * @param peca a Pe�a a ser posicionada nesta Casa.
     */
    public void colocarPeca(Peca peca) {
        this.peca = peca;
    }
    
    /**
     * Remove a peca posicionada nesta casa, se houver.
     */
    public void removerPeca() {
        peca = null;
    }
    
    /**
     * @return a Peca posicionada nesta Casa, ou Null se a casa estiver livre.
     */
    public Peca getPeca() {
        return peca;
    }

    /**
     * @return retorna a posição x da casa
     */
    public int getPosicaoX() {
        return this.x;
    }

    /**
     * @return retorna a posição y da casa
     */
    public int getPosicaoY() {
        return this.y;
    }
    
    /**
     * @return true se existe uma pe�a nesta casa, caso contrario false.
     */
    public boolean possuiPeca() {
        return peca != null;
    }

    public boolean estaNaDiagonalSuperiorDe(Casa origem) {
        boolean diagonalSuperiorDireita = origem.getPosicaoX() + 1 == this.x && origem.getPosicaoY() + 1 == this.y;
        boolean diagonalSuperiorEsquerda = origem.getPosicaoX() - 1 == this.x && origem.getPosicaoY() + 1 == this.y;

        return diagonalSuperiorDireita || diagonalSuperiorEsquerda;
    }
}
