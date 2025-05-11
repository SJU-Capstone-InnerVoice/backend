package com.innervoice.talk.repository;

import com.innervoice.talk.domain.Talk;
import com.innervoice.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TalkRepository extends JpaRepository<Talk, Long> {

    List<Talk> findAllByUser(User user);
}