package br.com.projetosigse.config;

import br.com.projetosigse.model.Usuario;
import br.com.projetosigse.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initUsuarios(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            criarUsuarioSeNecessario(usuarioRepository, passwordEncoder, "Administrador", "admin@sigse.com", "admin123", Usuario.Perfil.ADMIN);
            criarUsuarioSeNecessario(usuarioRepository, passwordEncoder, "Portaria", "portaria@sigse.com", "portaria123", Usuario.Perfil.PORTARIA);
            criarUsuarioSeNecessario(usuarioRepository, passwordEncoder, "Inspetora", "inspetora@sigse.com", "inspetora123", Usuario.Perfil.INSPETORA);
            criarUsuarioSeNecessario(usuarioRepository, passwordEncoder, "Televisão", "tv@sigse.com", "tv123", Usuario.Perfil.TELEVISAO);
        };
    }

    private void criarUsuarioSeNecessario(UsuarioRepository usuarioRepository,
                                         PasswordEncoder passwordEncoder,
                                         String nome,
                                         String email,
                                         String senha,
                                         Usuario.Perfil perfil) {
        try {
            if (usuarioRepository.findByEmail(email).isEmpty()) {
                Usuario usuario = new Usuario();
                usuario.setNome(nome);
                usuario.setEmail(email);
                usuario.setSenha(passwordEncoder.encode(senha));
                usuario.setPerfil(perfil);
                usuario.setAtivo(true);
                usuarioRepository.save(usuario);
            }
        } catch (DataIntegrityViolationException e) {
            // ignora se o usuário já existir em outro processo
        }
    }
}
