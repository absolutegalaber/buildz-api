package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.repository.BuildLabelRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BuildLabelService {
    private final BuildLabelRepository buildLabelRepository

    BuildLabelService(BuildLabelRepository buildLabelRepository) {
        this.buildLabelRepository = buildLabelRepository
    }

    Set<String> allKnownLabelKeys() {
        buildLabelRepository.distinctLabelKeys();
    }
}
