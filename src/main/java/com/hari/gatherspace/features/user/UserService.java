package com.hari.gatherspace.features.user;

import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService implements UserDetailsService {
  private UserRepository userRepository;

  UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public void updateUser(User user) {

    User usser = userRepository.save(user);
    log.debug("User avatar updated: userId={}, avatarId={}", usser.getId(), usser.getAvatarId());
    return;
  }

  public List<User> getUsersById(List<String> ids) {
    return userRepository.findAllById(ids);
  }

  @Transactional
  public User saveUser(User user) {
    return userRepository.save(user);
  }

  public Optional<User> getUser(String username) {
    return userRepository.findByUsername(username);
  }

  public Optional<User> getUserById(String id) {
    return userRepository.findById(id);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = getUser(username);
    if (user.isEmpty()) {
      throw new UsernameNotFoundException("user not found");
    }

    // Extract roles from User entity and map them to Spring Security authorities
    Collection<GrantedAuthority> authorities = new java.util.ArrayList<>(List.of());
    log.debug("Assigning role to user: username={}, role={}", user.get().getUsername(), user.get().getRole());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + user.get().getRole().toString()));
    log.trace("User loaded: username={}", user.get().getUsername());
    return new org.springframework.security.core.userdetails.User(
        user.get().getUsername(), user.get().getPassword(), authorities);
  }

  public void removeUser(String userId) {}
}
