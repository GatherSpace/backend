package com.hari.gatherspace.service;

import com.hari.gatherspace.dto.UserSessionDto;
import com.hari.gatherspace.model.UserSession;
import com.hari.gatherspace.repository.UserSessionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserSessionServiceImpl implements UserSessionService {

    private final UserSessionRepository sessionRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserSessionServiceImpl(UserSessionRepository sessionRepository, ModelMapper modelMapper) {
        this.sessionRepository = sessionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserSessionDto> getAllSessions() {
        List<UserSession> sessions = sessionRepository.findAll();
        return sessions.stream()
                .map(session -> modelMapper.map(session, UserSessionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserSessionDto getSessionById(Long sessionId) {
        UserSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        return modelMapper.map(session, UserSessionDto.class);
    }

    @Override
    public List<UserSessionDto> getSessionsByUserId(String userId) {
        List<UserSession> sessions = sessionRepository.findByUserId(userId);
        return sessions.stream()
                .map(session -> modelMapper.map(session, UserSessionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserSessionDto addSession(UserSessionDto userSessionDto) {
        UserSession session = modelMapper.map(userSessionDto, UserSession.class);
        session.setIsValid(true);
        UserSession savedSession = sessionRepository.save(session);
        return modelMapper.map(savedSession, UserSessionDto.class);
    }

    @Override
    public UserSessionDto deleteSession(Long id) {
        UserSession session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        sessionRepository.delete(session);
        return modelMapper.map(session, UserSessionDto.class);
    }

    @Override
    public UserSessionDto invalidate(String token) {
        UserSession session = sessionRepository.findByRefreshToken(token)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        session.setIsValid(false);
        UserSession updatedSession = sessionRepository.save(session);
        return modelMapper.map(updatedSession, UserSessionDto.class);
    }

    @Override
    public void invalidateAllSessionsForUser(String userId) {
        List<UserSession> sessions = sessionRepository.findByUserId(userId);
        sessions.forEach(session -> {
            session.setIsValid(false);
            sessionRepository.save(session);
        });
    }

    @Override
    public boolean isTokenValid(String token) {
        UserSession session = sessionRepository.findByRefreshToken(token)
                .orElse(null);
        return session != null &&
                session.getIsValid() &&
                session.getExpiresAt().isAfter(LocalDateTime.now());
    }

    @Override
    public void flushExpiredSessions() {
        List<UserSession> expiredSessions = sessionRepository.findByExpiresAtBefore(LocalDateTime.now());
        sessionRepository.deleteAll(expiredSessions);
    }
}
