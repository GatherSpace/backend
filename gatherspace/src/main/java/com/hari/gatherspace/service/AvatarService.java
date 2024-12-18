package com.hari.gatherspace.service;

import com.hari.gatherspace.dto.CreateAvatarRequest;
import com.hari.gatherspace.model.Avatar;
import com.hari.gatherspace.repository.AvatarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvatarService {

    @Autowired
    AvatarRepository avatarRepository;

    public List<Avatar> findAll() {
        return avatarRepository.findAll();
    }

    public List<Avatar> getAvatarsById(List<String> ids) {
        return avatarRepository.findAllById(ids);
    }


    public Avatar createAvatar(CreateAvatarRequest request) {
        Avatar avatar = new Avatar();
        avatar.setImageUrl(request.getImageUrl());
        avatar.setName(request.getName());
        return avatarRepository.save(avatar);
    }
}
