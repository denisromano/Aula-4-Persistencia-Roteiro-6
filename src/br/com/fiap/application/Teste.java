package br.com.fiap.application;

import java.util.List;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.fiap.core.Helper;
import br.com.fiap.entity.Funcionario;
import br.com.fiap.entity.Tarefa;

public class Teste {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Aula4");
		EntityManager em = emf.createEntityManager();
		
		Cache cache = emf.getCache();

		Funcionario funcionario = new Funcionario();
		funcionario.setMatricula("2000");
		funcionario.setNome("Alberto Santos");
		Tarefa tarefa = new Tarefa();
		tarefa.setDescricao("Teste Unitario");
		tarefa.setDuracao(100);
		tarefa.getFuncionarios().add(funcionario);
		funcionario.getTarefas().add(tarefa);		
		
		incluirFuncionario(em,funcionario,tarefa);
		
		System.out.println("Funcionario " + funcionario.getNome()+"esta "
				+ "no chache?" +cache.contains(Funcionario.class, funcionario.getId()));
		
		listarFuncionarios(em);
		buscarFuncionario(em, "2000");
	}

	private static void incluirFuncionario(EntityManager em, Funcionario funcionario) {
		Helper dao = new Helper(em);

		try {
			dao.salvar(funcionario);
			System.out.println("Funcionario OK");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void listarFuncionarios(EntityManager em) {
		Helper dao = new Helper(em);
		List<Funcionario> funcionarios = dao.listarTodos();
		for (Funcionario funcionario : funcionarios) {
			System.out.println(funcionario.getMatricula() + ": " + funcionario.getNome());
		}
		//em.close();
	}

	private static void buscarFuncionario(String matricula) {
		
		Helper dao = new Helper(em);
		Funcionario f = dao.buscarFuncionario(matricula);
		System.out.println(f.getMatricula() + ": " + f.getNome());
	}
}