package com.tms.easyrento.service;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/6/23 8:43 PM
 */

import java.util.List;

/**
 * <code>S</code> : Source or RequestDto <br>
 * <code>D</code> : Destination or ResponseDto <br>
 * <code>M</code> : Model or Model object
 * <code>I</code> : Id of model

 */
public interface CurdService <REQ, MOD, RES, ID>{

    ID create(REQ request);

    ID update(REQ request, ID id);

    List<RES> read(String isActive);

    RES read(ID id);

    void delete(ID id);

    boolean hardDelete(ID id);

    MOD model(ID id);
}
