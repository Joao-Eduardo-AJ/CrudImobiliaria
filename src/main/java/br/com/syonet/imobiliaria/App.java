package br.com.syonet.imobiliaria;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.syonet.imobiliaria.dao.condominio.CondominioDAOImpl;
import br.com.syonet.imobiliaria.repository.Condominio;
import br.com.syonet.imobiliaria.repository.Imovel;

public class App {

	private static CondominioDAOImpl crud = new CondominioDAOImpl();

    public static void main(String args[]) {
        List<Imovel> imoveisVale = new ArrayList<>();
        List<Imovel> imoveisJardins = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            Imovel imovel = new Imovel();
            String descricao = "Casa com ";

            if (i % 2 == 0) {
                imovel.setAluguel(499.90);
                descricao += "2 dormitórios, 1 banheiro, ";
            } else {
                imovel.setAluguel(399.90);
                descricao += "1 dormitório, 1 banheiro, ";
            }

            if (i <= 4) {
                descricao += "amplo espaço interno";
            } else if (i <= 8) {
                descricao += "quintal espaçoso";
            } else if (i <= 12) {
                descricao += "garagem para 1 carro";
            } else if (i <= 16) {
                descricao += "piscina";
            } else {
                descricao += "localização central";
            }

            imovel.setDescricao(descricao);

            if (i <= 10) {
                imoveisVale.add(imovel);
            } else {
                imoveisJardins.add(imovel);
            }
        }

        Condominio moradasDoVale = new Condominio();
        moradasDoVale.setName("Moradas do Vale");
        moradasDoVale.setImoveis(imoveisVale);

        Condominio belosJardins = new Condominio();
        belosJardins.setName("Belos Jardins");
        belosJardins.setImoveis(imoveisJardins);
        
        try {
			crud.saveCondominiosImoveis(moradasDoVale);
		} catch (IOException e) {
			e.printStackTrace();
		}
        try {
			crud.saveCondominiosImoveis(belosJardins);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        try {
        	System.out.println("======================== Lista de condominios ========================");
			String resposta = crud.listaCondominiosImoveis();
			System.out.println(resposta);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        try {
        	System.out.println("======================== Condominio com aluguel baixo ========================");
			String resposta = crud.obtemCondominiosComAluguelAbaixoDoValor(350);
			System.out.println(resposta);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        try {
        	System.out.println("======================== Condominios com mais de 3 imóveis ========================");
			String resposta = crud.obtemCondominiosComMaisQueTresImoveis();
			System.out.println(resposta);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
