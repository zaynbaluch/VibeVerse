package com.vibeverse.server.service;

import com.vibeverse.server.model.Viber;

import java.util.List;

public interface ViberService {
    Viber createViber(Viber viber);
    Viber getViberById(Long id);
    List<Viber> getAllVibers();
    Viber updateViber(Long id, Viber viberDetails);
    void deleteViber(Long id);
}
