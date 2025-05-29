/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projeto_2.ctr;

import java.sql.ResultSet;
import br.com.projeto_2.dto.FornecedorDTO;
import br.com.projeto_2.dto.ProdutoDTO;
import br.com.projeto_2.dao.ProdutoDAO;
import br.com.projeto_2.dao.ConexaoDAO;

/**
 *
 * @author allan
 */
public class ProdutoCTR {
    
    ProdutoDAO produtoDAO = new ProdutoDAO();
    
    public ProdutoCTR() {
        
    }
    
    public String inserirProduto(ProdutoDTO produtoDTO, FornecedorDTO fornecedorDTO) {
        try{
            if (produtoDAO.inserirProduto(produtoDTO, fornecedorDTO)) {
                return "Produto Cadastrado com Sucesso!!!";
            } else {
                return "Produto NÃO Cadastrado!!!";
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return "Produto NÃO Cadastrado";
        }
    }
    
    public String alterarProduto(ProdutoDTO produtoDTO, FornecedorDTO fornecedorDTO) {
        try {
            if(produtoDAO.alterarProduto(produtoDTO, fornecedorDTO)) {
                return "Produto Alterado com Sucesso!!!";
            } else {
                return "Produto NÃO Alterado!!!";
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return "Produto NÃO Cadastrado";
        }
    }
    
    public String excluirProduto(ProdutoDTO produtoDTO) {
        try {
            if (produtoDAO.excluirProduto(produtoDTO)) {
                return "Produto Excluído com Sucesso!!!";
            } else {
                return "Produto NÃO Excluído!!!";
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return "Produto NÃO Excluído";
        }
    }
    
    public ResultSet consultarProduto(ProdutoDTO produtoDTO, int opcao) {
        
        ResultSet rs = null;
        
        rs = produtoDAO.consultarProduto(produtoDTO, opcao);
        return rs;
    }
    
    public void CloseDB() {
        ConexaoDAO.CloseDB();
    }
    
}
