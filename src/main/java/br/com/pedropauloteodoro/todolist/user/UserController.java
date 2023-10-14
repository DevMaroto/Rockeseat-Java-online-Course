package br.com.pedropauloteodoro.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Modificador
 * public = qualquer um pode acessar a classe
 * private = tem uma restrição um pouco maior
 * protected = na mesma estrutura do pacote que tem acesso
 */
@RestController
@RequestMapping("/users")
public class UserController {

  /**
   * String (texto)
   * Integer (int) números inteiros
   * Double (double) números 0.0000
   * Float (float) números 0.000
   * char (A C)
   * Date (data)
   * void
   */
  /**
   * Body
   */
  @Autowired
  private IUserRepository userRepository;

  @PostMapping("/")
  public ResponseEntity create(@RequestBody UserModel userModel) {
    var user = this.userRepository.findByUsername(userModel.getUsername());

    if (user != null) {
      // Mensagem de erro
      // Status Code ex: 200, 400, 401...
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
    }

    var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

    userModel.setPassword(passwordHashred);

    var userCreated = this.userRepository.save(userModel);
    return ResponseEntity.status(HttpStatus.OK).body(userCreated);

  }
}
