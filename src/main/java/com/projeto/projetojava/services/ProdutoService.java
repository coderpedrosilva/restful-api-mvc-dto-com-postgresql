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

        // Obtendo optional de produto pelo Id
        Optional<Produto> produto = produtoRepository.findById(id);

        // Se não encontrar lança Exception
        if (produto.isEmpty()) {
            throw new ResourceNotFoundException("Produto com id: " + id + " não encontrado");
        }
        // Convertendo o meu optional de produto em um produto dto

        ProdutoDTO dto = new ModelMapper().map(produto.get(), ProdutoDTO.class);

        // Criando e retornando um optional de produto dto
        return Optional.of(dto);
    }

    /**
     * Método que adiciona um produto na lista de produtos
     * 
     * @param produto que será adicionado
     * @return Retorna o produto adicionado
     */
    public ProdutoDTO adicionar(ProdutoDTO produtoDto) {
        // Remover o id para conseguir fazer o cadastro
        produtoDto.setId(null);

        // Criar um objeto de mapeamento
        ModelMapper mapper = new ModelMapper();

        // Converter o nosso produto dto em um produto
        Produto produto = mapper.map(produtoDto, Produto.class);

        // Salvar o produto no banco
        produto = produtoRepository.save(produto);

        produtoDto.setId(produto.getId());

        // retornar o produto dto atualizado

        return produtoDto;
    }

    /**
     * Método para deletar um produto da lista de produtos por Id
     * 
     * @param id do produto a ser deletado
     */
    public void deletar(Integer id) {

        // Verificar se o produto existe
        Optional<Produto> produto = produtoRepository.findById(id);

        // Se não existir lança uma Exception
        if (produto.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Não foi possível deletar o produto com id: " + id + " - Produto não existe");
        }

        // Deleta o produto pelo id
        produtoRepository.deleteById(id);
    }

    /**
     * Método que atualiza um produto na lista de produtos
     * 
     * @param produto que será atualizado
     * @param id      do produto que será atualizado
     * @return Retorna o produto atualizado
     */
    public ProdutoDTO atualizar(Integer id, ProdutoDTO produtoDto) {

        // Passar o id para o produto dto
        produtoDto.setId(id);

        // Criar um objeto de mapeamentp
        ModelMapper mapper = new ModelMapper();

        // Converter o produto dto em um produto
        Produto produto = mapper.map(produtoDto, Produto.class);

        // Atualizar o produto no banco
        produtoRepository.save(produto);

        // Retornar o produto dto atualizado
        return produtoDto;
    }

}
