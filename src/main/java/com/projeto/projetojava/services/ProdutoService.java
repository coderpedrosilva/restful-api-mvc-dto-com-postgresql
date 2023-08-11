package com.projeto.projetojava.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.projetojava.model.Produto;
import com.projeto.projetojava.model.exception.ResourceNotFoundException;
import com.projeto.projetojava.repository.ProdutoRepository;
import com.projeto.projetojava.shared.ProdutoDTO;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Método para retornar uma lista de ptodutos
     * 
     * @return Lista de produtos
     */
    public List<ProdutoDTO> obterTodos() {

        // Retorna uma lista de podutos model
        List<Produto> produtos = produtoRepository.findAll();

        return produtos.stream()
                .map(produto -> new ModelMapper().map(produto, ProdutoDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Método que retorna o produto encontrado pelo seu Id
     * 
     * @param id do produto que será localizado
     * @return Retorna um produto caso seja encontrado
     */
    public Optional<ProdutoDTO> obterPorId(Integer id) {
        //Obtendo optional de produto pelo Id
        Optional<Produto> produto = produtoRepository.findById(id);
        //Se não encontrar lança Exception
        if(produto.isEmpty()){
            throw new ResourceNotFoundException("Produto com id: " + id + " não encontrado")
        }
        // Convertendo o meu optional de produto em um produto dto
        ProdutoDTO dto = new ModelMapper().map(produto.get(), ProdutoDTO.class);
        //Criando e retornando um optional de produto dto
        return Optional.of(dto);
    }

    /**
     * Método que adiciona um produto na lista de produtos
     * 
     * @param produto que será adicionado
     * @return Retorna o produto adicionado
     */
    public ProdutoDTO adicionar(ProdutoDTO produtoDto) {
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
    public ProdutoDTO atualizar(Integer id, ProdutoDTO produto) {

        produto.setId(id);
        return produtoRepository.save(produto);
    }

}
