package Teste.Weka.Dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="previsaotempo", schema = "previsao")
public class PrevisaoTempo extends PadraoDominio 
{
	@Id
	@SequenceGenerator(name = "sequencia", sequenceName = "teste.seq_previsao")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sequencia")
	@Column(name = "id")
	public int ID;
	
	@Column(name="temperatura")
	public int temperatura;
	
	@Column(name="umidade")
	public int umidade;
	
	@Column(name="choveu")
	public boolean choveu;
	
	public int getTemperatura()
	{
		return temperatura;
	}

	public void setTemperatura(int temperatura)
	{
		this.temperatura = temperatura;
	}

	public int getUmidade()
	{
		return umidade;
	}

	public void setUmidade(int umidade)
	{
		this.umidade = umidade;
	}

	public boolean isChoveu() 
	{
		return choveu;
	}

	public void setChoveu(boolean choveu)
	{
		this.choveu = choveu;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public void setID(int id) {
		// TODO Auto-generated method stub
		ID = id;
	}

}
