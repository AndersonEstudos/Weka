package Teste.Weka.Minera��o;

/*
 * Essa classe � respons�vel por avaliar e classificar atributos
 * nominais, � usado o algoritmo J48 da api do weka.
 * 
 */


import java.util.Scanner;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;

public class ClassificarNominalAtt 
{	
	private J48 tree;
	private Scanner input;
	private Evaluation avaliacao;

	public ClassificarNominalAtt()
	{
		tree = new J48();
	}
	
	private void constroiClassificacao(Instances data) throws Exception
	{
		tree.buildClassifier(data);
	}

	/*
	 * o metodo classifica uma instancia como verdadeiro ou falsa
	 * conforme a regra de avali��o criados com a arvore de deci��o 
	 * do algoritimo j48.
	 * 
	 * @Param Instances, conjunto de dados que ser� usado para criar as regras de avalia��o
	 * @Param Instance, instancia unica a ser avaliada
	 * 
	 * @return boolean, true caso a instancia tenha as condi��es certas conforme as regras de avalia��o
	 * e false caso contrario.
	 */
	public boolean classificaInstancia(Instances data,Instance instancia) throws Exception
	{
		constroiClassificacao(data);
		
		if (tree.classifyInstance(instancia) == 1)
		{
			return true;
		}
		
		return false;
	}
	
	/*
	 * imprime as caracteristicas da arvore de avali��o criada.
	 * 
	 * @param Instances,  conjunto de dados usado para criar as regras de avalia��o
	 */
	public void imprimeArvore(Instances data) throws Exception
	{
		constroiClassificacao(data);
		System.out.println(tree);
	}
	
	
	private void imprimeAtributos(Instances data)
	{
		for (int contador = 0 ; contador < data.numAttributes();contador++)
		{
			System.out.println("Atributo " + contador + ":" +data.attribute(contador));
		}
	}
	
	
	private Instance preencheAtributos(Instances data)
	{
		Instance instancia = new Instance(data.numAttributes());		
		instancia.setDataset(data);
		input = new Scanner(System.in);

		boolean continuar =  true;
		while(continuar)
		{	
			Integer indice = 0;
			
			imprimeAtributos(data);
			System.out.println();
			System.out.println("Digite o valor e o indice do atributo que voc� quer:");
			System.out.println("Indice:");
			indice = input.nextInt();

			System.out.println("O atributo escolhido � do tipo String?");
			System.out.println("sim = true | n�o = false ");
			
			if(input.nextBoolean())
			{
				System.out.println(instancia.attribute(indice));
				System.out.println("Valor Nominal:");
				instancia.setValue(indice,input.next());
			}
			
			else
			{	
			System.out.println("Valor numerico:" + indice);
			instancia.setValue(indice,input.nextInt());
			}		
			
			System.out.println("Deseja dar valor a outro atributo?");
			System.out.println("sim = true | n�o = false ");
			
			if (input.nextBoolean() == false)
			{
				continuar = false;
			}
		}
	
		return instancia;
	}
	
	/*
	 * o metodo classifica uma instancia como verdadeiro ou falsa
	 * conforme a regra de avali��o criados com a arvore de deci��o 
	 * do algoritimo j48.
	 * sera criado uma instancia, e pedido que se escolha as os atributos e os valores
	 * que ser�o usado para a avalia��o
	 * 
	 * @Param Instances, conjunto de dados que ser� usado para criar as regras de avalia��o
	 * 
	 * 
	 * @return boolean, true caso a instancia tenha as condi��es certas conforme as regras de avalia��o
	 * e false caso contrario.
	 */
	public boolean classificaInstancia(Instances data) throws Exception
	{
		double predicao = tree.classifyInstance(preencheAtributos(data));
		if (predicao == 1)
		{
			System.out.println("predi�ao Favoravel!");
			return true;
		}
			System.out.println("predi�ao Desfavoravel!" + predicao);
		return false;		
	}
	
	/*
	 * cria uma avalia��o de todas as instancias, conforme as regras de avalia��o
	 * criados a partir de um conjunto usado para a associa��o. imprime na tela o numero
	 * de instancias certas e erradas.
	 * 
	 * @param Instances , um conjunto de dados usado para cria��o das regras de associa��o
	 * @param Instances , um conjunto de dados que ser� avaliado.
	 */
	
	public void AvaliaDados(Instances treino , Instances avaliado) throws Exception
	{
		constroiClassificacao(treino);
		avaliacao = new Evaluation(treino);
		avaliacao.evaluateModel(tree, avaliado);
		 
		 System.out.println(avaliacao.toSummaryString("\nResults\n\n", false));
		 
	}
}
