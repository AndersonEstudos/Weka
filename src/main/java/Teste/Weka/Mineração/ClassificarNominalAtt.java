package Teste.Weka.Mineração;

/*
 * Essa classe é responsável por avaliar e classificar atributos
 * nominais, é usado o algoritmo J48 da api do weka.
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
	 * conforme a regra de avalição criados com a arvore de decição 
	 * do algoritimo j48.
	 * 
	 * @Param Instances, conjunto de dados que será usado para criar as regras de avaliação
	 * @Param Instance, instancia unica a ser avaliada
	 * 
	 * @return boolean, true caso a instancia tenha as condições certas conforme as regras de avaliação
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
	 * imprime as caracteristicas da arvore de avalição criada.
	 * 
	 * @param Instances,  conjunto de dados usado para criar as regras de avaliação
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
			System.out.println("Digite o valor e o indice do atributo que você quer:");
			System.out.println("Indice:");
			indice = input.nextInt();

			System.out.println("O atributo escolhido é do tipo String?");
			System.out.println("sim = true | não = false ");
			
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
			System.out.println("sim = true | não = false ");
			
			if (input.nextBoolean() == false)
			{
				continuar = false;
			}
		}
	
		return instancia;
	}
	
	/*
	 * o metodo classifica uma instancia como verdadeiro ou falsa
	 * conforme a regra de avalição criados com a arvore de decição 
	 * do algoritimo j48.
	 * sera criado uma instancia, e pedido que se escolha as os atributos e os valores
	 * que serão usado para a avaliação
	 * 
	 * @Param Instances, conjunto de dados que será usado para criar as regras de avaliação
	 * 
	 * 
	 * @return boolean, true caso a instancia tenha as condições certas conforme as regras de avaliação
	 * e false caso contrario.
	 */
	public boolean classificaInstancia(Instances data) throws Exception
	{
		double predicao = tree.classifyInstance(preencheAtributos(data));
		if (predicao == 1)
		{
			System.out.println("prediçao Favoravel!");
			return true;
		}
			System.out.println("prediçao Desfavoravel!" + predicao);
		return false;		
	}
	
	/*
	 * cria uma avaliação de todas as instancias, conforme as regras de avaliação
	 * criados a partir de um conjunto usado para a associação. imprime na tela o numero
	 * de instancias certas e erradas.
	 * 
	 * @param Instances , um conjunto de dados usado para criação das regras de associação
	 * @param Instances , um conjunto de dados que será avaliado.
	 */
	
	public void AvaliaDados(Instances treino , Instances avaliado) throws Exception
	{
		constroiClassificacao(treino);
		avaliacao = new Evaluation(treino);
		avaliacao.evaluateModel(tree, avaliado);
		 
		 System.out.println(avaliacao.toSummaryString("\nResults\n\n", false));
		 
	}
}
