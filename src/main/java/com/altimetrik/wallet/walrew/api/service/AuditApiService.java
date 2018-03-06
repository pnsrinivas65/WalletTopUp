package com.altimetrik.wallet.walrew.api.service;

import java.util.List;

import com.altimetrik.wallet.walrew.exception.NotFoundException;
import com.altimetrik.wallet.walrew.model.Audit;

public interface AuditApiService {

	List<Audit> findAllAudit() throws NotFoundException;

	Audit updateAudit(Audit audit) throws NotFoundException;

	Audit addAudit(Audit audit) throws NotFoundException;

	Audit findByIdAudit(Integer id) throws NotFoundException;

	void deleteAudit(Integer id) throws NotFoundException;

}
