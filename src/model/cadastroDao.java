package model;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class cadastroDao {
 Connection con;
 
 public cadastroDao() throws SQLException{
 con = ConnectionFactory.getConnection();
 }
 public boolean checkLogin(String email, String senha) throws NoSuchAlgorithmException{
 PreparedStatement stmt = null;
 ResultSet rs = null;
 boolean check = false;
 
 cadastro c = new cadastro();
 BigInteger cripto;
 cripto = c.criptografarSenha(senha);
         
 try{
 stmt = con.prepareStatement("SELECT * FROM tbagenda"
         + " where email = ? and senha =?");
         stmt.setString(1, email);
         stmt.setString(2, cripto.toString());
         //stmt.setString(2, senha);
        
         rs = stmt.executeQuery();
 if(rs.next()){
 check = true; 
    }
 }catch (SQLException e){
 JOptionPane.showMessageDialog(null,"Erro: "+e.getMessage());
 }
 return check;
 }
 public void create (cadastro c) throws NoSuchAlgorithmException{
 PreparedStatement stmt = null;
     BigInteger cripto; 
 try{
     
     
     stmt = con.prepareStatement("INSERT INTO tbagenda (nome,"
             + "email,senha,telefone,recado) VALUE (?,?,?,?,?)");
     stmt.setString(1, c.getNome());
     stmt.setString(2, c.getEmail() );
     cripto = c.criptografarSenha(c.getSenha());        
     stmt.setString(3, cripto.toString());
     stmt.setString(4, c.getTelefone());
     stmt.setString(5, c.getRecado());
     stmt.executeUpdate();
     JOptionPane.showMessageDialog(null,"Cadastro"+c.getNome()
     +"Salvo com sucesso");
 }catch(SQLException e){
 JOptionPane.showMessageDialog(null,"Erro:"+e.getMessage());
 }finally{
     ConnectionFactory.closeConnection(con, stmt);
     }
 }
 public void update(cadastro c){
 PreparedStatement stmt = null;
 try{
 stmt = con.prepareStatement("UPDATE tbagenda SET nome = ?,"
         + "email = ?, senha = ?, telefone = ?, recado = ? WHERE id = ?");
 
 stmt.setString(1, c.getNome());
 stmt.setString(2, c.getEmail());
 stmt.setString(3, c.getSenha());
 stmt.setString(4, c.getTelefone());
 stmt.setString(5, c.getRecado());
 stmt.setInt    (6, c.getId());
 stmt.executeUpdate();
 JOptionPane.showMessageDialog(null,"cadastro"+c.getNome()
 +"Modificado com sucesso ");
 }catch(SQLException e){
 JOptionPane.showMessageDialog(null,"Erro:"+e.getMessage());
 }finally{
 ConnectionFactory.closeConnection(con,stmt);
 }
 }
 public void delete (cadastro c){
 PreparedStatement stmt = null;
 try{
 stmt = con.prepareStatement("DELETE FROM tbagenda WHERE id = ?");
 
 stmt.setInt (1, c.getId());
 
 if(JOptionPane.showConfirmDialog(null,"Exclusão","Tem certeza que"
         + "deseja excluir o cadastro? "+c.getNome(),
         JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
     
     {JOptionPane.showMessageDialog(null,"cadastro"+c.getNome()
     +" Excluido com sucesso");
     stmt.executeUpdate();          }   
 }else{JOptionPane.showMessageDialog(null,"A exclusao do cadastro"+c.getNome()
 +" cancelado com sucesso ");
         }
 }catch(SQLException e){
JOptionPane.showMessageDialog(null,"erro"+e.getMessage());
}finally{
ConnectionFactory.closeConnection(con, stmt);
     }
}    
 public ArrayList<cadastro> read(){
 PreparedStatement stmt = null;
 ResultSet rs = null;
 List<cadastro> cadastros = new ArrayList<cadastro>();
 try{
 stmt = con.prepareStatement("SELECT * FROM tbagenda");
 rs = stmt.executeQuery();
 while(rs.next()){
 cadastro cadastro = new cadastro();
 cadastro.setId(rs.getInt("id"));
 cadastro.setNome(rs.getString("nome"));
 cadastro.setEmail(rs.getString("email"));
 cadastro.setSenha(rs.getString("senha"));
 cadastro.setTelefone(rs.getString("telefone"));
 cadastro.setRecado(rs.getString("recado"));
 cadastros.add(cadastro);
    }
 }catch(SQLException e){
 JOptionPane.showMessageDialog(null,"Erro: "+e.getMessage());
 }finally{
 ConnectionFactory.closeConnection(con, stmt, rs);
 }
 return (ArrayList<cadastro>) cadastros;
 }
 public ArrayList<cadastro> readPesq(String nome){
 PreparedStatement stmt = null;
 ResultSet rs = null;
 List<cadastro> cadastros = new ArrayList<cadastro>();
 try{
 stmt = con.prepareStatement("SELECT * FROM tbagenda WHERE nome LIKE ?");
 stmt.setString(1, "%"+nome+"%");
 rs = stmt.executeQuery();
 while(rs.next()){
 cadastro cadastro = new cadastro ();
 cadastro.setId(rs.getInt("id"));
 cadastro.setNome(rs.getString("nome"));
 cadastro.setEmail(rs.getString("email"));
 cadastro.setSenha(rs.getString("senha"));
 cadastro.setTelefone(rs.getString("telefone"));
 cadastro.setRecado(rs.getString("recado"));
 cadastros.add(cadastro);
 }
 }catch (SQLException e){
 JOptionPane.showMessageDialog(null,"Erro: "+e.getMessage());
 }finally{
 ConnectionFactory.closeConnection(con,stmt, rs);
 }
 return(ArrayList<cadastro>) cadastros;
 }
 public void delet (cadastro c){
 throw new UnsupportedOperationException("Not supported yet.");
 }
}

 
 
 
 
 
 
 
 
 
 
 
 

