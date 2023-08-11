package com.projeto.projetojava.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.projetojava.model.Produto;
import com.projeto.projetojava.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Método para retornar uma lista de ptodutos
     * 
     * @return Lista de produtos
     */
    public List<Produto> obterTodos() {
        return produtoRepository.findAll();
    }

    /**
     * Método que retorna o produto encontrado pelo seu Id
     * 
     * @param id do produto que será localizado
     * @return Retorna um produto caso seja encontrado
     */
    public Optional<Produto> obterPorId(Integer id) {
        return produtoRepository.findById(id);
    }

    /**
     * Método que adiciona um produto na lista de produtos
     * 
     * @param produto que será adicionado
     * @return Retorna o produto adicionado
     */
    public Produto adicionar(Produto produto) {
        return produtoRepository.save(produto);
    }

    /**
     * Método para deletar um produto da lista de produtos por Id
     * 
     * @param id do produto a ser deletado
     */
    public void deletar(Integer id) {
        produtoRepository.deleteById(id);
    }

    /**
     * Método que atualiza um produto na lista de produtos
     * 
     * @param produto que será atualizado
     * @param id      do produto que será atualizado
     * @return Retorna o produto atualizado
     */
    public Produto atualizar(Integer id, Produto produto) {

        produto.setId(id);
        return produtoRepository.save(produto);
    }

}
