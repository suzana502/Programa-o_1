/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projeto_2.ctr;

import java.sql.ResultSet;
import br.com.projeto_2.dao.FornecedorDAO;
import br.com.projeto_2.dto.FornecedorDTO;
import br.com.projeto_2.dao.ConexaoDAO;

/**
 *
 * @author allan
 */
public class FornecedorCTR {
    
    FornecedorDAO fornecedorDAO = new FornecedorDAO();
    
    public FornecedorCTR() {
    }
    
    public String inserirFornecedor(FornecedorDTO fornecedorDTO) {
        try {
            if(fornecedorDAO.inserirFornecedor(fornecedorDTO)) {
                return "Fornecedor Cadastrado Com Sucesso!";
            } else {
                return "Fornecedor NÃO Cadastrado!!!";
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return "Fornecedor NÃO Cadastrado!!! (Exception)";
        }
        
        
    }
    
    public String alterarFornecedor(FornecedorDTO fornecedorDTO) {
        try {
            if(fornecedorDAO.alterarFornecedor(fornecedorDTO)) {
                return "Fornecedor Alterado Com Sucesso!";
            } else {
                return "Fornecedor NÃO Alterado!!!";
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return "Fornecedor NÃO Alterado!!! (Exception)";
        }
        
        
    }
    
    public String excluirFornecedor(FornecedorDTO fornecedorDTO) {
        try {
            if(fornecedorDAO.excluirFornecedor(fornecedorDTO)) {
                return "Fornecedor Excluído Com Sucesso!";
            } else {
                return "Fornecedor NÃO Excluído!!!";
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return "Fornecedor NÃO Excluído!!! (Exception)";
        }
        
        
    }
    
    public ResultSet consultarFornecedor(FornecedorDTO fornecedorDTO, int opcao) {
        
        ResultSet rs = null;
        
        rs = fornecedorDAO.consultarFornecedor(fornecedorDTO, opcao);
        return rs;
        
    }
    
    public void CloseDB() {
        ConexaoDAO.CloseDB();
    }
    
}
