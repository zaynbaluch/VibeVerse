package com.vibeverse.server.repository;

import com.vibeverse.server.entity.ViberMedia;
import com.vibeverse.server.entity.Viber;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ViberMediaRepository extends BaseRepository<ViberMedia> {
    List<ViberMedia> findByViber(Viber viber);
    List<ViberMedia> findByViberId(Long viberId);
    List<ViberMedia> findByViberIdAndType(Long viberId, ViberMedia.MediaType type);
    Optional<ViberMedia> findByViberIdAndMediaId(Long viberId, Long mediaId);
    Optional<ViberMedia> findByViberIdAndExternalId(Long viberId, String externalId);
}
