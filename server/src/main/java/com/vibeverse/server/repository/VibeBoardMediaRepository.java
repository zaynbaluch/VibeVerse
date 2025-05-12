package com.vibeverse.server.repository;
//package com.vibeverse.repository;

import com.vibeverse.server.entity.VibeBoardMedia;
import com.vibeverse.server.entity.VibeBoard;
import com.vibeverse.server.entity.ViberMedia;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VibeBoardMediaRepository extends BaseRepository<VibeBoardMedia> {
    List<VibeBoardMedia> findByVibeBoard(VibeBoard vibeBoard);
    List<VibeBoardMedia> findByVibeBoardId(Long vibeBoardId);
    Optional<VibeBoardMedia> findByVibeBoardIdAndMediaIdAndMediaType(Long vibeBoardId, Long mediaId, ViberMedia.MediaType mediaType);
    void deleteByVibeBoardIdAndMediaIdAndMediaType(Long vibeBoardId, Long mediaId, ViberMedia.MediaType mediaType);
}