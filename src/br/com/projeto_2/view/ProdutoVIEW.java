/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.projeto_2.view;

import java.awt.Dimension;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import br.com.projeto_2.dto.FornecedorDTO;
import br.com.projeto_2.ctr.FornecedorCTR;
import br.com.projeto_2.dto.ProdutoDTO;
import br.com.projeto_2.ctr.ProdutoCTR;

/**
 *
 * @author allan
 */
public class ProdutoVIEW extends javax.swing.JInternalFrame {
    
    FornecedorDTO fornecedorDTO = new FornecedorDTO();
    FornecedorCTR fornecedorCTR = new FornecedorCTR();
    ProdutoDTO produtoDTO = new ProdutoDTO();
    ProdutoCTR produtoCTR = new ProdutoCTR();
    
    int gravar_alterar;
    
    ResultSet rs;
    DefaultTableModel modelo_jtl_consultar_produto;
    DefaultTableModel modelo_jtl_consultar_fornecedor;

    /**
     * Creates new form ProdutoVIEW
     */
    public ProdutoVIEW() {
        initComponents();
        
        liberaCampos(false);
        
        liberaBotoes(true, false, false ,false, true);
        modelo_jtl_consultar_produto = (DefaultTableModel) jtl_consultar_produto.getModel();
        modelo_jtl_consultar_fornecedor = (DefaultTableModel) jtl_consultar_fornecedor.getModel();
    }
    
    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }
    
    private void gravar() {
        try{
            produtoDTO.setNome_prod(nome_prod.getText());
            produtoDTO.setDesc_prod(desc_prod.getText());
            produtoDTO.setCod_bar_prod(cod_bar_prod.getText());
            produtoDTO.setP_custo_prod(Double.parseDouble(p_custo_prod.getText()));
            produtoDTO.setP_venda_prod(Double.parseDouble(p_venda_prod.getText()));
            fornecedorDTO.setId_for(Integer.parseInt(String.valueOf(
                jtl_consultar_fornecedor.getValueAt(
                jtl_consultar_fornecedor.getSelectedRow(), 0))));
            
            JOptionPane.showMessageDialog(null,
                    produtoCTR.inserirProduto(produtoDTO, fornecedorDTO)
            );
            
        }
        catch(Exception e) {
            System.out.println("Erro ao Gravar" + e.getMessage());
        }
    }
    
    private void alterar() {
        try{
            produtoDTO.setNome_prod(nome_prod.getText());
            produtoDTO.setDesc_prod(desc_prod.getText());
            produtoDTO.setCod_bar_prod(cod_bar_prod.getText());
            produtoDTO.setP_custo_prod(Double.parseDouble(p_custo_prod.getText()));
            produtoDTO.setP_venda_prod(Double.parseDouble(p_venda_prod.getText()));
            fornecedorDTO.setId_for(Integer.parseInt(String.valueOf(
                    jtl_consultar_fornecedor.getValueAt(
                    jtl_consultar_fornecedor.getSelectedRow(), 0))));
            
            JOptionPane.showMessageDialog(null,
                    produtoCTR.alterarProduto(produtoDTO, fornecedorDTO)
            );
        }
        catch(Exception e) {
            System.out.println("Erro ao Alterar" + e.getMessage());
        }
    }
    
    private void excluir() {
        if(JOptionPane.showConfirmDialog(null, "Deseja Realmente excluir o Produto?", "Aviso",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null,
                    produtoCTR.excluirProduto(produtoDTO)
            );
        }
    }
    
    private void liberaCampos(boolean a) {
        nome_prod.setEnabled(a);
        desc_prod.setEnabled(a);
        cod_bar_prod.setEnabled(a);
        p_custo_prod.setEnabled(a);
        p_venda_prod.setEnabled(a);
        pesquisa_nome_fornecedor.setEnabled(a);
        btnPesquisarFornecedor.setEnabled(a);
        jtl_consultar_fornecedor.setEnabled(a);
    }
    
    private void liberaBotoes(boolean a, boolean b, boolean c, boolean d, boolean e) {
        btnNovo.setEnabled(a);
        btnSalvar.setEnabled(b);
        btnCancelar.setEnabled(c);
        btnExcluir.setEnabled(d);
        btnSair.setEnabled(e);
    }
    
    private void limpaCampos() {
        nome_prod.setText("");
        desc_prod.setText("");
        cod_bar_prod.setText("");
        p_custo_prod.setText("");
        p_venda_prod.setText("");
        pesquisa_nome_fornecedor.setText("");
        modelo_jtl_consultar_fornecedor.setNumRows(0);
        
    }
    
    private void preencheTabelaProduto(String nome_prod) {
        try{
            modelo_jtl_consultar_produto.setNumRows(0);
            
            produtoDTO.setNome_prod(nome_prod);
            rs = produtoCTR.consultarProduto(produtoDTO, 1);
            while(rs.next()) {
                modelo_jtl_consultar_produto.addRow(new Object[]{
                    rs.getString("id_prod"),
                    rs.getString("nome_prod"),
                });
            }
        }
        catch(Exception e) {
            System.out.println("Erro preencheTabelaProduto: " + e.getMessage());
        }
        finally{
            produtoCTR.CloseDB();
        }
    }
    
    private void preencheCamposProduto(int id_prod) {
        try{
            produtoDTO.setId_prod(id_prod);
            rs = produtoCTR.consultarProduto(produtoDTO, 2);
            if(rs.next()) {
                limpaCampos();
                
                nome_prod.setText(rs.getString("nome_prod"));
                desc_prod.setText(rs.getString("desc_prod"));
                cod_bar_prod.setText(rs.getString("cod_bar_prod"));
                p_custo_prod.setText(rs.getString("p_custo_prod"));
                p_venda_prod.setText(rs.getString("p_venda_prod"));
                
                modelo_jtl_consultar_fornecedor.setNumRows(0);
                modelo_jtl_consultar_fornecedor.addRow(new Object[]{rs.getInt("id_for"), rs.getString("nome_for"),});
                jtl_consultar_fornecedor.setRowSelectionInterval(0, 0);
                
                gravar_alterar = 2;
                liberaCampos(true);
            }
        }
        catch(Exception e) {
            System.out.println("Erro preencheCamposProduto: " + e.getMessage());
        }
        finally{
            produtoCTR.CloseDB();
        }
    }
    
    private void preencheTabelaFornecedor(String nome_for) {
        try {
            modelo_jtl_consultar_fornecedor.setNumRows(0);
            
            fornecedorDTO.setNome_for(nome_for);
            
            rs = fornecedorCTR.consultarFornecedor(fornecedorDTO, 1);
            
            while(rs.next()) {
                modelo_jtl_consultar_fornecedor.addRow(new Object []{
                    rs.getString("id_for"),
                    rs.getString("nome_for"),
                });
            }
        }
        catch(Exception e) {
            System.out.println("Erro preencheTabelaFornecedor: " + e.getMessage());
        }
        finally{
            produtoCTR.CloseDB();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblProdutos = new javax.swing.JLabel();
        lblNome = new javax.swing.JLabel();
        lblDescricao = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        lblCusto = new javax.swing.JLabel();
        lblVenda = new javax.swing.JLabel();
        nome_prod = new javax.swing.JTextField();
        desc_prod = new javax.swing.JTextField();
        cod_bar_prod = new javax.swing.JTextField();
        p_custo_prod = new javax.swing.JTextField();
        p_venda_prod = new javax.swing.JTextField();
        lblFornecedor = new javax.swing.JLabel();
        pesquisa_nome_fornecedor = new javax.swing.JTextField();
        btnPesquisarFornecedor = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtl_consultar_fornecedor = new javax.swing.JTable();
        lblConsulta = new javax.swing.JLabel();
        btnPesquisarProduto = new javax.swing.JButton();
        lblNomePesquisa = new javax.swing.JLabel();
        pesquisa_nome_produto = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtl_consultar_produto = new javax.swing.JTable();
        btnNovo = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();

        lblProdutos.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblProdutos.setText("Produto");

        lblNome.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lblNome.setText("Nome:");

        lblDescricao.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lblDescricao.setText("Descrição:");

        lblCodigo.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lblCodigo.setText("Código:");

        lblCusto.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lblCusto.setText("P. Custo:");

        lblVenda.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lblVenda.setText("P. Venda:");

        nome_prod.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        desc_prod.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        cod_bar_prod.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        p_custo_prod.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        p_venda_prod.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        lblFornecedor.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lblFornecedor.setText("Fornecedor:");

        pesquisa_nome_fornecedor.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        btnPesquisarFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_2/view/imagens/pesquisar.png"))); // NOI18N
        btnPesquisarFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarFornecedorActionPerformed(evt);
            }
        });

        jtl_consultar_fornecedor.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jtl_consultar_fornecedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome"
            }
        ));
        jScrollPane1.setViewportView(jtl_consultar_fornecedor);

        lblConsulta.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblConsulta.setText("Consulta");

        btnPesquisarProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_2/view/imagens/pesquisar.png"))); // NOI18N
        btnPesquisarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarProdutoActionPerformed(evt);
            }
        });

        lblNomePesquisa.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lblNomePesquisa.setText("Nome:");

        pesquisa_nome_produto.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jtl_consultar_produto.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jtl_consultar_produto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome"
            }
        ));
        jtl_consultar_produto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtl_consultar_produtoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jtl_consultar_produto);

        btnNovo.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_2/view/imagens/novo.png"))); // NOI18N
        btnNovo.setText("Novo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnSalvar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_2/view/imagens/salvar.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_2/view/imagens/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnExcluir.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_2/view/imagens/excluir.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnSair.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_2/view/imagens/sair.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblCusto)
                            .addComponent(lblNome)
                            .addComponent(lblDescricao)
                            .addComponent(lblCodigo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(desc_prod, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(p_custo_prod, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                    .addComponent(cod_bar_prod))
                                .addGap(18, 18, 18)
                                .addComponent(lblVenda)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(p_venda_prod, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(nome_prod)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblFornecedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pesquisa_nome_fornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPesquisarFornecedor))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblConsulta)
                                .addGap(133, 133, 133))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblNomePesquisa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pesquisa_nome_produto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPesquisarProduto)
                                .addContainerGap())))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(208, 208, 208)
                        .addComponent(lblProdutos))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(btnNovo)
                        .addGap(18, 18, 18)
                        .addComponent(btnSalvar)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar)
                        .addGap(18, 18, 18)
                        .addComponent(btnExcluir)
                        .addGap(18, 18, 18)
                        .addComponent(btnSair)))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblProdutos)
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblFornecedor)
                                .addComponent(pesquisa_nome_fornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblNome)
                                    .addComponent(nome_prod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblConsulta))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblDescricao)
                                            .addComponent(desc_prod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblCodigo)
                                            .addComponent(cod_bar_prod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblCusto)
                                            .addComponent(lblVenda)
                                            .addComponent(p_custo_prod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(p_venda_prod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(28, 28, 28)
                                        .addComponent(btnPesquisarFornecedor))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblNomePesquisa)
                                            .addComponent(pesquisa_nome_produto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(btnPesquisarProduto)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnSalvar)
                    .addComponent(btnCancelar)
                    .addComponent(btnExcluir)
                    .addComponent(btnSair))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // TODO add your handling code here:
        liberaCampos(true);
        liberaBotoes(false, true, true , false, true);
        gravar_alterar = 1;
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnPesquisarFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarFornecedorActionPerformed
        // TODO add your handling code here:
        preencheTabelaFornecedor(pesquisa_nome_fornecedor.getText());
    }//GEN-LAST:event_btnPesquisarFornecedorActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // TODO add your handling code here:
        if(gravar_alterar == 1) {
            gravar();
            gravar_alterar = 0;
        }
        else {
            if(gravar_alterar == 2) {
                alterar();
                gravar_alterar = 0;
            }
            else {
                JOptionPane.showMessageDialog(null, "Erro no Sistema!!!");
            }
        }
        
        limpaCampos();
        liberaCampos(false);
        liberaBotoes(true, false, false, false, true);
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnPesquisarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarProdutoActionPerformed
        // TODO add your handling code here:
        preencheTabelaProduto(pesquisa_nome_produto.getText());
    }//GEN-LAST:event_btnPesquisarProdutoActionPerformed

    private void jtl_consultar_produtoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtl_consultar_produtoMouseClicked
        // TODO add your handling code here:
        preencheCamposProduto(Integer.parseInt(String.valueOf(
                jtl_consultar_produto.getValueAt(
                jtl_consultar_produto.getSelectedRow(), 0))));
        liberaBotoes(false, true, true ,true, true);
    }//GEN-LAST:event_jtl_consultar_produtoMouseClicked

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:
        excluir();
        limpaCampos();
        liberaCampos(false);
        liberaBotoes(true, false, false, false, true);
        modelo_jtl_consultar_produto.setNumRows(0);
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        limpaCampos();
        liberaCampos(false);
        modelo_jtl_consultar_produto.setNumRows(0);
        liberaBotoes(true, false, false, false, true);
        gravar_alterar = 0;
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSairActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisarFornecedor;
    private javax.swing.JButton btnPesquisarProduto;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JTextField cod_bar_prod;
    private javax.swing.JTextField desc_prod;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jtl_consultar_fornecedor;
    private javax.swing.JTable jtl_consultar_produto;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblConsulta;
    private javax.swing.JLabel lblCusto;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblFornecedor;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblNomePesquisa;
    private javax.swing.JLabel lblProdutos;
    private javax.swing.JLabel lblVenda;
    private javax.swing.JTextField nome_prod;
    private javax.swing.JTextField p_custo_prod;
    private javax.swing.JTextField p_venda_prod;
    private javax.swing.JTextField pesquisa_nome_fornecedor;
    private javax.swing.JTextField pesquisa_nome_produto;
    // End of variables declaration//GEN-END:variables
}
