package Teste.Weka.Dao;

import org.hibernate.HibernateException;
import Teste.Weka.Dominio.PrevisaoTempo;


public class PrevisaoTempoDao extends PadraoDao<PrevisaoTempo>
{
	public PrevisaoTempo buscarPorId(long id) throws HibernateException, IndexOutOfBoundsException {
		return super.buscarPorIdClasse(id, PrevisaoTempo.class);
	}
}
