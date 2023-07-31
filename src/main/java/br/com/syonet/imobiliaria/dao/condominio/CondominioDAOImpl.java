package br.com.syonet.imobiliaria.dao.condominio;

import java.io.IOException;
import java.util.List;

import javax.persistence.TypedQuery;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.syonet.imobiliaria.repository.Condominio;
import br.com.syonet.imobiliaria.service.EntityManagerProvider;

public class CondominioDAOImpl {
	private static EntityManagerProvider entityManagerProvider = EntityManagerProvider.getInstance();
	ObjectMapper objectMapper = new ObjectMapper();

	public String listaCondominiosImoveis() throws IOException {
		TypedQuery<Condominio> query = entityManagerProvider
				.executeTransaction(manager -> manager.createQuery("select c from Condominio c", Condominio.class));
		List<Condominio> condominios = query.getResultList();

		return objectMapper.writeValueAsString(condominios);
	}

	public void saveCondominiosImoveis(Condominio condominio) throws IOException {
		entityManagerProvider.executeTransaction(manager -> {
			manager.persist(condominio);
			return null;
		});
	}

	public String obtemCondominiosComMaisQueTresImoveis() throws IOException {
		TypedQuery<Condominio> query = entityManagerProvider.executeTransaction(manager -> manager
				.createQuery("select c from Condominio c where size(c.imoveis) > 3", Condominio.class));

		List<Condominio> condominios = query.getResultList();
		return objectMapper.writeValueAsString(condominios);
	}

	public String obtemCondominiosComAluguelAbaixoDoValor(double value) throws IOException {
		TypedQuery<Condominio> query = entityManagerProvider.executeTransaction(manager -> manager.createQuery(
				"select c from Condominio c "
						+ "where (select i.aluguel from Imovel i where i.condominio = c) < :value",
				Condominio.class));
		query.setParameter("value", value);
		List<Condominio> condominios = query.getResultList();
		return objectMapper.writeValueAsString(condominios);
	}
}
