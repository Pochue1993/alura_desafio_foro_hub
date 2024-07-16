package com.challengehub.forohub.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.challengehub.forohub.DTOs.DTOCrearUsuario;
import com.challengehub.forohub.DTOs.DTOGuardarUsuario;
import com.challengehub.forohub.DTOs.DTOLoginDatosUsuario;
import com.challengehub.forohub.DTOs.DTOObtenerDataToken;
import com.challengehub.forohub.DTOs.DTOActualizarUsuario;
import com.challengehub.forohub.DTOs.DTODetalleUsuarios;
import com.challengehub.forohub.modelos.Perfil;
import com.challengehub.forohub.modelos.Usuario;
import com.challengehub.forohub.repositorios.PerfilRepositorio;
import com.challengehub.forohub.repositorios.UsuarioRepositorio;
import com.challengehub.forohub.seguridad.TokenServicio;

import jakarta.validation.ValidationException;

@Service
public class UsuarioServicio
{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenServicio tokenService;

    @Autowired
    private UsuarioRepositorio userRepository;

    @Autowired
    private PerfilRepositorio profileRepository;

    public DTOObtenerDataToken authenticateUser(DTOLoginDatosUsuario dtoLoginDataUser)
    {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dtoLoginDataUser.username(),
                dtoLoginDataUser.password());

        var userAuthenticate = authenticationManager.authenticate(authenticationToken);
        var JWTtoken = tokenService.generateToken((Usuario) userAuthenticate.getPrincipal());

        DTOObtenerDataToken dtoResponseTokenData = new DTOObtenerDataToken(JWTtoken,
                "Bearer");

        return dtoResponseTokenData;
    }

    public DTODetalleUsuarios findUserById(Long id)
    {
        Optional<Usuario> userOptional = userRepository.findById(id);

        if(userOptional.isPresent())
        {
            DTODetalleUsuarios dtoUserMoreDetails = new DTODetalleUsuarios(userOptional.get().getCode(),
                    userOptional.get().getUsername(),
                    userOptional.get().getEmail(),
                    userOptional.get().getProfile().getName());

            return dtoUserMoreDetails;
        }
        else
        {
            throw new ValidationException("Ese perfil no existe");
        }
    }

    public List<DTODetalleUsuarios> findAllUsers()
    {
        List<Usuario> userRepositoriesList =userRepository.findAll();

        List<DTODetalleUsuarios> dtoUserMoreDetailsList = userRepositoriesList.stream()
                .map(u -> new DTODetalleUsuarios(u.getCode(),
                        u.getUsername(),
                        u.getEmail(),
                        u.getProfile().getName()))
                .toList();

        return dtoUserMoreDetailsList;
    }

    public DTODetalleUsuarios createNewUser(DTOCrearUsuario dtoCreateUser)
    {
        Optional<Perfil> profile = profileRepository.findById(Long.valueOf(dtoCreateUser.typeOfProfile()));

        if(profile.isPresent())
        {
            DTOGuardarUsuario dtoCreateUserToDatabase = new DTOGuardarUsuario(dtoCreateUser.username(),
                    dtoCreateUser.email(),
                    encryptPassword(dtoCreateUser.password()),
                    profile.get());

            Usuario userCreated = new Usuario(dtoCreateUserToDatabase);

            userRepository.save(userCreated);

            DTODetalleUsuarios dtoUserMoreDetails = new DTODetalleUsuarios(userCreated.getCode(),
                    userCreated.getUsername(),
                    userCreated.getEmail(),
                    userCreated.getProfile().getName());

            return dtoUserMoreDetails;
        }
        else
        {
            throw new ValidationException("Ese perfil no existe");
        }
    }

    public DTODetalleUsuarios updateUser(Long id, DTOActualizarUsuario dtoUpdateUser)
    {
        Optional<Perfil> profile = profileRepository.findById(Long.valueOf(dtoUpdateUser.typeOfProfile()));
        Optional<Usuario> userSearched = userRepository.findById(id);

        if(profile.isPresent())
        {
            if(userSearched.isPresent())
            {
                Usuario getUser = userSearched.get();

                getUser.setUsername(dtoUpdateUser.username());
                getUser.setEmail(dtoUpdateUser.email());
                getUser.setEmail(dtoUpdateUser.email());
                getUser.setProfile(profile.get());

                DTODetalleUsuarios dtoUserMoreDetails = new DTODetalleUsuarios(getUser.getCode(),
                        getUser.getUsername(),
                        getUser.getEmail(),
                        getUser.getProfile().getName());

                return dtoUserMoreDetails;
            }
            else
            {
                throw new ValidationException("Ese usuario no existe");
            }
        }
        else
        {
            throw new ValidationException("El tipo de perfil no existe");
        }
    }

    private String encryptPassword(String password)
    {
        return new BCryptPasswordEncoder().encode(password);
    }
}
