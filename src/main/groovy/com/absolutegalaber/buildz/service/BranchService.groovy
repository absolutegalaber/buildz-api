package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.repository.BranchRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class BranchService {
    private final BranchRepository branchRepository

    BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository
    }
}
